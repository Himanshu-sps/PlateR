package com.plater.android.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plater.android.core.datastore.UserPreferencesManager
import com.plater.android.domain.models.AuthModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the Home screen that manages user session state.
 * Observes user authentication session from DataStore and provides it to the UI.
 *
 * @param userPreferencesManager Manages user preferences and authentication session data
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userPreferencesManager: UserPreferencesManager
) : ViewModel() {

    private val _userState = MutableStateFlow<AuthModel?>(null)
    val userState: StateFlow<AuthModel?> = _userState.asStateFlow()

    init {
        observeUserSession()
    }

    /**
     * Observes the user authentication session from DataStore.
     * Updates the user state whenever the session changes.
     * Handles errors silently by printing stack trace.
     */
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

