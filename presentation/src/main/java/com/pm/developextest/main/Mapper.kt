package com.pm.developextest.main

import com.pm.developextest.domain.engine.*


fun WaitingUrlInfo.map() = Item(url = url, status = "Waiting")
fun ProcessingUrlInfo.map() = Item(url = url, status = "In progress")
fun CompletedUrlInfo.map() = Item(
    url = url,
    status = title,
    matches = when (textMatchNumber) {
        0 -> ""
        else -> textMatchNumber.toString()
    }
)

fun ErrorUrlInfo.map() = Item(url = url, status = error)

fun UrlInfo.map() = when (this) {
    is WaitingUrlInfo -> this.map()
    is ProcessingUrlInfo -> this.map()
    is CompletedUrlInfo -> this.map()
    is ErrorUrlInfo -> this.map()
}

fun List<UrlInfo>.mapToItems() = map { urlInfo -> urlInfo.map() }
