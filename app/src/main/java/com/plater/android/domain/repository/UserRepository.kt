package com.plater.android.domain.repository

import com.plater.android.data.remote.ApiResponse
import com.plater.android.domain.models.AuthSession

/**
 * Abstraction over remote/local data sources that deal with authentication APIs.
 */
interface UserRepository {

    /**
     * Executes a login attempt and returns the raw API response.
     */
    suspend fun login(
        username: String,
        password: String
    ): ApiResponse<AuthSession>

}