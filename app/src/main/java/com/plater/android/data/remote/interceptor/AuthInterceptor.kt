package com.plater.android.data.remote.interceptor

import com.plater.android.core.datastore.UserPreferencesManager
import com.plater.android.data.remote.dto.request.RefreshTokenRequest
import com.plater.android.data.remote.service.AuthService
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val userPreferencesManager: UserPreferencesManager,
    private val authService: AuthService
) : Interceptor {

    companion object {
        private const val AUTHORIZATION_HEADER = "Authorization"
        private const val BEARER_PREFIX = "Bearer "
        private const val HTTP_UNAUTHORIZED = 401
        private const val TOKEN_EXPIRY_MINS = 30
    }

    // Mutex to prevent multiple simultaneous refresh calls
    private val refreshMutex = Mutex()

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val accessToken = runBlocking {
            userPreferencesManager.getAccessTokenSync()
        }

        // Execute request with current token
        val request = buildRequestWithHeaderToken(originalRequest, accessToken)
        val response = chain.proceed(request)

        // Handle 401 Unauthorized by refreshing token
        if (response.code == HTTP_UNAUTHORIZED && accessToken != null) {
            response.close()
            return handleTokenRefresh(chain, originalRequest, accessToken)
        }

        return response
    }

    /**
     * Builds a request with the provided access token in the Authorization header.
     */
    private fun buildRequestWithHeaderToken(request: Request, token: String?): Request {
        return if (token != null) {
            request.newBuilder()
                .header(AUTHORIZATION_HEADER, "$BEARER_PREFIX$token")
                .build()
        } else {
            request
        }
    }

    /**
     * Handles token refresh when a 401 response is received.
     * Uses Mutex to prevent multiple simultaneous refresh calls.
     */
    private fun handleTokenRefresh(
        chain: Interceptor.Chain,
        originalRequest: Request,
        originalToken: String
    ): Response {
        return runBlocking {
            refreshMutex.withLock {
                // Check if token was already refreshed by another thread
                val currentToken = userPreferencesManager.getAccessTokenSync()

                // If token changed, retry with new token
                if (currentToken != originalToken && currentToken != null) {
                    return@withLock chain.proceed(
                        buildRequestWithHeaderToken(
                            originalRequest,
                            currentToken
                        )
                    )
                }

                // Try to refresh the token
                val refreshToken = userPreferencesManager.getRefreshTokenSync()

                if (refreshToken == null) {
                    clearSession()
                    return@withLock createUnauthorizedResponse(originalRequest)
                }

                return@withLock try {
                    val refreshResponse = authService.refreshToken(
                        RefreshTokenRequest(
                            refreshToken = refreshToken,
                            expiresInMins = TOKEN_EXPIRY_MINS
                        )
                    )

                    // Update stored tokens
                    userPreferencesManager.updateTokens(
                        newAccessToken = refreshResponse.accessToken,
                        newRefreshToken = refreshResponse.refreshToken
                    )

                    // Retry original request with new token
                    chain.proceed(
                        buildRequestWithHeaderToken(
                            originalRequest,
                            refreshResponse.accessToken
                        )
                    )
                } catch (e: Exception) {
                    clearSession()
                    createUnauthorizedResponse(originalRequest)
                }
            }
        }
    }

    /**
     * Clears the auth session when token refresh fails.
     */
    private fun clearSession() {
        runBlocking {
            userPreferencesManager.clearAuthSession()
        }
    }

    /**
     * Creates an unauthorized response for failed token refresh.
     */
    private fun createUnauthorizedResponse(request: Request): Response {
        return Response.Builder()
            .request(request)
            .protocol(okhttp3.Protocol.HTTP_1_1)
            .code(HTTP_UNAUTHORIZED)
            .message("Unauthorized")
            .body(okhttp3.ResponseBody.create(null, ""))
            .build()
    }
}