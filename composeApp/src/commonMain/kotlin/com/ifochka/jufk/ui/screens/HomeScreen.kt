package com.ifochka.jufk.ui.screens

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ifochka.jufk.data.Content
import com.ifochka.jufk.data.Limitation
import com.ifochka.jufk.data.PlatformSection
import com.ifochka.jufk.ui.components.HeroSection
import com.ifochka.jufk.ui.components.LimitationsCard
import com.ifochka.jufk.ui.components.PlatformSectionCard

@Composable
fun HomeScreen(
    heroTitle: String,
    heroSubtitle: String,
    platformSections: List<PlatformSection>,
    limitations: List<Limitation>,
    limitationsExpanded: Boolean,
    onLimitationsToggle: () -> Unit,
    onCodeCopy: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val uriHandler = LocalUriHandler.current
    val clipboardManager = LocalClipboardManager.current

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

        platformSections.forEach { section ->
            PlatformSectionCard(
                section = section,
                onCodeCopy = { code ->
                    clipboardManager.setText(AnnotatedString(code))
                    onCodeCopy(code)
                },
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        Spacer(modifier = Modifier.height(8.dp))

        LimitationsCard(
            limitations = limitations,
            expanded = limitationsExpanded,
            onToggle = onLimitationsToggle,
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Star on GitHub",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier.clickable { uriHandler.openUri(Content.GITHUB_URL) },
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Help get jufk into Homebrew core!",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Built with Kotlin Multiplatform & Compose Multiplatform",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}
