package com.ifochka.jufk

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.ifochka.jufk.theme.JUFKTheme
import com.ifochka.jufk.ui.components.FixedFooter
import com.ifochka.jufk.ui.screens.HomeScreen
import com.ifochka.jufk.viewmodel.HomeViewModel
import com.ifochka.kotrack.AnalyticsManager
import com.ifochka.kotrack.createAnalytics
import kotlinx.coroutines.launch

@Composable
fun App() {
    val scope = rememberCoroutineScope()
    val analyticsManager = remember {
        AnalyticsManager(
            analytics = createAnalytics(
                apiKey = BuildKonfig.POSTHOG_API_KEY,
                coroutineScope = scope,
            ),
        )
    }

    LaunchedEffect(analyticsManager) {
        analyticsManager.initialize(BuildKonfig.VERSION_NAME)
    }

    JUFKTheme {
        val viewModel = remember { HomeViewModel() }
        val snackbarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            Scaffold(
                snackbarHost = {
                    SnackbarHost(snackbarHostState) { data ->
                        Snackbar(
                            snackbarData = data,
                            containerColor = MaterialTheme.colorScheme.surface,
                            contentColor = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                },
                bottomBar = {
                    FixedFooter(
                        socialLinks = viewModel.uiState.socialLinks,
                        author = viewModel.uiState.footerAuthor,
                    )
                },
            ) { innerPadding ->
                HomeScreen(
                    heroTitle = viewModel.uiState.heroTitle,
                    heroSubtitle = viewModel.uiState.heroSubtitle,
                    codeSnippet = viewModel.uiState.codeSnippet,
                    platformSections = viewModel.uiState.platformSections,
                    limitations = viewModel.uiState.limitations,
                    limitationsHeading = viewModel.uiState.limitationsHeading,
                    limitationsExpanded = viewModel.uiState.limitationsExpanded,
                    onLimitationsToggle = { viewModel.toggleLimitations() },
                    onCodeCopy = { _ ->
                        scope.launch {
                            snackbarHostState.showSnackbar("Copied!")
                        }
                    },
                    modifier = Modifier.padding(innerPadding),
                )
            }
        }
    }
}
