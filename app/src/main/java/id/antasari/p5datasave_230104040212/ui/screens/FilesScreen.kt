package id.antasari.p5datasave_230104040212.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FilesScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "Files",
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            "Placeholder textarea + Save/Read/Delete.",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}