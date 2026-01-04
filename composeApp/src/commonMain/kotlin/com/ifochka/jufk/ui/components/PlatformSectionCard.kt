package com.ifochka.jufk.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ifochka.jufk.data.Cta
import com.ifochka.jufk.data.PlatformSection

@Composable
fun PlatformSectionCard(
    section: PlatformSection,
    onCodeCopy: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val uriHandler = LocalUriHandler.current
    val clipboardManager = LocalClipboardManager.current

    val borderBrush = if (section.isHighlighted) {
        Brush.verticalGradient(
            colors = listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary),
        )
    } else {
        SolidColor(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
    }

    val iconTint = when (section.id) {
        "wasm" -> Color(0xFFD500F9) // Pink
        "android" -> Color(0xFF00C853) // Green
        "ios" -> Color.White
        "desktop" -> Color(0xFF2979FF) // Blue
        "cli" -> Color(0xFFBDBDBD) // Grey
        else -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                brush = borderBrush,
                shape = RoundedCornerShape(12.dp),
            ).background(
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
                shape = RoundedCornerShape(12.dp),
            ).clip(RoundedCornerShape(12.dp))
            .padding(24.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = section.icon,
                contentDescription = section.title,
                tint = iconTint,
                modifier = Modifier.size(24.dp),
            )
            Text(
                text = section.title,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(start = 12.dp),
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = section.content,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            lineHeight = 22.sp,
        )

        Spacer(modifier = Modifier.height(24.dp))

        when (val cta = section.cta) {
            is Cta.Link -> {
                Text(
                    text = cta.text,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable { uriHandler.openUri(cta.url) },
                )
            }
            is Cta.Button -> {
                Button(
                    onClick = { uriHandler.openUri(cta.url) },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.onSurface,
                    ),
                ) {
                    cta.icon?.let { icon ->
                        Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(18.dp))
                    }
                    Text(cta.text, modifier = Modifier.padding(start = 8.dp))
                }
            }
            is Cta.Code -> {
                CodeBlock(code = cta.code, onCopy = {
                    clipboardManager.setText(AnnotatedString(it))
                    onCodeCopy(it)
                })
            }
        }
    }
}

@Composable
private fun CodeBlock(
    code: String,
    onCopy: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color.Black,
                shape = RoundedCornerShape(8.dp),
            ).border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                shape = RoundedCornerShape(8.dp),
            ).clip(RoundedCornerShape(8.dp))
            .clickable { onCopy(code) }
            .padding(horizontal = 16.dp, vertical = 12.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = code,
                fontFamily = FontFamily.Monospace,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 14.sp,
            )
            Text(
                text = "Copy",
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                fontSize = 12.sp,
            )
        }
    }
}
