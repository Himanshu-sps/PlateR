package com.plater.android.domain.models

/**
 * Wrapper type used by the domain layer to represent loading, success, or error states.
 */
sealed class ResultResource<out T> {
    /**
     * Represents the loading state while data is being fetched.
     */
    data object Loading : ResultResource<Nothing>()

    /**
     * Represents a successful result containing data.
     */
    data class Success<out T>(val message: String, val data: T) : ResultResource<T>()

    /**
     * Represents a failure result with an error message and an optional cause.
     */
    data class Error(val message: String, val cause: Throwable? = null) : ResultResource<Nothing>()
}