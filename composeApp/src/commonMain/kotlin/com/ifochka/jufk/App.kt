package com.ifochka.jufk

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ifochka.jufk.theme.JUFKTheme

private const val WEBSITE_URL = "https://justusefuckingkotlin.com"
private const val GITHUB_URL = "https://github.com/adjorno/JUFK"
private const val PLAY_STORE_URL = "https://play.google.com/store/apps/details?id=com.ifochka.jufk"

@Composable
private fun LinkText(
    text: String,
    url: String,
    modifier: Modifier = Modifier,
) {
    val uriHandler = LocalUriHandler.current

    Text(
        text = text,
        fontSize = 14.sp,
        color = MaterialTheme.colorScheme.primary,
        textAlign = TextAlign.Center,
        modifier =
            modifier
                .clickable { uriHandler.openUri(url) }
                .semantics { role = Role.Button },
    )
}

@Composable
fun App() {
    JUFKTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
            ) {
                Spacer(modifier = Modifier.height(48.dp))

                Text(
                    text = "Just Use Fucking Kotlin",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "One language. One codebase. Every platform.",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(48.dp))

                Text(
                    text = "This exact UI is running on:",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(16.dp))

                val platforms = Platform.supportedPlatforms.map {
                    if (it == "Web") "$it (this page)" else it
                }

                platforms.forEach { platform ->
                    Text(
                        text = "- $platform",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Center,
                    )
                }

                Spacer(modifier = Modifier.height(48.dp))

                Text(
                    text = "All from the same Kotlin codebase.",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Stop hiring 5 teams. Learn Kotlin.",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(48.dp))

                Text(
                    text = "Get the app:",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(16.dp))

                LinkText(
                    text = "🌐 justusefuckingkotlin.com",
                    url = WEBSITE_URL,
                )

                Spacer(modifier = Modifier.height(8.dp))

                LinkText(
                    text = "📱 Google Play Store",
                    url = PLAY_STORE_URL,
                )

                Spacer(modifier = Modifier.height(8.dp))

                LinkText(
                    text = "💻 github.com/adjorno/JUFK",
                    url = GITHUB_URL,
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Built with Kotlin Multiplatform & Compose Multiplatform",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}
