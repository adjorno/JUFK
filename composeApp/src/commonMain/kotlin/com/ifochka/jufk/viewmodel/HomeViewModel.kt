package com.ifochka.jufk.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.ifochka.jufk.data.Content
import com.ifochka.jufk.data.GoodnessLink
import com.ifochka.jufk.data.Limitation
import com.ifochka.jufk.data.PlatformSection
import com.ifochka.jufk.data.SocialLink
import com.ifochka.jufk.data.Video

/**
 * UI state for the home screen.
 */
data class HomeUiState(
    val heroTitle: String = Content.HERO_TITLE,
    val heroSubtitle: String = Content.HERO_SUBTITLE,
    val platformSections: List<PlatformSection> = Content.platformSections,
    val limitations: List<Limitation> = Content.limitations,
    val limitationsHeading: String = Content.LIMITATIONS_HEADING,
    val socialLinks: List<SocialLink> = Content.socialLinks,
    val footerAuthor: String = Content.FOOTER_AUTHOR,
    val makingOfHeading: String = Content.MAKING_OF_HEADING,
    val videos: List<Video> = Content.videos,
    val goodnessHeading: String = Content.GOODNESS_HEADING,
    val goodnessLinks: List<GoodnessLink> = Content.goodnessLinks,
)

/**
 * ViewModel for the home screen.
 */
class HomeViewModel {
    var uiState by mutableStateOf(HomeUiState())
        private set
}
