package com.webianks.lazypizza

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.webianks.lazypizza.screens.DetailsScreen
import com.webianks.lazypizza.screens.HomeScreen
import com.webianks.lazypizza.screens.sampleMenu

@Composable
fun AppNavigation(windowSizeClass: WindowSizeClass) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                windowSizeClass = windowSizeClass,
                onPizzaSelected = {
                    navController.navigate("details/${it.id}")
                }
            )
        }
        composable("details/{menuItemId}") { backStackEntry ->
            val menuItemId = backStackEntry.arguments?.getString("menuItemId")
            val menuItem = sampleMenu.find { it.id == menuItemId }
            if (menuItem != null) {
                DetailsScreen(
                    windowSizeClass = windowSizeClass,
                    menuItem = menuItem,
                    onNavigateBack = { navController.popBackStack() })
            }
        }
    }
}