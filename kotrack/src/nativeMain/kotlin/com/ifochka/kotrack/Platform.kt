package com.ifochka.kotrack

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO

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

actual fun createAnalytics(): Analytics = PostHogClient()
