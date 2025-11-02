package id.antasari.p5datasave_230104040212.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination

/**
 * Navigasi yang konsisten untuk BottomBar & card:
 * - popUpTo ke startDestination (saveState)
 * - launchSingleTop (hindari duplikasi)
 * - restoreState (balik ke state lama jika ada)
 */
fun NavController.navigateSingleTopTo(route: String) {
    this.navigate(route) {
        popUpTo(this@navigateSingleTopTo.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}