package com.pm.developextest.data

import android.arch.lifecycle.MutableLiveData
import com.pm.developextest.domain.engine.*
import io.reactivex.disposables.Disposable

class UrlExplorer {

    val urls = MutableLiveData<List<UrlInfo>>()
    val state = MutableLiveData<UrlExplorerEngineState>()

    private var disposable: Disposable? = null
    private var engine: UrlExplorerEngine = IdleUrlExplorerEngine(
        emptyList(),
        listOf(WaitingUrlInfo("https://uk.wikipedia.org")),
        1000,
        4,
        "hello"
    )


    init {
        state.postValue(UrlExplorerEngineState.Idle)
        urls.postValue(engine.processedUrls.plus(engine.waitingUrls))
    }

    fun getUrl() = engine.startUrl().url
    fun getSearchText() = engine.searchText
    fun getMaxThreads() = engine.maxThreads
    fun getMaxUrls() = engine.maxUrls

    fun start() {
        val current = engine

        if (current is IdleUrlExplorerEngine) {

            val newEngine = current.toRunning()
            engine = newEngine
            state.postValue(UrlExplorerEngineState.Running)
            disposable = newEngine.result
                .subscribe{
                    urls.postValue(it)
                    if (it.all { info -> info.isFinalized() }) {
                        stop()
                    }
                }

        }
    }

    fun stop() {
        disposable?.dispose()
        val current = engine
        if (current is RunningUrlExplorerEngine) {
            current.stop()
            val currentState = urls.value ?: emptyList()
            engine = current.toIdle(currentState)
            state.postValue(UrlExplorerEngineState.Idle)
        }
    }

    fun setUp(url: String, searchText: String, maxThreadNumber: Long, maxUrlNumber: Long) {

        val current = engine

        var processedUrls = current.processedUrls
        var waitingUrls = current.waitingUrls

        if (url != current.startUrl().url || searchText != current.searchText) {
            processedUrls = listOf()
            waitingUrls = listOf(WaitingUrlInfo(url))
        }

        if (current is RunningUrlExplorerEngine) {
            current.stop()
        }

        engine = IdleUrlExplorerEngine(processedUrls, waitingUrls, maxUrlNumber, maxThreadNumber, searchText)

        if (current is RunningUrlExplorerEngine) {
            start()
        } else {
            urls.postValue(engine.processedUrls.plus(engine.waitingUrls))
        }

    }

}

enum class UrlExplorerEngineState {
    Idle, Running
}


