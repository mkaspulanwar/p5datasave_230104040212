package id.antasari.p5datasave_230104040212.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// ===== Brand colors =====
val KevaOrange = Color(0xFFDBA666) // SharedPrefs card
val KevaGreen = Color(0xFF4CAF50)
val KevaBlue = Color(0xFF87B7FF) // Files card / primary
val KevaPurple = Color(0xFF9B86FF) // Accent/tertiary
val KevaTeal = Color(0xFF81F4B6) // Secondary
val KevaBg = Color(0xFFF6F6F6)
val KevaOnBg = Color(0xFF1A1A1A)

// ===== Legacy Keva palette (optional) =====
val OldKevaGreen = Color(0xFF10B981) // primary
val OldKevaBlue = Color(0xFF3B82F6)  // secondary
val OldKevaTeal = Color(0xFF14B8A6)  // tertiary
val KevaOnPrimaryDark = Color(0xFF002116) // opsional

// ===== Light Theme =====
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

    surfaceVariant = Color(0xFFBEBEF5),
    onSurfaceVariant = Color(0xFF343155),
    outline = Color(0xFFB0BED1)
)

// ===== Dark Theme =====
val DarkColors = darkColorScheme(
    primary = KevaBlue,
    onPrimary = Color.White,
    secondary = KevaTeal,
    onSecondary = Color.White,
    tertiary = KevaPurple,

    background = Color(0xFF181220),
    onBackground = Color(0xFFE5E7EB),
    surface = Color(0xFF181827),
    onSurface = Color(0xFFE5E7EB),

    surfaceVariant = Color(0xFF1F2937),
    onSurfaceVariant = Color(0xFFC7D2FE),
    outline = Color(0xFF475569)
)