package com.pm.developextest.domain.engine

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import org.jsoup.Jsoup
import java.net.URL
import java.util.concurrent.Executors

sealed class UrlExplorerEngine {
    abstract val processedUrls: List<UrlInfo>
    abstract val waitingUrls: List<UrlInfo>
    abstract val maxUrls: Long
    abstract val maxThreads: Long
    abstract val searchText: String

    fun startUrl() = requireNotNull(
        processedUrls.asSequence().plus(waitingUrls).sortedBy { it.url }.first()
    )
}

class IdleUrlExplorerEngine(
    override val processedUrls: List<UrlInfo>,
    override val waitingUrls: List<UrlInfo>,
    override val maxUrls: Long,
    override val maxThreads: Long,
    override val searchText: String
) : UrlExplorerEngine()

class RunningUrlExplorerEngine(
    override val processedUrls: List<UrlInfo>,
    override val waitingUrls: List<UrlInfo>,
    override val maxUrls: Long,
    override val maxThreads: Long,
    override val searchText: String
) : UrlExplorerEngine() {

    val result = BehaviorSubject.create<List<UrlInfo>>().toSerialized()
    private val newUrls = PublishSubject.create<UrlInfo>().toSerialized()
    private val urlsState = PublishSubject.create<UrlInfo>().toSerialized()

    private val scheduler = Schedulers.from(Executors.newFixedThreadPool(maxThreads.toInt()))
    private val initUrl = inflateStartUrl()

    private val newUrlsDisposable = newUrls
        .startWith(processedUrls.plus(waitingUrls))
        .distinct { it.url }
        .take(maxUrls)
        .skip(processedUrls.size.toLong())
        .subscribeOn(scheduler)
        .observeOn(scheduler)
        .subscribe {
            urlsState.onNext(it)
            process(it as WaitingUrlInfo)
        }

    private val urlStateDisposable = urlsState
        .scan(processedUrls.asSequence().plus(waitingUrls).associateBy { it.url }) { acc, info ->
            acc.plus(info.url to info)
        }
        .map { state -> state.values.toList().sortedBy { info -> info.timestamp } }
        .subscribe { urls ->
            result.onNext(urls)
        }


    private fun process(info: WaitingUrlInfo) {
        val processedUrl = info.toProcessing()

        Single
            .fromCallable {
                urlsState.onNext(processedUrl)
                Jsoup.connect(info.url).get()
            }
            .subscribeOn(scheduler)
            .subscribe({ doc ->
                doc
                    .select("a[href]")
                    .map { element -> element.attr("href") }
                    .filter { href -> href.startsWith('/') && !href.startsWith("//") }
                    .forEach { url -> newUrls.onNext(WaitingUrlInfo(initUrl + url)) }

                urlsState.onNext(
                    processedUrl.toCompleted(
                        doc.title(),
                        doc.text().findOccurrence(searchText)
                    )
                )
            }, { throwable ->
                urlsState.onNext(processedUrl.toError(throwable.message ?: "Unknown error"))
            })

    }

    private fun inflateStartUrl(): String {
        return startUrl()
            .let { info ->
                val url = URL(info.url)
                "${url.protocol}://${url.host}"
            }
    }

    fun stop() {
        newUrlsDisposable.dispose()
        urlStateDisposable.dispose()
        scheduler.shutdown()
        result.onComplete()
    }
}

private fun String.findOccurrence(text: String): Int {

    var pos = 0
    var counter = 0

    do {
        pos = indexOf(text, pos, true)
        if (pos >= 0) {
            counter++
            pos++
        }
    } while (pos > 0)

    return counter
}