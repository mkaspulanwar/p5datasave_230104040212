package id.antasari.p5datasave_230104040212.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import id.antasari.p5datasave_230104040212.ui.screens.AboutScreen
import id.antasari.p5datasave_230104040212.ui.screens.FilesScreen
import id.antasari.p5datasave_230104040212.ui.screens.HomeScreen
import id.antasari.p5datasave_230104040212.ui.screens.SharedPrefsScreen

@Composable
fun NavGraph(
    nav: NavHostController,
    pad: PaddingValues,
    onDarkChanged: (Boolean) -> Unit // <- callback dari mainActivity
) {
    NavHost(
        navController = nav,
        startDestination = Dest.startRoute,
        modifier = Modifier.padding(pad)
    ) {
        composable(Dest.Home.route) { HomeScreen(nav) }
        composable(Dest.SP.route) { SharedPrefsScreen(onDarkChanged = onDarkChanged) }
        composable(Dest.Files.route) { FilesScreen() }
        composable(Dest.About.route) { AboutScreen() }
    }
}