package rs.symphony.cicak.webshop.presentation.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import rs.symphony.cicak.webshop.presentation.ui.main.Cyan

@Composable
fun Title(modifier: Modifier = Modifier, text: String) {
    Text(
        modifier = modifier,
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