package com.plater.android.data.remote

/**
 * Simple wrapper for normalising API responses across remote and cached sources.
 *
 * @param T type of the payload returned by the API.
 * @property status bool ie true for success and false for failure.
 * @property message optional message for user-friendly display.
 * @property data optional payload data.
 */
data class ApiResponse<T>(
    val status: Boolean,
    val message: String? = null,
    val data: T? = null
)