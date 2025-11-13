package com.webianks.lazypizza.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.webianks.lazypizza.ui.components.CenteredBottomBar
import com.webianks.lazypizza.ui.components.LogoutDialog
import com.webianks.lazypizza.ui.components.NavigationRail
import com.webianks.lazypizza.ui.components.TopLevelNavBar

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    windowSizeClass: WindowSizeClass,
    mainNavController: NavController,
    cartViewModel: CartViewModel,
    menuViewModel: MenuViewModel,
    authViewModel: AuthViewModel,
    gridState: LazyStaggeredGridState,
    navigateToCart: Boolean
) {
    val bottomNavController = rememberNavController()
    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val cartLines by cartViewModel.lines.collectAsState()
    val authState by authViewModel.authState.collectAsState()
    var showLogoutDialog by remember { mutableStateOf(false) }

    if (navigateToCart) {
        LaunchedEffect(Unit) {
            bottomNavController.navigate(Destination.CART.route) {
                launchSingleTop = true
                restoreState = true
            }
        }
    }

    if (showLogoutDialog) {
        LogoutDialog(
            onConfirmLogout = {
                authViewModel.logout()
                showLogoutDialog = false
            },
            onDismiss = { showLogoutDialog = false }
        )
    }

    fun handleAccountClick() {
        if (authState !is AuthState.SignedIn) {
            mainNavController.navigate("auth")
        } else {
            showLogoutDialog = true
        }
    }

    val useNavRail = windowSizeClass.widthSizeClass > WindowWidthSizeClass.Compact

    Row {
        if (useNavRail) {
            NavigationRail(
                selectedIndex = when (currentDestination?.route) {
                    Destination.MENU.route -> 0
                    Destination.CART.route -> 1
                    Destination.HISTORY.route -> 2
                    else -> 0
                },
                onItemSelected = {
                    val route = Destination.entries[it].route
                    bottomNavController.navigate(route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                cartItemCount = cartLines.size
            )
        }

        Scaffold(
            topBar = {
                TopLevelNavBar(
                    modifier = Modifier
                        .statusBarsPadding()
                        .height(64.dp),
                    onHomeScreen = currentDestination?.route == Destination.MENU.route,
                    centerTitle = when (currentDestination?.route) {
                        Destination.CART.route -> "Cart"
                        Destination.HISTORY.route -> "Order History"
                        else -> ""
                    },
                    onAccountClick = ::handleAccountClick,
                    loggedIn = authState is AuthState.SignedIn
                )
            },
            bottomBar = {
                if (!useNavRail) {
                    CenteredBottomBar(
                        selectedIndex = when (currentDestination?.route) {
                            Destination.MENU.route -> 0
                            Destination.CART.route -> 1
                            Destination.HISTORY.route -> 2
                            else -> 0
                        },
                        onItemSelected = {
                            val route = Destination.entries[it].route
                            bottomNavController.navigate(route) {
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        cartItemCount = cartLines.sumOf { it.quantity }
                    )
                }
            }
        ) { padding ->
            NavHost(
                navController = bottomNavController,
                startDestination = Destination.MENU.route,
                modifier = Modifier.padding(padding)
            ) {
                composable(Destination.MENU.route) {
                    MenuScreen(
                        windowSizeClass = windowSizeClass,
                        cartViewModel = cartViewModel,
                        menuViewModel = menuViewModel,
                        gridState = gridState,
                        onPizzaSelected = { menuItem ->
                            mainNavController.navigate("details/${menuItem.id}")
                        }
                    )
                }
                composable(Destination.CART.route) {
                    CartScreen(
                        viewModel = cartViewModel,
                        windowSizeClass = windowSizeClass,
                        onNavigateToMenu = { bottomNavController.navigate(Destination.MENU.route) }
                    )
                }
                composable(Destination.HISTORY.route) { 
                    HistoryScreen(
                        windowSizeClass = windowSizeClass,
                        isUserLoggedIn = authState is AuthState.SignedIn,
                        onSignInClicked = { mainNavController.navigate("auth") } 
                    )
                }
            }
        }
    }
}