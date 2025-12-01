package com.plater.android.presentation.screens.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plater.android.core.datastore.UserPreferencesManager
import com.plater.android.domain.models.AuthSession
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val userPreferencesManager: UserPreferencesManager
) : ViewModel() {

    private val _userState = MutableStateFlow<AuthSession?>(null)
    val userState: StateFlow<AuthSession?> = _userState.asStateFlow()

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
}

