package com.ifochka.jufk.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ifochka.jufk.data.SocialLink
import com.ifochka.jufk.ui.theme.Dimensions
import jufk.composeapp.generated.resources.Res
import jufk.composeapp.generated.resources.github_mark_white
import jufk.composeapp.generated.resources.linkedin
import jufk.composeapp.generated.resources.x_logo
import org.jetbrains.compose.resources.painterResource

@Composable
fun FixedFooter(
    socialLinks: List<SocialLink>,
    modifier: Modifier = Modifier,
) {
    val uriHandler = LocalUriHandler.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = Dimensions.CONTENT_PADDING),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
        ) {
            Text(
                text = "Built by Mykhailo Dorokhin",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                modifier = Modifier
                    .align(Alignment.TopEnd),
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(32.dp)) {
                    socialLinks.forEach { link ->
                        val iconPainter = when (link.name) {
                            "GitHub" -> painterResource(Res.drawable.github_mark_white)
                            "X" -> painterResource(Res.drawable.x_logo)
                            "LinkedIn" -> painterResource(Res.drawable.linkedin)
                            else -> null
                        }
                        iconPainter?.let {
                            Image(
                                painter = it,
                                contentDescription = link.name,
                                modifier = Modifier
                                    .size(24.dp)
                                    .clickable { uriHandler.openUri(link.url) }
                                    .semantics { role = Role.Button },
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "v${com.ifochka.jufk.BuildKonfig.VERSION_NAME}",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                )
            }
        }
    }
}
