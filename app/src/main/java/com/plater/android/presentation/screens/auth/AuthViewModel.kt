package com.plater.android.presentation.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plater.android.core.datastore.UserPreferencesManager
import com.plater.android.core.utils.ValidationUtils
import com.plater.android.domain.models.ResultResource
import com.plater.android.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Business logic holder for the authentication screen. Validates credentials,
 * orchestrates the login use case, and emits UI state/effects for Compose.
 */
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val userPreferencesManager: UserPreferencesManager
) : ViewModel() {

    private val _authState = MutableStateFlow(AuthUIState())
    val authState = _authState.asStateFlow()

    private val _viewEffects = MutableSharedFlow<AuthSharedFlowEffect>()
    val viewEffects: SharedFlow<AuthSharedFlowEffect> = _viewEffects.asSharedFlow()

    /**
     * Entry point for UI intents exposed by [AuthUIEvents].
     */
    fun onEvent(authUIEvents: AuthUIEvents) {
        when (authUIEvents) {
            is AuthUIEvents.RequestUserLogin -> {
                if (validateInputs(authUIEvents.username, authUIEvents.password)) {
                    login(
                        username = authUIEvents.username,
                        password = authUIEvents.password
                    )
                }
            }
        }
    }

    /**
     * Runs client-side validation and emits field error ids when something fails.
     */
    private fun validateInputs(username: String, password: String): Boolean {
        val usernameValidation = ValidationUtils.validateUsername(username)
        val passwordValidation = ValidationUtils.validatePassword(password)

        return if (!usernameValidation.isValid || !passwordValidation.isValid) {
            viewModelScope.launch {
                _viewEffects.emit(
                    AuthSharedFlowEffect.ShowValidationErrors(
                        usernameErrorResId = usernameValidation.errorResId,
                        passwordErrorResId = passwordValidation.errorResId
                    )
                )
            }
            false
        } else {
            true
        }
    }

    /**
     * Executes [LoginUseCase], persists the auth session, and emits side-effects.
     */
    private fun login(username: String, password: String) {
        viewModelScope.launch {
            loginUseCase(username, password).collect { apiResource ->
                when (apiResource) {
                    is ResultResource.Loading -> {
                        _authState.update { it.copy(isLoading = true) }
                    }

                    is ResultResource.Error -> {
                        _authState.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                        _viewEffects.emit(
                            AuthSharedFlowEffect.ShowError(apiResource.message)
                        )
                    }

                    is ResultResource.Success -> {
                        _authState.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                        userPreferencesManager.saveAuthSession(apiResource.data)
                        _viewEffects.emit(AuthSharedFlowEffect.NavigateHome)
                    }
                }
            }
        }
    }
}