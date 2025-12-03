package com.plater.android.presentation.screens.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plater.android.core.datastore.UserPreferencesManager
import com.plater.android.domain.models.AuthModel
import com.plater.android.domain.models.ResultResource
import com.plater.android.domain.models.User
import com.plater.android.domain.usecase.GetCurrentUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val userPreferencesManager: UserPreferencesManager,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    private val _userState = MutableStateFlow<AuthModel?>(null)
    val userState: StateFlow<AuthModel?> = _userState.asStateFlow()

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    private val _isLoadingUser = MutableStateFlow(false)
    val isLoadingUser: StateFlow<Boolean> = _isLoadingUser.asStateFlow()

    private val _viewEffects = MutableSharedFlow<String>()
    val viewEffects: SharedFlow<String> = _viewEffects.asSharedFlow()

    init {
        observeUserSession()
    }

    private fun observeUserSession() {
        viewModelScope.launch {
            userPreferencesManager.authSession()
                .catch { exception ->
                    // Handle error if needed
                    exception.printStackTrace()
                }
                .collect { session ->
                    _userState.value = session
                }
        }
    }

    /**
     * Fetches the current authenticated user's information from the API using GetCurrentUserUseCase.
     */
    fun fetchCurrentUser() {
        viewModelScope.launch {
            getCurrentUserUseCase()
                .collect { resultResource ->
                    when (resultResource) {
                        is ResultResource.Loading -> {
                            _isLoadingUser.update { true }
                        }

                        is ResultResource.Error -> {
                            _isLoadingUser.update { false }
                            _viewEffects.emit(resultResource.message)
                        }

                        is ResultResource.Success -> {
                            _isLoadingUser.update { false }
                            _currentUser.update { resultResource.data }
                        }
                    }
                }
        }
    }

    fun logout() {
        viewModelScope.launch {
            userPreferencesManager.clearAuthSession()
        }
    }
}

