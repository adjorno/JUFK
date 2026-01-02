package com.ifochka.kotrack

import io.ktor.client.HttpClient
import io.ktor.client.engine.js.Js

internal actual fun createHttpClient(): HttpClient = HttpClient(Js)

private external interface Window {
    @JsName("POSTHOG_API_KEY")
    val posthogApiKey: String?
}

private external interface Document {
    val cookie: String
}

@JsName("window")
private external val window: Window

@JsName("document")
private external val document: Document

internal actual fun getPlatformName(): String = "WEB"

internal actual fun getPostHogApiKey(): String? = window.posthogApiKey

actual fun createAnalytics(): Analytics {
    val analytics = PostHogClient()

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
