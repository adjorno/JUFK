package com.ifochka.jufk

object Platform {
    val supportedPlatforms =
        listOf(
            "Web",
            "Android",
            "iOS",
            "macOS",
            "Windows",
            "Linux",
        )

    fun isSupported(platform: String): Boolean = supportedPlatforms.any { it.equals(platform, ignoreCase = true) }

    fun getPlatformCount(): Int = supportedPlatforms.size
}
