package com.plater.android.data.repositoryimpl

import android.content.Context
import com.plater.android.R
import com.plater.android.data.mappers.toAuthSession
import com.plater.android.data.mappers.toDomain
import com.plater.android.data.remote.ApiResponse
import com.plater.android.data.remote.dto.request.LoginRequest
import com.plater.android.data.remote.service.ApiService
import com.plater.android.data.remote.service.AuthService
import com.plater.android.domain.models.AuthModel
import com.plater.android.domain.models.User
import com.plater.android.domain.repository.UserRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val apiService: ApiService,
    @ApplicationContext private val context: Context
) : UserRepository {

    override suspend fun login(
        username: String,
        password: String
    ): ApiResponse<AuthModel> {
        return try {
            val loginRequest = LoginRequest(username = username, password = password)
            val userDto = authService.login(loginRequest)
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

    override suspend fun getCurrentUser(): ApiResponse<User> {
        return try {
            val userDto = apiService.getCurrentUser()
            ApiResponse(
                status = true,
                message = "User fetched successfully",
                data = userDto.toDomain()
            )
        } catch (e: Exception) {
            val errorMsg = when {
                e.message?.contains("HTTP 401", true) == true -> {
                    "Unauthorized. Please login again."
                }

                e.message?.contains("HTTP 403", true) == true -> {
                    "Access forbidden"
                }

                else -> {
                    e.message ?: context.getString(R.string.unknown_error_occurred)
                }
            }
            ApiResponse(
                status = false,
                message = errorMsg,
                data = null
            )
        }
    }
}