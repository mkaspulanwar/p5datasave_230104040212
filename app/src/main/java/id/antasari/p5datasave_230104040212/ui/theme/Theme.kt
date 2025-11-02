package id.antasari.p5datasave_230104040212.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColor = lightColorScheme(
    primary = KevaGreen,
    onPrimary = Color.White,
    secondary = KevaBlue,
    onSecondary = Color.White,
    tertiary = KevaTeal
    // sisanya biarkan default Material3
)

private val DarkColor = darkColorScheme(
    primary = KevaGreen,
    onPrimary = KevaOnPrimaryDark, // agar kontras di surface primary gelap
    secondary = KevaBlue,
    onSecondary = Color.White,
    tertiary = KevaTeal
)

@Composable
fun KevaTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val scheme = if (darkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = scheme,
        // Kalau Anda punya Typography/Shapes khusus, panggil di sini.
        content = content
    )
}
