package com.plater.android.presentation.screens.auth

/**
 * User-driven intents emitted from the authentication UI.
 */
sealed class AuthUIEvents {

    /**
     * Triggered when the user submits credentials via the sign-in button.
     */
    data class RequestUserLogin(
        val username: String,
        val password: String
    ) : AuthUIEvents()
}