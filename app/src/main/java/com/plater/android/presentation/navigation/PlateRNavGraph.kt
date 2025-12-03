package com.plater.android.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.plater.android.core.datastore.UserPreferencesManager
import com.plater.android.domain.models.AuthModel
import com.plater.android.presentation.screens.auth.LoginScreen
import com.plater.android.presentation.screens.main.MainScreen
import com.plater.android.presentation.screens.onboarding.OnboardingScreen
import com.plater.android.presentation.screens.splash.SplashScreen
import com.plater.android.presentation.shared.RecipeSharedViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val SPLASH_DELAY_MS = 3000L

/**
 * Central navigation host that routes users through splash, onboarding, auth,
 * and home depending on onboarding completion and encrypted auth session state.
 */
@Composable
fun PlateRNavGraph(
    userPreferencesManager: UserPreferencesManager,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()

    val onboardingCompleted by produceState<Boolean?>(initialValue = null, userPreferencesManager) {
        userPreferencesManager.hasCompletedOnboarding.collect { completed ->
            value = completed
        }
    }

    // Encrypted auth session sourced from secure DataStore storage.
    val authSession by produceState<AuthModel?>(initialValue = null, userPreferencesManager) {
        userPreferencesManager.authSession().collect { session ->
            value = session
        }
    }

    val navigationHandled = remember { mutableStateOf(false) }

    NavHost(
        navController = navController,
        startDestination = ScreenRoutes.SplashScreenRoute,
        modifier = modifier
    ) {

        composable<ScreenRoutes.SplashScreenRoute> {
            SplashScreen()

            LaunchedEffect(onboardingCompleted, authSession) {
                val completed = onboardingCompleted
                val session = authSession

                // Only navigate from splash if we're actually on the splash screen
                // This prevents navigation when activity is recreated (e.g., theme change)
                // Check if back stack has more than just splash (meaning we've navigated away)
                val backStackEntry = navController.currentBackStackEntry
                val hasNavigatedAway = backStackEntry?.destination?.route?.let { route ->
                    !route.contains("SplashScreenRoute", ignoreCase = true)
                } ?: false

                if (!navigationHandled.value && completed != null && !hasNavigatedAway) {
                    delay(SPLASH_DELAY_MS)

                    // Double-check we're still on splash after delay
                    val stillOnSplash =
                        navController.currentBackStackEntry?.destination?.route?.let { route ->
                            route.contains("SplashScreenRoute", ignoreCase = true)
                        } ?: false

                    if (stillOnSplash) {
                        val destination = when {
                            session != null -> ScreenRoutes.MainSubGraph.MainGraphRoute
                            completed -> ScreenRoutes.AuthSubGraph.AuthGraphRoute
                            else -> ScreenRoutes.OnboardingScreenRoute
                        }

                        navigationHandled.value = true
                        navController.navigate(destination) {
                            popUpTo<ScreenRoutes.SplashScreenRoute> {
                                inclusive = true
                            }
                        }
                    }
                }
            }
        }

        composable<ScreenRoutes.OnboardingScreenRoute> {
            OnboardingScreen(
                onSkipClick = {
                    coroutineScope.launch {
                        userPreferencesManager.setOnboardingCompleted(completed = true)
                    }
                    navController.navigate(ScreenRoutes.AuthSubGraph.AuthGraphRoute) {
                        popUpTo<ScreenRoutes.OnboardingScreenRoute> { inclusive = true }
                    }
                }
            )
        }

        navigation<ScreenRoutes.AuthSubGraph.AuthGraphRoute>(
            startDestination = ScreenRoutes.AuthSubGraph.LoginScreenRoute
        ) {
            composable<ScreenRoutes.AuthSubGraph.LoginScreenRoute> {
                LoginScreen(
                    onSignInSuccess = {
                        navController.navigate(ScreenRoutes.MainSubGraph.MainGraphRoute) {
                            popUpTo<ScreenRoutes.AuthSubGraph.AuthGraphRoute> { inclusive = true }
                        }
                    }
                )
            }
        }

        navigation<ScreenRoutes.MainSubGraph.MainGraphRoute>(
            startDestination = ScreenRoutes.MainSubGraph.MainScreenRoute
        ) {
            composable<ScreenRoutes.MainSubGraph.MainScreenRoute> {
                val recipeSharedViewModel: RecipeSharedViewModel = hiltViewModel()
                MainScreen(
                    authSession = authSession,
                    onLogout = {
                        navController.navigate(ScreenRoutes.AuthSubGraph.AuthGraphRoute) {
                            popUpTo<ScreenRoutes.MainSubGraph.MainGraphRoute> {
                                inclusive = true
                            }
                        }
                    },
                    recipeSharedViewModel = recipeSharedViewModel
                )
            }
        }

    }
}

