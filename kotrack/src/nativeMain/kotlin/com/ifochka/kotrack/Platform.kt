package com.ifochka.kotrack

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO

internal actual fun createHttpClient(): HttpClient = HttpClient(CIO)

internal actual fun getPlatformName(): String = "NATIVE" // iOS or CLI

internal actual fun getPostHogApiKey(): String? = null // TODO: Get from BuildKonfig

actual fun createAnalytics(): Analytics = PostHogClient()
