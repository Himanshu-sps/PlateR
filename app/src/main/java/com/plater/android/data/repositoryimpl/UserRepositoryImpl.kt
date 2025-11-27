package com.plater.android.data.repositoryimpl

import android.content.Context
import com.plater.android.R
import com.plater.android.data.mappers.toAuthSession
import com.plater.android.data.remote.ApiResponse
import com.plater.android.data.remote.dto.request.LoginRequest
import com.plater.android.data.remote.service.ApiService
import com.plater.android.domain.models.AuthSession
import com.plater.android.domain.repository.UserRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    @ApplicationContext private val context: Context
) : UserRepository {

    override suspend fun login(
        username: String,
        password: String
    ): ApiResponse<AuthSession> {
        return try {
            val loginRequest = LoginRequest(username = username, password = password)
            val userDto = apiService.login(loginRequest)
            ApiResponse(
                status = true,
                message = context.getString(R.string.login_success_message),
                data = userDto.toAuthSession()
            )
        } catch (e: Exception) {
            val errorMsg = if (e.message?.contains("HTTP 400", true) == true) {
                "Invalid Credentials"
            } else {
                e.message ?: context.getString(R.string.unknown_error_occurred)
            }
            ApiResponse(
                status = false,
                message = errorMsg,
                data = null
            )
        }
    }
}