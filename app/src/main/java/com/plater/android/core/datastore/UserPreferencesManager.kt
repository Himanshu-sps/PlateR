package com.plater.android.core.datastore

import com.google.gson.Gson
import com.plater.android.domain.models.AuthSession
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Thin wrapper that exposes strongly-typed user preference queries while delegating
 * the actual persistence work to [DataStoreManager].
 */
@Singleton
class UserPreferencesManager @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val encryptedDataStoreManager: EncryptedDataStoreManager,
    private val gson: Gson
) {

    companion object {
        private const val KEY_ONBOARDING_COMPLETED = "onboarding_completed"
        private const val KEY_AUTH_SESSION = "auth_session"
    }

    val hasCompletedOnboarding: Flow<Boolean> =
        dataStoreManager.readBoolean(KEY_ONBOARDING_COMPLETED, defaultValue = false)

    suspend fun setOnboardingCompleted(completed: Boolean) {
        dataStoreManager.writeBoolean(KEY_ONBOARDING_COMPLETED, completed)
    }

    fun authSession(): Flow<AuthSession?> =
        encryptedDataStoreManager.readSecureString(KEY_AUTH_SESSION)
            .map { stored ->
                stored?.let {
                    runCatching { gson.fromJson(it, AuthSession::class.java) }.getOrNull()
                }
            }

    suspend fun saveAuthSession(session: AuthSession) {
        val sessionJson = gson.toJson(session)
        encryptedDataStoreManager.writeSecureString(KEY_AUTH_SESSION, sessionJson)
    }

    suspend fun clearAuthSession() {
        encryptedDataStoreManager.clearSecureKey(KEY_AUTH_SESSION)
    }
}

