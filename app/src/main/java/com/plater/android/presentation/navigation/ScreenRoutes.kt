package com.plater.android.presentation.navigation

import kotlinx.serialization.Serializable

sealed class ScreenRoutes {

    @Serializable
    data object SplashScreenRoute : ScreenRoutes()

    @Serializable
    data object OnboardingScreenRoute : ScreenRoutes()

    @Serializable
    data object AuthScreenRoute : ScreenRoutes()
}