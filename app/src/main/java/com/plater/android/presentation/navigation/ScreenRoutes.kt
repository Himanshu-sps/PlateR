package com.plater.android.presentation.navigation

import kotlinx.serialization.Serializable

sealed class ScreenRoutes {

    @Serializable
    data object SplashScreenRoute : ScreenRoutes()

    @Serializable
    data object OnboardingScreenRoute : ScreenRoutes()

    //================ Auth Graph ================//
    sealed class AuthSubGraph : ScreenRoutes() {
        @Serializable
        data object AuthGraphRoute : AuthSubGraph()

        @Serializable
        data object LoginScreenRoute : AuthSubGraph()
    }

    sealed class MainSubGraph : ScreenRoutes() {
        @Serializable
        data object MainGraphRoute : MainSubGraph()

        @Serializable
        data object MainScreenRoute : MainSubGraph()

        @Serializable
        data object HomeScreenRoute : MainSubGraph()

        @Serializable
        data object SearchScreenRoute : MainSubGraph()

        @Serializable
        data object BookmarkScreenRoute : MainSubGraph()

        @Serializable
        data object AccountScreenRoute : MainSubGraph()
    }

}