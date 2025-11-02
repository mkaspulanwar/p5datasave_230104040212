package id.antasari.p5datasave_230104040212.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// Brand colors
val KevaGreen = Color(0xFF0DBA66) // SharedPreferences card
val KevaBlue = Color(0xFF2B7BFF) // Files card / primary
val KevaPurple = Color(0xFF9B6BFF) // Accent / tertiary
val KevaTeal = Color(0xFF14B8A6) // Secondary
val KevaBg = Color(0xFFF6F9FC)
val KevaOnBg = Color(0xFF0F172A)

val LightColors = lightColorScheme(
    primary = KevaBlue,
    onPrimary = Color.White,
    secondary = KevaTeal,
    onSecondary = Color.White,
    tertiary = KevaPurple,

    background = KevaBg,
    onBackground = KevaOnBg,
    surface = Color.White,
    onSurface = KevaOnBg,

    surfaceVariant = Color(0xFFE8EEF5),
    onSurfaceVariant = Color(0xFF334155),
    outline = Color(0xFFCCBD5E1)
)
val DarkColors = darkColorScheme(
    primary = KevaBlue,
    onPrimary = Color.White,
    secondary = KevaTeal,
    onSecondary = Color.White,
    tertiary = KevaPurple,

    background = Color(0xFF0B1220),
    onBackground = Color(0xFFE5E7EB),
    surface = Color(0xFF101827),
    onSurface = Color(0xFFE5E7EB),

    surfaceVariant = Color(0xFF1F2937),
    onSurfaceVariant = Color(0xFFC7D2FE),
    outline = Color(0xFF475569)
)