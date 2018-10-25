package com.pm.developextest.domain.engine

fun WaitingUrlInfo.toProcessing() =
    ProcessingUrlInfo(url = url, timestamp = timestamp)

fun ProcessingUrlInfo.toWaiting() =
    WaitingUrlInfo(url = url, timestamp = timestamp)

fun ProcessingUrlInfo.toCompleted(
    title: String,
    textMatchNumber: Int
) = CompletedUrlInfo(
    url = url,
    timestamp = timestamp,
    title = title,
    textMatchNumber = textMatchNumber
)

fun ProcessingUrlInfo.toError(
    error: String
) = ErrorUrlInfo(url = url, timestamp = timestamp, error = error)

fun UrlInfo.isFinalized() = when (this) {
    is CompletedUrlInfo -> true
    is ErrorUrlInfo -> true
    else -> false
}

fun List<UrlInfo>.getFinalizedUrlInfo() = asSequence()
    .filter { info -> info.isFinalized() }

fun List<UrlInfo>.getNotFinalizedUrlInfo() = asSequence()
    .filter { info -> !info.isFinalized() }
    .map {
        when (it) {
            is ProcessingUrlInfo -> it.toWaiting()
            else -> it
        }
    }


fun RunningUrlExplorerEngine.toIdle(urls: List<UrlInfo>) = IdleUrlExplorerEngine(
    urls.getFinalizedUrlInfo().toList(),
    urls.getNotFinalizedUrlInfo().toList(),
    maxUrls,
    maxThreads,
    searchText
)

fun IdleUrlExplorerEngine.toRunning() = RunningUrlExplorerEngine(
    processedUrls,
    waitingUrls,
    maxUrls,
    maxThreads,
    searchText
)
