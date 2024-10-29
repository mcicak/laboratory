package rs.symphony.cicak.webshop.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun getPlatformPadding(): Dp {
    return if (isAndroid()) 16.dp else 0.dp
}

expect fun isAndroid(): Boolean
expect fun isApple(): Boolean
