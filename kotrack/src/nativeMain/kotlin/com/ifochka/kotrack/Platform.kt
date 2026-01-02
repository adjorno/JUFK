package com.ifochka.kotrack

import kotlinx.coroutines.CoroutineScope

// Simple in-memory storage for distinct_id (persists for app lifetime)
private var storedDistinctId: String = ""

@OptIn(kotlin.experimental.ExperimentalNativeApi::class)
internal actual fun getPlatformName(): String =
    when (Platform.osFamily) {
        OsFamily.IOS -> "IOS"
        OsFamily.LINUX -> "CLI_LINUX"
        OsFamily.MACOSX -> "CLI_MACOS"
        OsFamily.WINDOWS -> "CLI_WINDOWS"
        else -> "NATIVE"
    }

internal actual fun getDistinctId(): String = storedDistinctId

internal actual fun saveDistinctId(id: String) {
    storedDistinctId = id
}

actual fun createAnalytics(
    apiKey: String,
    coroutineScope: CoroutineScope,
): Analytics = PostHogClient(apiKey, coroutineScope)
