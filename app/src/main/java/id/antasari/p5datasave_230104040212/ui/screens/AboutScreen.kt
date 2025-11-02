package id.antasari.p5datasave_230104040212.ui.screens

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Savings
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import id.antasari.p5datasave_230104040212.ui.components.InfoBanner
import id.antasari.p5datasave_230104040212.ui.components.KevaChip

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AboutScreen() {
    val ctx = LocalContext.current
    val pkg = ctx.packageName

    // Ambil versionName & versionCode via PackageManager (tanpa BuildConfig)
    val pm = ctx.packageManager
    val pInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        pm.getPackageInfo(pkg, PackageManager.PackageInfoFlags.of(0))
    } else {
        @Suppress("DEPRECATION")
        pm.getPackageInfo(pkg, 0)
    }

    val versionName = pInfo.versionName ?: "-"
    val versionCode: Long = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        pInfo.longVersionCode
    } else {
        @Suppress("DEPRECATION")
        pInfo.versionCode.toLong()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()) // <- bikin halaman About bisa scroll
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // Kartu utama About
        Surface(shape = MaterialTheme.shapes.large, tonalElevation = 1.dp) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {

                // Judul rata tengah
                Text(
                    "About Keva",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                // Header ikon + nama app
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Savings,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.width(8.dp))
                    Column {
                        Text("<Keva App>", style = MaterialTheme.typography.titleSmall)
                        Text(
                            "Keep-Value Data Saver",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // App info
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            tonalElevation = 0.dp
        ) {
            Column(
                Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                RowInfo("Package", pkg)
                RowInfo("Version", "$versionName ($versionCode)")
            }
        }

        // Quick Tips (chips)
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Outlined.Info,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.width(6.dp))
                Text("Quick Tips", style = MaterialTheme.typography.titleSmall)
            }

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                KevaChip("SharedPreferences = key-value")
                KevaChip("Cook: data kecil")
                KevaChip("File Handling = teks panjang")
                KevaChip("Internal storage (aman)")
                KevaChip("Tanpa runtime permission")
                KevaChip("Material 3 + Compose")
            }
        }

        // Banner catatan singkat
        InfoBanner(
            "Praktikum menekankan pemisahan layer: UI + ViewModel + Repository agar kode mudah diuji & dipelihara."
        )

        // Share App
        FilledTonalButton(
            onClick = {
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "Keva App – praktik menyimpan data sederhana (SharedPreferences & File Handling). " +
                                "Package: $pkg, Version: $versionName ($versionCode)."
                    )
                }
                ctx.startActivity(Intent.createChooser(intent, "Share Keva App"))
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Share App")
        }

        // Kartu ringkas branding/tujuan (ikut scroll)
        Surface(
            shape = MaterialTheme.shapes.large,
            tonalElevation = 1.dp,
            color = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer
        ) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("Branding & Goal", style = MaterialTheme.typography.titleSmall)
                Text(
                    "Keva membantu memahami perbedaan penyimpanan data sederhana: " +
                            "saat memilih key-value vs file teks, sekaligus praktik arsitektur bersih pada Android Compose.",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        Spacer(Modifier.height(4.dp))
    }
}

@Composable
private fun RowInfo(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, style = MaterialTheme.typography.labelMedium)
        Text(value, style = MaterialTheme.typography.bodySmall, color = Color.Unspecified)
    }
}