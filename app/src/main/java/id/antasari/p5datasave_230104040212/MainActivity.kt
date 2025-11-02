package id.antasari.p5datasave_230104040212

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Savings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.platform.LocalContext
import id.antasari.p5datasave_230104040212.data.prefs.PreferencesRepository
import id.antasari.p5datasave_230104040212.navigation.Dest
import id.antasari.p5datasave_230104040212.navigation.NavGraph
import id.antasari.p5datasave_230104040212.ui.theme.KevaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { KevaApp() }
    }
}
@Composable
private fun KevaApp() {
    val nav = rememberNavController()
    val ctx = LocalContext.current

    // Tidak perlu Saver - cukup remember repo sekali
    val repo = remember { PreferencesRepository(ctx) }

    // baca preferensi awal, lalu bisa berubah realtime
    var dark by rememberSaveable { mutableStateOf(repo.load().dark) }

    KevaTheme(darkTheme = dark) {
        Scaffold(
            topBar = { KevaTopBar() }, // Asumsi fungsi KevaTopBar() tersedia
            bottomBar = {
                NavigationBar {
                    val backStackEntry by nav.currentBackStackEntryAsState()
                    val currentRoute = backStackEntry?.destination?.route

                    Dest.all.forEach { d ->
                        NavigationBarItem(
                            selected = currentRoute == d.route,
                            onClick = {
                                nav.navigate(d.route) {
                                    launchSingleTop = true
                                    restoreState = true
                                    popUpTo(Dest.startRoute) { saveState = true }
                                }
                            },
                            icon = { Icon(d.icon, contentDescription = d.label) },
                            label = { Text(d.label) }
                        )
                    }
                }
            }
        ) { pad ->
            // Callback untuk mengubah tema saat toggle di SharedPreferences
            NavGraph(
                nav = nav,
                pad = pad,
                onDarkChanged = { isDark ->
                    dark = isDark
                    val current = repo.load()
                    repo.save(current.copy(dark = isDark))
                }
            )
        }
    }
}
// Bagian 2: Fungsi Composable untuk TopAppBar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun KevaTopBar() {
    CenterAlignedTopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Outlined.Savings, contentDescription = null)
                Spacer(Modifier.width(6.dp))
                Text("Keva", style = MaterialTheme.typography.titleMedium)
            }
        }
    )
}
