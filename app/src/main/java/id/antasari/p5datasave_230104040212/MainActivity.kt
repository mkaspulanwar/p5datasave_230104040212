package id.antasari.p5datasave_230104040212

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Savings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import id.antasari.p5datasave_230104040212.data.prefs.PreferencesRepository
import id.antasari.p5datasave_230104040212.navigation.Dest
import id.antasari.p5datasave_230104040212.navigation.NavGraph
import id.antasari.p5datasave_230104040212.navigation.navigateSingleTopTo
import id.antasari.p5datasave_230104040212.ui.theme.KevaTheme


private val APP_BAR_HEIGHT = 128.dp
private val LOGO_BADGE_SIZE = 56.dp
private val LOGO_BADGE_PADDING = 12.dp

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
    val repo = remember { PreferencesRepository(ctx) }

    // Simpan tema gelap/terang
    var dark by rememberSaveable { mutableStateOf(repo.load().dark) }

    // Cek rute aktif
    val backStackEntry by nav.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    KevaTheme(darkTheme = dark) {
        Scaffold(
            topBar = { KevaTopBar() },
            bottomBar = {
                NavigationBar {
                    // ✅ FIXED: gunakan Dest.all, bukan Dest.allForEACH
                    Dest.all.forEach { d ->
                        NavigationBarItem(
                            selected = currentRoute == d.route,
                            onClick = { nav.navigateSingleTopTo(d.route) },
                            icon = { Icon(d.icon, contentDescription = d.label) },
                            label = { Text(d.label) }
                        )
                    }
                }
            }
        ) { pad ->
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

@Composable
private fun KevaTopBar() {
    val ctx = LocalContext.current
    val logoDrawableId: Int = run {
        val pkgName = ctx.packageName
        fun findId(name: String, type: String): Int {
            return ctx.resources.getIdentifier(name, type, pkgName).takeIf { it != 0 } ?: 0
        }

        findId("ic_keva_foreground", "drawable")
            .takeIf { it != 0 }
            ?: findId("ic_launcher_foreground", "drawable").takeIf { it != 0 }
            ?: findId("ic_keva", "drawable").takeIf { it != 0 }
            ?: findId("logo_keva", "drawable").takeIf { it != 0 }
            ?: 0
    }

    Surface(
        tonalElevation = 3.dp,
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.statusBars)
                .height(APP_BAR_HEIGHT)
                .padding(top = 8.dp, bottom = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primary
            ) {
                if (logoDrawableId != 0) {
                    Icon(
                        painter = painterResource(id = logoDrawableId),
                        contentDescription = "Keva",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .size(LOGO_BADGE_SIZE)
                            .padding(LOGO_BADGE_PADDING)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Outlined.Savings,
                        contentDescription = "Keva",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .size(LOGO_BADGE_SIZE)
                            .padding(LOGO_BADGE_PADDING)
                    )
                }
            }
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Keva",
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}