package com.plater.android.data.remote.service

import com.plater.android.data.remote.dto.response.UserDto
import retrofit2.http.GET

/**
 * Retrofit definitions for authentication endpoints. It will use all apis with Bearer <TOKEN> headers
 */
interface ApiService {

    companion object {
        const val GET_CURRENT_USER_ENDPOINT = "auth/me"
    }

    /**
     * Gets the current authenticated user's information.
     */
    @GET(GET_CURRENT_USER_ENDPOINT)
    suspend fun getCurrentUser(): UserDto

}