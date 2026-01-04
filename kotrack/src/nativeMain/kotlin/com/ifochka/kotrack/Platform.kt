package com.ifochka.kotrack

import kotlinx.coroutines.CoroutineScope

@OptIn(kotlin.experimental.ExperimentalNativeApi::class)
public actual fun getPlatformName(): String =
    when (Platform.osFamily) {
        OsFamily.IOS -> "IOS"
        OsFamily.LINUX -> "CLI_LINUX"
        OsFamily.MACOSX -> "CLI_MACOS"
        OsFamily.WINDOWS -> "CLI_WINDOWS"
        else -> "NATIVE"
    }

actual fun createAnalytics(
    apiKey: String,
    coroutineScope: CoroutineScope,
): Analytics = PostHogClient(apiKey, coroutineScope)
