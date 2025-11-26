package com.plater.android.core.datastore

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Thin wrapper that exposes strongly-typed user preference queries while delegating
 * the actual persistence work to [DataStoreManager].
 */
@Singleton
class UserPreferencesManager @Inject constructor(
    private val dataStoreManager: DataStoreManager
) {

    companion object {
        private const val KEY_ONBOARDING_COMPLETED = "onboarding_completed"
    }

    val hasCompletedOnboarding: Flow<Boolean> =
        dataStoreManager.readBoolean(KEY_ONBOARDING_COMPLETED, defaultValue = false)

    suspend fun setOnboardingCompleted(completed: Boolean) {
        dataStoreManager.writeBoolean(KEY_ONBOARDING_COMPLETED, completed)
    }
}

