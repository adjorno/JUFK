package com.ifochka.jufk.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ifochka.jufk.data.Content
import com.ifochka.jufk.data.GoodnessLink
import com.ifochka.jufk.data.Limitation
import com.ifochka.jufk.data.PlatformSection
import com.ifochka.jufk.data.Video
import com.ifochka.jufk.ui.components.HeroSection
import com.ifochka.jufk.ui.components.LimitationsCard
import com.ifochka.jufk.ui.components.LinkCard
import com.ifochka.jufk.ui.components.PlatformSectionCard
import com.ifochka.kotrack.getPlatformName

@Composable
fun HomeScreen(
    heroTitle: String,
    heroSubtitle: String,
    platformSections: List<PlatformSection>,
    limitations: List<Limitation>,
    limitationsHeading: String,
    makingOfHeading: String,
    videos: List<Video>,
    goodnessHeading: String,
    goodnessLinks: List<GoodnessLink>,
    onCodeCopy: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val uriHandler = LocalUriHandler.current
    val currentPlatform = getPlatformName()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        HeroSection(
            title = heroTitle,
            subtitle = heroSubtitle,
        )

        Text(
            text = "Why Kotlin?",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 64.dp, bottom = 16.dp),
        )

        BoxWithConstraints {
            val isDesktop = maxWidth > 600.dp
            val textModifier = if (isDesktop) {
                Modifier.fillMaxWidth(0.6f)
            } else {
                Modifier.fillMaxWidth()
            }
            Text(
                text = "See for yourself. This entire app—Web, iOS, Android, Desktop, " +
                    "and even a CLI tool—is built from a single Kotlin codebase. No magic. Just code.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                modifier = textModifier.padding(horizontal = if (isDesktop) 16.dp else 0.dp),
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        BoxWithConstraints(
            modifier = Modifier.widthIn(max = 1100.dp).padding(horizontal = 24.dp), // Constrain max width for the grid
        ) {
            val twoColumns = this.maxWidth > 800.dp // Our breakpoint

            if (twoColumns) {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    platformSections.chunked(2).forEach { rowItems ->
                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            rowItems.forEach { section ->
                                Box(modifier = Modifier.weight(1f)) {
                                    PlatformSectionCard(
                                        section = section,
                                        onCodeCopy = onCodeCopy,
                                        isCurrentPlatform = section.id.equals(currentPlatform, ignoreCase = true),
                                    )
                                }
                            }
                            if (rowItems.size == 1) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            } else {
                // 1-column layout for narrow screens
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    platformSections.forEach { section ->
                        PlatformSectionCard(
                            section = section,
                            onCodeCopy = onCodeCopy,
                            isCurrentPlatform = section.id.equals(currentPlatform, ignoreCase = true),
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { uriHandler.openUri(Content.GITHUB_URL) },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
            ),
            shape = RoundedCornerShape(8.dp),
        ) {
            Text("View on GitHub")
        }

        Text(
            text = "Check out the code, and don\'t forget to star the repo!",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
            modifier = Modifier.padding(top = 8.dp),
        )

        Spacer(modifier = Modifier.height(64.dp))

        Column(
            modifier = Modifier.widthIn(max = 800.dp).padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            LimitationsCard(
                heading = limitationsHeading,
                limitations = limitations,
            )

            Spacer(modifier = Modifier.height(64.dp))

            Text(
                text = goodnessHeading,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 24.dp),
            )

            goodnessLinks.forEach { link ->
                LinkCard(
                    title = link.title,
                    subtitle = link.subtitle,
                    icon = link.icon,
                    onClick = { uriHandler.openUri(link.url) },
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
