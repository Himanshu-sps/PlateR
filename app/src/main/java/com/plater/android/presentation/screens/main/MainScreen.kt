package com.plater.android.presentation.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.plater.android.R
import com.plater.android.domain.models.AuthModel
import com.plater.android.presentation.navigation.AppBottomTabBar
import com.plater.android.presentation.navigation.BottomNavItem
import com.plater.android.presentation.navigation.ScreenRoutes
import com.plater.android.presentation.navigation.isSameRoute
import com.plater.android.presentation.screens.account.AccountScreen
import com.plater.android.presentation.screens.bookmark.BookmarkScreen
import com.plater.android.presentation.screens.home.HomeScreen
import com.plater.android.presentation.screens.search.SearchScreen
import com.plater.android.presentation.shared.RecipeSharedViewModel

@Composable
fun MainScreen(
    authSession: AuthModel?,
    onLogout: () -> Unit,
    recipeSharedViewModel: RecipeSharedViewModel
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomNavItems = remember {
        listOf(
            BottomNavItem(
                route = ScreenRoutes.MainSubGraph.HomeScreenRoute,
                selectedIcon = R.drawable.ic_home_filled,
                unselectedIcon = R.drawable.ic_home,
                label = "Home"
            ),
            BottomNavItem(
                route = ScreenRoutes.MainSubGraph.SearchScreenRoute,
                selectedIcon = R.drawable.ic_search_filled,
                unselectedIcon = R.drawable.ic_search,
                label = "Search"
            ),
            BottomNavItem(
                route = ScreenRoutes.MainSubGraph.BookmarkScreenRoute,
                selectedIcon = R.drawable.ic_bookmark_filled,
                unselectedIcon = R.drawable.ic_bookmark,
                label = "Bookmark"
            ),
            BottomNavItem(
                route = ScreenRoutes.MainSubGraph.AccountScreenRoute,
                selectedIcon = R.drawable.ic_account_filled,
                unselectedIcon = R.drawable.ic_account,
                label = "Account"
            )
        )
    }

    // Watch for auth session changes and navigate to login if session is cleared
    LaunchedEffect(authSession) {
        if (authSession == null) {
            onLogout()
        }
    }

    Scaffold(
        modifier = Modifier.background(MaterialTheme.colorScheme.surface),
        containerColor = MaterialTheme.colorScheme.surface,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            AppBottomTabBar(
                items = bottomNavItems,
                currentDestination = currentDestination,
                onItemSelected = { route ->
                    if (!currentDestination.isSameRoute(route)) {
                        navController.navigate(route) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo<ScreenRoutes.MainSubGraph.HomeScreenRoute> {
                                saveState = true
                            }
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ScreenRoutes.MainSubGraph.HomeScreenRoute,
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
        ) {
            composable<ScreenRoutes.MainSubGraph.HomeScreenRoute> {
                HomeScreen(recipeSharedViewModel = recipeSharedViewModel)
            }
            composable<ScreenRoutes.MainSubGraph.SearchScreenRoute> {
                SearchScreen(recipeSharedViewModel = recipeSharedViewModel)
            }
            composable<ScreenRoutes.MainSubGraph.BookmarkScreenRoute> {
                BookmarkScreen(recipeSharedViewModel = recipeSharedViewModel)
            }
            composable<ScreenRoutes.MainSubGraph.AccountScreenRoute> {
                AccountScreen()
            }
        }
    }
}


