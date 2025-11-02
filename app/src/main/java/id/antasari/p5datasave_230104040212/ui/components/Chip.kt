package id.antasari.p5datasave_230104040212.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

@Composable
fun KevaChip(
    text: String,
    onGradient: Boolean = false // true jika ditempatkan di atas kartu gradasi
) {
    val container = if (onGradient) Color.White.copy(alpha = 0.16f)
    else MaterialTheme.colorScheme.surfaceVariant

    val content = if (onGradient) Color.White
    else MaterialTheme.colorScheme.onSurfaceVariant

    Surface(
        color = container,
        contentColor = content,
        shape = RoundedCornerShape(100),
        tonalElevation = if (onGradient) 0.dp else 1.dp
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            maxLines = 1
        )
    }
}