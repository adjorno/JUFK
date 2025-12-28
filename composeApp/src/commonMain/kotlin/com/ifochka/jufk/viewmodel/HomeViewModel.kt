package com.ifochka.jufk.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.ifochka.jufk.data.Content
import com.ifochka.jufk.data.PlatformSection
import com.ifochka.jufk.data.SocialLink

/**
 * UI state for the home screen.
 */
data class HomeUiState(
    val heroTitle: String = Content.HERO_TITLE,
    val heroSubtitle: String = Content.HERO_SUBTITLE,
    val codeSnippet: String = Content.CODE_SNIPPET,
    val platformSections: List<PlatformSection> = Content.platformSections,
    val limitations: List<String> = Content.limitations,
    val limitationsHeading: String = Content.LIMITATIONS_HEADING,
    val socialLinks: List<SocialLink> = Content.socialLinks,
    val footerAuthor: String = Content.FOOTER_AUTHOR,
    val limitationsExpanded: Boolean = false,
)

/**
 * ViewModel for the home screen.
 */
class HomeViewModel {
    var uiState by mutableStateOf(HomeUiState())
        private set

    fun toggleLimitations() {
        uiState = uiState.copy(limitationsExpanded = !uiState.limitationsExpanded)
    }
}
