package com.ifochka.jufk.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QueryBuilder
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

data class PlatformSection(
    val id: String,
    val title: String,
    val content: String,
    val icon: ImageVector,
    val cta: Cta,
    val isHighlighted: Boolean = false,
)

data class SocialLink(
    val name: String,
    val url: String,
    val icon: ImageVector,
)

data class Video(
    val id: String,
    val title: String,
    val url: String,
)

data class GoodnessLink(
    val id: String,
    val title: String,
    val subtitle: String,
    val url: String,
    val icon: ImageVector,
)

data class Limitation(
    val title: String,
    val description: String,
    val icon: ImageVector,
)

data class InspirationLink(
    val name: String,
    val url: String,
)

object Content {
    const val HERO_TITLE = "Just Use Fucking Kotlin. Period."
    const val HERO_SUBTITLE =
        "Too many platforms. Too many rewrites. " +
            "The same logic, drifting apart over time. One language keeps it together."

    const val BREW_COMMAND = "brew install adjorno/jufk/jufk"

    const val GITHUB_URL = "https://github.com/adjorno/JUFK"
    const val WEBSITE_URL = "https://justusefuckingkotlin.com"

    val platformSections = listOf(
        PlatformSection(
            id = "web",
            title = "Kotlin/WASM",
            content = "Build fast Web UIs. Compiled from the same Kotlin codebase. Seriously performant.",
            icon = Icons.Default.Language,
            cta = Cta.Link("justusefuckingkotlin.com", WEBSITE_URL),
        ),
        PlatformSection(
            id = "android",
            title = "Android",
            content = "Kotlin\'s native platform. Android Studio loves it. Your users will too.",
            icon = Icons.Default.Android,
            cta = Cta.Button(
                text = "Get it on Google Play",
                url = "https://play.google.com/store/apps/details?id=com.ifochka.jufk",
                icon = null,
            ), // Placeholder URL
        ),
        PlatformSection(
            id = "ios",
            title = "iOS",
            content = "Yes, a Kotlin app on iOS. Compiles down to native iOS. No joke.",
            icon = Icons.Default.Devices,
            cta = Cta.Button(
                "App Store",
                "https://apps.apple.com/us/app/jufk/id6757016615",
                Icons.Default.Download,
            ), // Placeholder URL
        ),
        PlatformSection(
            id = "desktop",
            title = "Desktop",
            content = "One codebase. Your desktop app that no one asked for.",
            icon = Icons.Default.Computer,
            cta = Cta.Button(
                "Download",
                "https://github.com/adjorno/JUFK/releases",
                Icons.Default.Download,
            ), // Placeholder URL
        ),
        PlatformSection(
            id = "cli",
            title = "CLI",
            content = "For the terminal warriors. It\'s just a Kotlin app. Even a command line tool.",
            icon = Icons.Default.Terminal,
            cta = Cta.Code(BREW_COMMAND),
        ),
    )

    const val LIMITATIONS_HEADING = "Okay, Fine, It\'s Not Perfect"
    val limitations = listOf(
        Limitation(
            title = "What Compile Times?",
            description = "Compiling Kotlin Multiplatform isn\'t instant (yet). " +
                "Sure, it\'s not a cup of coffee, but you will have enough time to " +
                "check a few official docs if you\'re desperate.",
            icon = Icons.Default.QueryBuilder,
        ),
        Limitation(
            title = "iOS Ecosystem",
            description = "You still need Xcode installed for your local machine. " +
                "We can\'t do Kotlin a native project, but you can make uniting it a vocation.",
            icon = Icons.Default.Devices,
        ),
    )

    const val MAKING_OF_HEADING = "The Making Of"
    val videos = listOf(
        Video("1", "Building a Kotlin Multiplatform App", "https://youtube.com/"),
        Video("2", "Kotlin/JS Deep Dive", "https://youtube.com/"),
    )

    const val GOODNESS_HEADING = "More Kotlin Goodness"
    val goodnessLinks = listOf(
        GoodnessLink(
            "1",
            "Kotlin Weekly",
            "Essential newsletter to stay up to date",
            "https://kotlinweekly.net/",
            Icons.Default.Email,
        ),
        GoodnessLink(
            "2",
            "Android Developers Blog",
            "Kotlin\'s official words from HQ",
            "https://android-developers.googleblog.com/",
            Icons.AutoMirrored.Filled.Article,
        ),
        GoodnessLink(
            "3",
            "Jake Wharton\'s Blog",
            "Deep dives on the future of everything",
            "https://jakewharton.com/blog/",
            Icons.Default.Person,
        ),
    )

    const val FOOTER_AUTHOR = "@adjorno"
    val socialLinks = listOf(
        SocialLink("GitHub", "https://github.com/adjorno", Icons.Default.Person), // Placeholder
        SocialLink("X", "https://x.com/adjorno", Icons.Default.Person), // Placeholder
        SocialLink(
            name = "LinkedIn",
            url = "https://www.linkedin.com/in/mykhailo-dorokhin-0b99305a",
            icon = Icons.Default.Person,
        ), // Placeholder
    )

    const val INSPIRATION_TEXT = "Inspired by"
    const val INSPIRATION_SUFFIX = "and other legends."
    val inspirationLinks = listOf(
        InspirationLink("justfuckingusetailwind.com", "https://justfuckingusetailwind.com"),
        InspirationLink("motherfuckingwebsite.com", "https://motherfuckingwebsite.com"),
    )
}
