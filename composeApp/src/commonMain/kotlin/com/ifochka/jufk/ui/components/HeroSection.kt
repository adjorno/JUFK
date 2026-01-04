package com.ifochka.jufk.ui.components

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HeroSection(
    title: String, // Note: The title string is now used for the parts of the hero text
    subtitle: String,
    modifier: Modifier = Modifier,
) {
    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        val isDesktop = maxWidth > 600.dp
        val horizontalPadding = if (isDesktop) 16.dp else 8.dp

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 64.dp, horizontal = horizontalPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val titleSize = if (isDesktop) 88.sp else 64.sp
            val titleLineHeight = if (isDesktop) 96.sp else 72.sp

            Text(
                text = buildAnnotatedString {
                    append("Just Use F*cking\n")
                    withStyle(
                        style = MaterialTheme.typography.displayLarge.toSpanStyle().copy(
                            color = MaterialTheme.colorScheme.primary,
                        ),
                    ) {
                        append("Kotlin") // Apply color only to the word
                    }
                    append(".\nPeriod.") // Append the period and the rest normally
                },
                fontSize = titleSize,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                lineHeight = titleLineHeight,
                color = MaterialTheme.colorScheme.onBackground,
            )

            Spacer(modifier = Modifier.height(24.dp))

            val subtitleModifier = if (isDesktop) {
                Modifier.fillMaxWidth(0.6f)
            } else {
                Modifier.fillMaxWidth()
            }

            Column(
                modifier = subtitleModifier,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = subtitle,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 32.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                )
            }
        }
    }
}
