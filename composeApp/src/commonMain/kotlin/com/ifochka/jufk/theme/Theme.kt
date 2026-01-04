package com.ifochka.jufk.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val Purple = Color(0xFF7F52FF)
private val Orange = Color(0xFFF88909)

private val DarkColorScheme =
    darkColorScheme(
        primary = Purple,
        secondary = Orange,
        background = Color(0xFF121212), // A deeper black for the background
        surface = Color(0xFF1E1E1E), // A slightly lighter black for surfaces
        onPrimary = Color.White,
        onSecondary = Color.White,
        onBackground = Color.White,
        onSurface = Color.White,
    )

@Composable
fun JUFKTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        content = content,
    )
}
