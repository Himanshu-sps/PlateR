package com.plater.android.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.plater.android.core.datastore.UserPreferencesManager
import com.plater.android.presentation.screens.auth.LoginScreen
import com.plater.android.presentation.screens.onboarding.OnboardingScreen
import com.plater.android.presentation.screens.splash.SplashScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val SPLASH_DELAY_MS = 3000L

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

    NavHost(
        navController = navController,
        startDestination = ScreenRoutes.SplashScreenRoute,
        modifier = modifier
    ) {
        composable<ScreenRoutes.SplashScreenRoute> {
            SplashScreen()

            LaunchedEffect(onboardingCompleted) {
                val completed = onboardingCompleted
                if (completed != null) {
                    delay(SPLASH_DELAY_MS)
                    val destination = if (completed) {
                        ScreenRoutes.AuthScreenRoute
                    } else {
                        ScreenRoutes.OnboardingScreenRoute
                    }

                    navController.navigate(destination) {
                        popUpTo<ScreenRoutes.SplashScreenRoute> {
                            inclusive = true
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
                    navController.navigate(ScreenRoutes.AuthScreenRoute) {
                        popUpTo<ScreenRoutes.OnboardingScreenRoute> { inclusive = true }
                    }
                }
            )
        }

        composable<ScreenRoutes.AuthScreenRoute> {
            LoginScreen()
        }
    }
}

