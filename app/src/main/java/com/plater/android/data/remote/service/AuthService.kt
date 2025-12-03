package com.plater.android.data.remote.service

import com.plater.android.data.remote.dto.request.LoginRequest
import com.plater.android.data.remote.dto.request.RefreshTokenRequest
import com.plater.android.data.remote.dto.response.RefreshTokenResponse
import com.plater.android.data.remote.dto.response.UserDto
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * In the AuthService we will not pass the token from the header of the Apis
 */
interface AuthService {

    companion object {
        const val LOGIN_ENDPOINT = "auth/login"
        const val REFRESH_ENDPOINT = "auth/refresh"
    }

    /**
     * Exchanges credentials for an authenticated session payload.
     */
    @POST(LOGIN_ENDPOINT)
    suspend fun login(@Body loginRequest: LoginRequest): UserDto

    @POST(REFRESH_ENDPOINT)
    suspend fun refreshToken(@Body refreshTokenRequest: RefreshTokenRequest): RefreshTokenResponse

}