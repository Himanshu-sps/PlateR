package com.plater.android.data.remote.service

import com.plater.android.data.remote.dto.request.LoginRequest
import com.plater.android.data.remote.dto.request.RefreshTokenRequest
import com.plater.android.data.remote.dto.response.RefreshTokenResponse
import com.plater.android.data.remote.dto.response.UserDto
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Retrofit definitions for authentication endpoints.
 */
interface ApiService {

    @POST("auth/login")
    /**
     * Exchanges credentials for an authenticated session payload.
     */
    suspend fun login(@Body loginRequest: LoginRequest): UserDto

    @POST("auth/refresh")
    /**
     * Refreshes the access token using a refresh token.
     */
    suspend fun refreshToken(@Body refreshTokenRequest: RefreshTokenRequest): RefreshTokenResponse

}