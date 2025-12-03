package com.plater.android.domain.usecase

import android.content.Context
import com.plater.android.R
import com.plater.android.data.remote.ApiResponse
import com.plater.android.domain.models.ResultResource
import com.plater.android.domain.models.User
import com.plater.android.domain.repository.UserRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Coordinates the get current user call, wrapping responses into [ResultResource] for the UI.
 */
class GetCurrentUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
    @ApplicationContext private val context: Context
) {
    /**
     * Fetches the current authenticated user's information and emits loading, success,
     * or error states that the presentation layer can observe.
     */
    operator fun invoke(): Flow<ResultResource<User>> =
        flow {
            emit(ResultResource.Loading)

            try {
                val apiResponse: ApiResponse<User> = userRepository.getCurrentUser()

                if (apiResponse.status && apiResponse.data != null) {
                    emit(
                        ResultResource.Success(
                            message = apiResponse.message.toString(), // I guarantee in repository for message
                            data = apiResponse.data
                        )
                    )
                } else {
                    emit(
                        ResultResource.Error(
                            message = apiResponse.message.toString(),
                            cause = null
                        )
                    )
                }
            } catch (e: Exception) {
                emit(
                    ResultResource.Error(
                        message = e.message ?: context.getString(R.string.unknown_error_occurred),
                        cause = e
                    )
                )
            }
        }
}

