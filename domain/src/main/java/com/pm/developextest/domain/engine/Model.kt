package com.pm.developextest.domain.engine

sealed class UrlInfo {
    abstract val url: String
    abstract val timestamp: Long
}

data class WaitingUrlInfo (
    override val url: String,
    override val timestamp: Long = System.currentTimeMillis()
): UrlInfo()

data class ProcessingUrlInfo(
    override val url: String,
    override val timestamp: Long
) : UrlInfo()

data class CompletedUrlInfo(
    override val url: String,
    override val timestamp: Long,
    val title: String,
    val textMatchNumber: Int
) : UrlInfo()

data class ErrorUrlInfo(
    override val url: String,
    override val timestamp: Long,
    val error: String
) : UrlInfo()
