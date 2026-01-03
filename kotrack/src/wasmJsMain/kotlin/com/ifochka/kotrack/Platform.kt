package com.ifochka.kotrack

import io.ktor.client.HttpClient
import io.ktor.client.engine.js.Js
import kotlinx.coroutines.CoroutineScope

internal actual fun createHttpClient(): HttpClient = HttpClient(Js)

private external interface Document {
    val cookie: String
}

@JsName("document")
private external val document: Document

internal actual fun getPlatformName(): String = "WEB"

actual fun createAnalytics(
    apiKey: String,
    coroutineScope: CoroutineScope,
): Analytics {
    val analytics = PostHogClient(apiKey, coroutineScope)

    // Check for campaign in cookie
    getCookie("campaign")?.let { analytics.setCampaign(it) }

    return analytics
}

private fun getCookie(name: String): String? =
    document.cookie
        .split(";")
        .map { it.trim() }
        .find { it.startsWith("$name=") }
        ?.substringAfter("=")
