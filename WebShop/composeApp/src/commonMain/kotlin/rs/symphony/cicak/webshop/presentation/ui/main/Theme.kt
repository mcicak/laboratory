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
val Transparent = Color.Transparent
val RedBright = Color(0xFFFF0033)
val RedBrightFaded = RedBright.copy(alpha = 0.7f)
val Cyan = Color.Cyan
val Cyan3 = Color(0xFF73E7DC)
val Magenta = Color(0xFFFF33CC)
val PurpleBright = Color(0xFF5946AF)

val PurpleDark = Color(0xff1c114a)
val PinkNeon = Color(0xffea0871)

val CyanLight = Color(0xffcdfffa)
val CyanDark = Color(0xff0d69b1)
val PurpleDark2 = Color(0xff190c39)

// main background gradient
val PurpleBlack = Color(0xFF0F0E17)

// profile item gradient
val Pink2 = Color(0xfff612a3)
val Blue2 = Color(0xff2e68c1)

// product item gradient
val Cyan2 = Color(0xFF5BD8F1)
val Purple2 = Color(0xFF402E80)

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
