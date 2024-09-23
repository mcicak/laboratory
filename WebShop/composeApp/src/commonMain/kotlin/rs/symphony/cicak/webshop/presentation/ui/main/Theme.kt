package rs.symphony.cicak.webshop.presentation.ui.main

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


// Define the purple-based color palette
private val LightPurpleColorPalette = lightColors(
    primary = Color(0xFF6200EA),
    primaryVariant = Color(0xFF3700B3),
    secondary = Color(0xFF03DAC6),
    background = Color(0xFFFFFFFF),
    surface = Color(0xFFF2E7FE),
    error = Color(0xFFB00020),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black
)

@Composable
fun MyAppTheme(
    darkTheme: Boolean = false, // Switch between dark and light mode
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        LightPurpleColorPalette
    } else {
        LightPurpleColorPalette
    }

    MaterialTheme(
        colors = colors,
        content = content
    )
}
