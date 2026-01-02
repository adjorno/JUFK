package com.ifochka.kotrack

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO

// Simple in-memory storage for distinct_id (persists for app lifetime)
private var storedDistinctId: String = ""

internal actual fun createHttpClient(): HttpClient = HttpClient(CIO)

@OptIn(kotlin.experimental.ExperimentalNativeApi::class)
internal actual fun getPlatformName(): String =
    when (kotlin.native.Platform.osFamily) {
        kotlin.native.OsFamily.IOS -> "IOS"
        kotlin.native.OsFamily.LINUX -> "CLI_LINUX"
        kotlin.native.OsFamily.MACOSX -> "CLI_MACOS"
        kotlin.native.OsFamily.WINDOWS -> "CLI_WINDOWS"
        else -> "NATIVE"
    }

internal actual fun getPostHogApiKey(): String? = null // TODO: Get from BuildKonfig

internal actual fun getDistinctId(): String = storedDistinctId

internal actual fun saveDistinctId(id: String) {
    storedDistinctId = id
}

actual fun createAnalytics(): Analytics = PostHogClient()
