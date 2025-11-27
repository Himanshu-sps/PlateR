package com.plater.android.presentation.screens.auth

import androidx.annotation.StringRes

/**
 * One-off signals that the login UI reacts to (navigation, dialogs, toast).
 * Act as Shared flow
 */
sealed class AuthSharedFlowEffect {
    data object NavigateHome : AuthSharedFlowEffect()
    data class ShowValidationErrors(
        @StringRes val usernameErrorResId: Int?,
        @StringRes val passwordErrorResId: Int?
    ) : AuthSharedFlowEffect()

    data class ShowError(val message: String) : AuthSharedFlowEffect()
}