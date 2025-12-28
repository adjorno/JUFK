package com.ifochka.jufk.data

/**
 * Represents a platform section with content and documentation link.
 */
data class PlatformSection(
    val id: String,
    val title: String,
    val content: String,
    val learnMoreUrl: String,
    val codeSnippet: String? = null,
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
    const val HERO_SUBTITLE = "One language. Everything."

    const val CODE_SNIPPET = """@Composable
fun Message() {
    Text("Just Use Fucking Kotlin")
}
// This works on Android, iOS, web, backend, desktop, CLI."""

    const val SHARE_MESSAGE = "One language. Everything."

    const val BREW_COMMAND = "brew install adjorno/jufk/jufk"

    const val GITHUB_URL = "https://github.com/adjorno/JUFK"
    const val WEBSITE_URL = "https://justusefuckingkotlin.com"

    val platformSections = listOf(
        PlatformSection(
            id = "backend",
            title = "Backend",
            content = "Ktor and Spring run half the internet. " +
                "Your backend is already Kotlin or switching to it. Share models with your frontend. Done.",
            learnMoreUrl = "https://ktor.io/",
        ),
        PlatformSection(
            id = "android",
            title = "Android",
            content = "Kotlin IS Android. Google killed Java. Jetpack Compose is the default. " +
                "If you're writing Android, you're writing Kotlin.",
            learnMoreUrl = "https://developer.android.com/kotlin",
        ),
        PlatformSection(
            id = "web",
            title = "Web",
            content = "Yes. Compose renders to Canvas. Same UI code as mobile. " +
                "No DOM, no JavaScript. Not for content sites.",
            learnMoreUrl = "https://kotlinlang.org/docs/wasm-overview.html",
        ),
        PlatformSection(
            id = "ios",
            title = "iOS",
            content = "Share all your logic or share all your UI. Your call. Skia adds 10MB. " +
                "Native performance. SwiftUI works if you want it.",
            learnMoreUrl = "https://www.jetbrains.com/kotlin-multiplatform/",
        ),
        PlatformSection(
            id = "cli",
            title = "CLI",
            content = "Native binaries. No JVM at runtime. Homebrew, apt, whatever. Faster than you think.",
            learnMoreUrl = "https://kotlinlang.org/docs/native-overview.html",
            codeSnippet = BREW_COMMAND,
        ),
        PlatformSection(
            id = "bots",
            title = "Bots",
            content = "Discord, Slack, Telegram. Ktor webhooks. Coroutines. Done.",
            learnMoreUrl = "https://github.com/kotlin-telegram-bot/kotlin-telegram-bot",
        ),
    )

    val limitations = listOf(
        "Gradle scares you",
        "SEO matters more than everything else",
        "10MB is unacceptable",
        "You refuse to learn anything new",
    )

    const val LIMITATIONS_HEADING = "You shouldn't use Kotlin if..."

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
