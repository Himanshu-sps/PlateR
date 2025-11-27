package com.plater.android.domain.models

/**
 * Represents the authenticated context returned by the backend after login.
 */
data class AuthSession(
    val user: User,
    val accessToken: String?,
    val refreshToken: String?
)

