package com.ifochka.jufk.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.ifochka.jufk.data.PlatformSection
import com.ifochka.jufk.ui.components.HeroSection
import com.ifochka.jufk.ui.components.LimitationsCard
import com.ifochka.jufk.ui.components.PlatformSectionCard

@Composable
fun HomeScreen(
    heroTitle: String,
    heroSubtitle: String,
    platformSections: List<PlatformSection>,
    limitations: List<String>,
    limitationsHeading: String,
    limitationsExpanded: Boolean,
    onLimitationsToggle: () -> Unit,
    onCodeCopy: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val uriHandler = LocalUriHandler.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
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

        Text(
            text = "See for yourself. This entire app—Web, iOS, Android, Desktop, and even a CLI tool—is built " +
                "from a single Kotlin codebase. No magic. Just code.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
            modifier = Modifier.padding(horizontal = 16.dp),
        )

        Spacer(modifier = Modifier.height(48.dp))

        platformSections.forEach { section ->
            PlatformSectionCard(
                section = section,
                onCodeCopy = onCodeCopy,
            )
            Spacer(modifier = Modifier.height(16.dp))
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
            text = "Check out the code, and don't forget to star the repo!",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
            modifier = Modifier.padding(top = 8.dp),
        )

        Spacer(modifier = Modifier.height(64.dp))

        LimitationsCard(
            heading = limitationsHeading,
            limitations = limitations,
            expanded = limitationsExpanded,
            onToggle = onLimitationsToggle,
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}
