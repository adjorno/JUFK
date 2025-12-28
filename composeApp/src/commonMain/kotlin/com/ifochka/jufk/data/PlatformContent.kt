package com.ifochka.jufk.data

/**
 * Represents a platform section with its benefits and documentation link.
 */
data class PlatformSection(
    val id: String,
    val title: String,
    val benefits: List<String>,
    val learnMoreUrl: String,
    val codeSnippet: String? = null,
)

/**
 * Represents a limitation with title and description.
 */
data class Limitation(
    val title: String,
    val description: String,
)

/**
 * Social link for the footer.
 */
data class SocialLink(
    val name: String,
    val url: String,
    val icon: String,
)

/**
 * All content data for the app.
 */
object Content {
    const val HERO_TITLE = "Just Use Fucking Kotlin"
    const val HERO_SUBTITLE = "One language. One codebase. Every platform."

    const val SHARE_MESSAGE = "One Kotlin codebase. Web, Android, iOS, Desktop.\njustusefuckingkotlin.com"

    const val BREW_COMMAND = "brew tap adjorno/jufk https://github.com/adjorno/JUFK && brew install jufk"

    const val GITHUB_URL = "https://github.com/adjorno/JUFK"
    const val WEBSITE_URL = "https://justusefuckingkotlin.com"

    val platformSections = listOf(
        PlatformSection(
            id = "web",
            title = "Web (WASM)",
            benefits = listOf(
                "Compose renders directly to Canvas - no DOM manipulation",
                "Same exact UI code runs on mobile and desktop",
                "No JavaScript frameworks needed - pure Kotlin",
                "Full access to Kotlin coroutines and flows",
            ),
            learnMoreUrl = "https://kotlinlang.org/docs/wasm-overview.html",
        ),
        PlatformSection(
            id = "backend",
            title = "Backend",
            benefits = listOf(
                "Ktor: Lightweight, coroutines-first, JetBrains-backed",
                "Spring: Enterprise-grade with Kotlin-first features",
                "Share data models between frontend and backend",
                "Full ecosystem of Kotlin libraries available",
            ),
            learnMoreUrl = "https://ktor.io/",
        ),
        PlatformSection(
            id = "android",
            title = "Android",
            benefits = listOf(
                "Jetpack Compose is Google's recommended UI toolkit",
                "First-class Kotlin support - no Java boilerplate",
                "100% of new Android features ship with Kotlin APIs",
                "Massive community and library ecosystem",
            ),
            learnMoreUrl = "https://developer.android.com/kotlin",
        ),
        PlatformSection(
            id = "ios",
            title = "iOS",
            benefits = listOf(
                "Compose Multiplatform renders native iOS UI",
                "Alternative: share business logic, use SwiftUI for UI",
                "Native performance through Kotlin/Native compilation",
                "Seamless Xcode integration for debugging",
            ),
            learnMoreUrl = "https://www.jetbrains.com/kotlin-multiplatform/",
        ),
        PlatformSection(
            id = "cli",
            title = "CLI Tools",
            benefits = listOf(
                "Native binaries - no JVM required at runtime",
                "Distribute via Homebrew, apt, or direct download",
                "Fast startup, small binary size",
                "Full access to system APIs",
            ),
            learnMoreUrl = "https://kotlinlang.org/docs/native-overview.html",
            codeSnippet = BREW_COMMAND,
        ),
        PlatformSection(
            id = "bots",
            title = "Bots",
            benefits = listOf(
                "Kotlin DSLs make bot logic readable",
                "Ktor for webhook endpoints",
                "Coroutines for async message handling",
                "Share logic across multiple bot platforms",
            ),
            learnMoreUrl = "https://github.com/kotlin-telegram-bot/kotlin-telegram-bot",
        ),
    )

    val limitations = listOf(
        Limitation(
            title = "Initial Setup Complexity",
            description = "Gradle configuration can be overwhelming. Expect 30-60 minutes for first project setup.",
        ),
        Limitation(
            title = "Web: Not SEO Friendly",
            description = "Canvas-based rendering means content isn't indexable by search engines.",
        ),
        Limitation(
            title = "iOS: Bundle Size Overhead",
            description = "Skia graphics library adds 10-15MB to app size.",
        ),
        Limitation(
            title = "macOS: No App Store",
            description = "Compose Desktop apps can't be published to Mac App Store yet.",
        ),
        Limitation(
            title = "Desktop: Larger Bundle",
            description = "Bundled JRE adds ~100-150MB to installers.",
        ),
        Limitation(
            title = "Learning Curve for iOS Devs",
            description = "Swift developers need to learn Kotlin syntax and Gradle build system.",
        ),
    )

    val socialLinks = listOf(
        SocialLink(
            name = "GitHub",
            url = "https://github.com/adjorno",
            icon = "github",
        ),
        SocialLink(
            name = "X",
            url = "https://x.com/adjorno",
            icon = "x",
        ),
        SocialLink(
            name = "LinkedIn",
            url = "https://linkedin.com/in/adjorno",
            icon = "linkedin",
        ),
    )

    const val FOOTER_AUTHOR = "@adjorno"
}
