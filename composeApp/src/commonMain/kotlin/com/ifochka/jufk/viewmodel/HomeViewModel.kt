package com.ifochka.jufk.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.ifochka.jufk.data.Content
import com.ifochka.jufk.data.Limitation
import com.ifochka.jufk.data.PlatformSection
import com.ifochka.jufk.data.SocialLink

/**
 * UI state for the home screen.
 */
data class HomeUiState(
    val heroTitle: String = Content.HERO_TITLE,
    val heroSubtitle: String = Content.HERO_SUBTITLE,
    val platformSections: List<PlatformSection> = Content.platformSections,
    val limitations: List<Limitation> = Content.limitations,
    val socialLinks: List<SocialLink> = Content.socialLinks,
    val footerAuthor: String = Content.FOOTER_AUTHOR,
    val limitationsExpanded: Boolean = false,
    val showCopiedToast: Boolean = false,
)

/**
 * ViewModel for the home screen.
 * Currently minimal - ready for future state management needs.
 */
class HomeViewModel {
    var uiState by mutableStateOf(HomeUiState())
        private set

    fun toggleLimitations() {
        uiState = uiState.copy(limitationsExpanded = !uiState.limitationsExpanded)
    }

    fun showCopiedToast() {
        uiState = uiState.copy(showCopiedToast = true)
    }

    fun dismissCopiedToast() {
        uiState = uiState.copy(showCopiedToast = false)
    }
}
