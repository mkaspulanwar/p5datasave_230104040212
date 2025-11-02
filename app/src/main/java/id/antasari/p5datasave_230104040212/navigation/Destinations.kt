package id.antasari.p5datasave_230104040212.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Dest(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    data object Home : Dest("home", "Home", Icons.Outlined.Home)
    data object SP : Dest("sp", "SharedPrefs", Icons.Outlined.Settings)
    data object Files : Dest("files", "Files", Icons.Outlined.Folder)
    data object About : Dest("about", "About", Icons.Outlined.Info)

    companion object {
        val all = listOf(Home, SP, Files, About)
        const val startRoute = "home"
    }
}