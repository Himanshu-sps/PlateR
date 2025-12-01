package com.plater.android.data.remote.interceptor

import com.google.gson.Gson
import com.plater.android.core.datastore.UserPreferencesManager
import com.plater.android.data.remote.dto.request.RefreshTokenRequest
import com.plater.android.data.remote.dto.response.RefreshTokenResponse
import com.plater.android.domain.models.AuthSession
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Production-grade OkHttp interceptor that handles authentication and silent token refresh.
 *
 * Features:
 * - Automatically attaches access token to Authorization header
 * - Detects 401 Unauthorized responses
 * - Performs silent token refresh with mutex-based synchronization
 * - Retries original request with new token
 * - Clears tokens and forces logout on refresh failure
 */
@Singleton
class AuthInterceptor @Inject constructor(
    private val userPreferencesManager: UserPreferencesManager,
    private val gson: Gson,
    @javax.inject.Named("base_url") private val baseUrl: String
) : Interceptor {

    companion object {
        private const val AUTHORIZATION_HEADER = "Authorization"
        private const val BEARER_PREFIX = "Bearer "
        private const val REFRESH_ENDPOINT = "auth/refresh"
    }

    // Global mutex to ensure only one refresh operation at a time
    private val refreshMutex = Mutex()

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        // Skip token attachment for refresh endpoint to avoid infinite loops
        if (originalRequest.url.encodedPath.contains(REFRESH_ENDPOINT)) {
            return chain.proceed(originalRequest)
        }

        // Attach access token from Encrypted DataStore
        val accessToken = runBlocking {
            userPreferencesManager.getAccessTokenSync()
        }

        val authenticatedRequest = if (!accessToken.isNullOrBlank()) {
            originalRequest.newBuilder()
                .header(AUTHORIZATION_HEADER, "$BEARER_PREFIX$accessToken")
                .build()
        } else {
            originalRequest
        }

        // Execute the request
        val response = chain.proceed(authenticatedRequest)

        // Check for 401 Unauthorized
        if (response.code == 401) {
            // Close the 401 response before retrying
            response.close()

            // Attempt silent token refresh
            return handleTokenRefresh(chain, originalRequest)
        }

        return response
    }

    /**
     * Handles token refresh with mutex-based synchronization.
     * Only one refresh request is allowed at a time.
     * Other requests wait for the mutex and then retry with the new token.
     */
    private fun handleTokenRefresh(
        chain: Interceptor.Chain,
        originalRequest: Request
    ): Response {
        return runBlocking {
            // Acquire mutex lock - only one refresh at a time
            refreshMutex.withLock {
                // Double-check: another thread might have refreshed while we waited
                val currentToken = userPreferencesManager.getAccessTokenSync()
                val retryRequest = if (!currentToken.isNullOrBlank()) {
                    originalRequest.newBuilder()
                        .header(AUTHORIZATION_HEADER, "$BEARER_PREFIX$currentToken")
                        .build()
                } else {
                    originalRequest
                }

                val retryResponse = chain.proceed(retryRequest)
                if (retryResponse.code != 401) {
                    return@withLock retryResponse
                }

                // Still 401, close the response and proceed with refresh
                retryResponse.close()
                val refreshToken = userPreferencesManager.getRefreshTokenSync()

                if (refreshToken.isNullOrBlank()) {
                    // No refresh token available, force logout
                    userPreferencesManager.clearAuthSession()
                    return@withLock createUnauthorizedResponse(originalRequest)
                }

                // Perform token refresh
                val refreshResult = performTokenRefresh(refreshToken)

                if (refreshResult == null) {
                    // Refresh failed, clear tokens and force logout
                    userPreferencesManager.clearAuthSession()
                    return@withLock createUnauthorizedResponse(originalRequest)
                }

                // Save new tokens
                val currentSession = userPreferencesManager.getAuthSessionSync()
                val updatedSession = currentSession?.copy(
                    accessToken = refreshResult.accessToken,
                    refreshToken = refreshResult.refreshToken
                ) ?: AuthSession(
                    user = com.plater.android.domain.models.User(
                        id = null,
                        username = null,
                        email = null,
                        firstName = null,
                        lastName = null,
                        image = null,
                        gender = null
                    ),
                    accessToken = refreshResult.accessToken,
                    refreshToken = refreshResult.refreshToken
                )

                userPreferencesManager.saveAuthSession(updatedSession)

                // Retry original request with new token
                val newAuthenticatedRequest = originalRequest.newBuilder()
                    .header(AUTHORIZATION_HEADER, "$BEARER_PREFIX${refreshResult.accessToken}")
                    .build()

                chain.proceed(newAuthenticatedRequest)
            }
        }
    }

    /**
     * Performs the actual token refresh API call.
     * Returns null on failure (network error or API error).
     */
    private suspend fun performTokenRefresh(refreshToken: String): RefreshTokenResponse? {
        return try {
            // Create a separate OkHttpClient for refresh to avoid interceptor loops
            val refreshClient = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()

            val refreshRetrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(refreshClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            val refreshService = refreshRetrofit.create(RefreshTokenService::class.java)
            val refreshRequest = RefreshTokenRequest(refreshToken = refreshToken)

            val response = refreshService.refreshToken(refreshRequest)
            response
        } catch (e: Exception) {
            // Network error or API error - return null to trigger logout
            null
        }
    }

    /**
     * Creates an unauthorized response when refresh fails.
     */
    private fun createUnauthorizedResponse(originalRequest: Request): Response {
        return Response.Builder()
            .request(originalRequest)
            .protocol(Protocol.HTTP_1_1)
            .code(401)
            .message("Unauthorized")
            .body("Authentication failed".toResponseBody("text/plain".toMediaType()))
            .build()
    }

    /**
     * Internal Retrofit service interface for token refresh.
     * This is separate from the main ApiService to avoid circular dependencies.
     */
    private interface RefreshTokenService {
        @POST(REFRESH_ENDPOINT)
        suspend fun refreshToken(
            @Body request: RefreshTokenRequest
        ): RefreshTokenResponse
    }
}

