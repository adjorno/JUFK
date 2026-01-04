package com.ifochka.jufk.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocalDining
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Cta {
    data class Link(
        val text: String,
        val url: String,
    ) : Cta()

    data class Button(
        val text: String,
        val url: String,
        val icon: ImageVector?,
    ) : Cta()

    data class Code(
        val code: String,
    ) : Cta()
}

/**
 * Represents a platform section with content and documentation link.
 */
data class PlatformSection(
    val id: String,
    val title: String,
    val content: String,
    val icon: ImageVector,
    val cta: Cta,
    val isHighlighted: Boolean = false,
)

/**
 * Social link for the footer.
 */
data class SocialLink(
    val name: String,
    val url: String,
)

/**
 * All content data for the app.
 */
object Content {
    const val HERO_TITLE = "Just Use F*cking Kotlin. Period."
    const val HERO_SUBTITLE = "You\'re tired of maintaining three codebases. " +
        "You\'re tired of \"it works on my machine\" but not on iOS. Stop overthinking it. " +
        "It\'s time to build everything in one language and go touch grass."

    const val BREW_COMMAND = "brew install adjorno/jufk/jufk"

    const val GITHUB_URL = "https://github.com/adjorno/JUFK"
    const val WEBSITE_URL = "https://justusefuckingkotlin.com"
    const val CODE_SNIPPET = "brew install adjorno/jufk/jufk"

    val platformSections = listOf(
        PlatformSection(
            id = "wasm",
            title = "Kotlin/Wasm",
            content = "Build fast Web UIs. Compiled from the same Kotlin codebase. Seriously performant.",
            icon = Icons.Default.Language,
            cta = Cta.Link("justusefuckingkotlin.com", WEBSITE_URL),
            isHighlighted = true,
        ),
        PlatformSection(
            id = "android",
            title = "Android",
            content = "Kotlin\'s native platform. Android Studio loves it. Your users will too.",
            icon = Icons.Default.Android,
            cta = Cta.Button("Get it on Google Play", "#", null), // Placeholder URL
        ),
        PlatformSection(
            id = "ios",
            title = "iOS",
            content = "Yes, a Kotlin app on iOS. Compiles down to native iOS. No joke.",
            icon = Icons.Default.LocalDining, // Apple does not exist, we will add a custom later
            cta = Cta.Button("App Store", "#", Icons.Default.Download), // Placeholder URL
        ),
        PlatformSection(
            id = "desktop",
            title = "Desktop",
            content = "One codebase. Your desktop app that no one asked for.",
            icon = Icons.Default.Computer,
            cta = Cta.Button("Download", "#", Icons.Default.Download), // Placeholder URL
        ),
        PlatformSection(
            id = "cli",
            title = "CLI",
            content = "For the terminal warriors. It\'s just a Kotlin app. Even a command line tool.",
            icon = Icons.Default.Terminal,
            cta = Cta.Code(BREW_COMMAND),
        ),
    )

    val limitations = listOf(
        "What Compile Times?",
        "iOS Ecosystem",
    )

    const val LIMITATIONS_HEADING = "Okay, Fine, It\'s Not Perfect"

    val socialLinks = listOf(
        SocialLink(
            name = "GitHub",
            url = "https://github.com/adjorno",
        ),
        SocialLink(
            name = "X",
            url = "https://x.com/adjorno",
        ),
        SocialLink(
            name = "LinkedIn",
            url = "https://linkedin.com/in/adjorno",
        ),
    )

    const val FOOTER_AUTHOR = "@adjorno"
}
