package rs.symphony.cicak.webshop.presentation.ui.main

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import webshop.composeapp.generated.resources.AcPlus_IBM_VGA_9x16
import webshop.composeapp.generated.resources.Res
import webshop.composeapp.generated.resources.Road_Rage

// Colors
val Pink = Color(color = 0xFFFF0033)
val PinkFaded = Pink.copy(alpha = 0.7f)
val PinkNeon = Color(color = 0xFFEE00FF)
val Cyan = Color.Cyan
val Transparent = Color.Transparent
val Cyan2 = Color(color = 0xFF73E7DC)
val Purple2 = Color(color = 0xFF5946AF)

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
        content = content,
        typography = Typography(
            defaultFontFamily = FontFamily(Font(Res.font.AcPlus_IBM_VGA_9x16)),

            h1 = TextStyle(
                fontFamily = FontFamily(Font(Res.font.Road_Rage)),
                fontSize = 32.sp
            ),

            subtitle2 = TextStyle(
                fontFamily = FontFamily(Font(Res.font.AcPlus_IBM_VGA_9x16)),
                fontSize = 20.sp
            ),
        )
    )
}
