package com.plater.android.core.datastore

import com.google.gson.Gson
import com.plater.android.domain.models.AuthModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
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

    fun authSession(): Flow<AuthModel?> =
        encryptedDataStoreManager.readSecureString(KEY_AUTH_SESSION)
            .map { stored ->
                stored?.let {
                    runCatching { gson.fromJson(it, AuthModel::class.java) }.getOrNull()
                }
            }

    suspend fun saveAuthSession(session: AuthModel) {
        val sessionJson = gson.toJson(session)
        encryptedDataStoreManager.writeSecureString(KEY_AUTH_SESSION, sessionJson)
    }

    suspend fun clearAuthSession() {
        encryptedDataStoreManager.clearSecureKey(KEY_AUTH_SESSION)
    }

    /**
     * Synchronously retrieves the access token from the stored auth session.
     * This method is intended for use in interceptors where blocking is necessary.
     *
     * @return The access token if available, null otherwise.
     */
    fun getAccessTokenSync(): String? = runBlocking {
        authSession().first()?.accessToken
    }

    /**
     * Synchronously retrieves the refresh token from the stored auth session.
     * This method is intended for use in interceptors where blocking is necessary.
     *
     * @return The refresh token if available, null otherwise.
     */
    fun getRefreshTokenSync(): String? = runBlocking {
        authSession().first()?.refreshToken
    }

    /**
     * Synchronously retrieves the full auth session.
     * This method is intended for use in interceptors where blocking is necessary.
     *
     * @return The auth session if available, null otherwise.
     */
    fun getAuthSessionSync(): AuthModel? = runBlocking {
        authSession().first()
    }

    /**
     * Updates only the tokens in the existing auth session while preserving user data.
     * This method is intended for use in interceptors when refreshing tokens.
     *
     * @param newAccessToken The new access token
     * @param newRefreshToken The new refresh token
     */
    suspend fun updateTokens(newAccessToken: String, newRefreshToken: String) {
        val currentSession = authSession().first()
        if (currentSession != null) {
            val updatedSession = currentSession.copy(
                accessToken = newAccessToken,
                refreshToken = newRefreshToken
            )
            saveAuthSession(updatedSession)
        }
    }
}

