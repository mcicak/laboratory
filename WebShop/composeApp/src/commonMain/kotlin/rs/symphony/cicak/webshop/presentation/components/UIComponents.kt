package rs.symphony.cicak.webshop.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.unit.dp
import rs.symphony.cicak.webshop.presentation.ui.main.Cyan
import rs.symphony.cicak.webshop.presentation.util.getPlatformPadding

@Composable
fun Title(modifier: Modifier = Modifier, text: String) {
    Text(
        modifier = Modifier.padding(16.dp, top = 16.dp + getPlatformPadding()),
        text = text,
        style = MaterialTheme.typography.h1.copy(
            color = Cyan,
            shadow = Shadow(
                color = Cyan.copy(alpha = 0.8f),
                offset = Offset(2f, 2f),
                blurRadius = 8f
            )
        )
    )
}