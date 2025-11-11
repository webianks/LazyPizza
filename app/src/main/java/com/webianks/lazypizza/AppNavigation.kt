package com.webianks.lazypizza

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.webianks.lazypizza.data.MenuItem
import com.webianks.lazypizza.ui.screens.AuthScreen
import com.webianks.lazypizza.ui.screens.AuthViewModel
import com.webianks.lazypizza.ui.screens.CartViewModel
import com.webianks.lazypizza.ui.screens.DetailsScreen
import com.webianks.lazypizza.ui.screens.HomeScreen
import com.webianks.lazypizza.ui.screens.ItemDetailsViewModel
import com.webianks.lazypizza.ui.screens.MenuViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AppNavigation(windowSizeClass: WindowSizeClass) {
    val navController = rememberNavController()
    val context = LocalContext.current.applicationContext
    val factory = GenericCoreModule.factory(context)
    val cartViewModel: CartViewModel = viewModel(factory = factory)
    val menuViewModel: MenuViewModel = viewModel(factory = factory)
    val itemDetailsViewModel: ItemDetailsViewModel = viewModel(factory = factory)
    val authViewModel: AuthViewModel =
        viewModel(factory = GenericCoreModule.AuthViewModelFactory(context))
    val menuScreenState = rememberLazyStaggeredGridState()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable(
            "home?navigateToCart={navigateToCart}",
            arguments = listOf(navArgument("navigateToCart") {
                type = NavType.BoolType
                defaultValue = false
            })
        ) { backStackEntry ->
            HomeScreen(
                windowSizeClass = windowSizeClass,
                mainNavController = navController,
                cartViewModel = cartViewModel,
                menuViewModel = menuViewModel,
                authViewModel = authViewModel,
                gridState = menuScreenState,
                navigateToCart = backStackEntry.arguments?.getBoolean("navigateToCart") ?: false
            )
        }
        composable("details/{menuItemId}") { backStackEntry ->
            val menuItemId = backStackEntry.arguments?.getString("menuItemId")
            val menu by menuViewModel.all.collectAsState()
            val menuItem = menu.find { it.id == menuItemId }
            if (menuItem is MenuItem.Configurable) {
                DetailsScreen(
                    windowSizeClass = windowSizeClass,
                    menuItem = menuItem,
                    onNavigateBack = { navController.popBackStack() },
                    onAddToCart = { navController.popBackStack() },
                    itemDetailsViewModel = itemDetailsViewModel
                )
            }
        }
        composable("auth") {
            AuthScreen(
                navController = navController,
                viewModel = authViewModel,
                windowSizeClass = windowSizeClass
            )
        }
    }
}