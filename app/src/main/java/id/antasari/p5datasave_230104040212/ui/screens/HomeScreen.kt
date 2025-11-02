package id.antasari.p5datasave_230104040212.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.Storage
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import id.antasari.p5datasave_230104040212.navigation.Dest
import id.antasari.p5datasave_230104040212.ui.components.GradientCard
import id.antasari.p5datasave_230104040212.ui.components.KevaChip
import id.antasari.p5datasave_230104040212.ui.components.InfoBanner
import id.antasari.p5datasave_230104040212.ui.theme.KevaBlue
import id.antasari.p5datasave_230104040212.ui.theme.KevaGreen

@Composable
fun HomeScreen(nav: NavController) {
    val scroll = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scroll)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // JUDUL RATA TENGAH
        Text(
            "Keep-Value Data Saver Lab",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface
        )
        KevaChip("Educational Practicum Project")

        // Card: SharedPreferences
        GradientCard(
            icon = Icons.Outlined.Storage,
            title = "SharedPreferences Demo",
            subtitle = "Save small data like name & NIM using key-value pairs",
            chips = listOf("Key-Value", "Persistent"),
            from = KevaGreen,
            to = KevaGreen.copy(alpha = .8f)
        ) { nav.navigate(Dest.SP.route) }

        // Card: File Handling
        GradientCard(
            icon = Icons.Outlined.Folder,
            title = "File Handling Demo",
            subtitle = "Write & read notes in internal storage",
            chips = listOf("Text Files", "Storage"),
            from = KevaBlue,
            to = KevaBlue.copy(alpha = .8f)
        ) { nav.navigate(Dest.Files.route) }

        // Info banner / quick tips kecil
        Spacer(Modifier.height(4.dp))
        InfoBanner("Tip: SharedPreferences cocok untuk data kecil (string/boolean). File lebih fleksibel untuk teks panjang.")
    }
}