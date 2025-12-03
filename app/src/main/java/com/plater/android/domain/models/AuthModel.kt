package com.plater.android.domain.models

/**
 * Domain model representing the authenticated context returned by the backend after login.
 * Contains user information and authentication tokens for session management.
 *
 * @property user The authenticated user's profile information
 * @property accessToken JWT access token for API authentication (short-lived)
 * @property refreshToken JWT refresh token for obtaining new access tokens (long-lived)
 */
data class AuthModel(
    val user: User,
    val accessToken: String?,
    val refreshToken: String?
)

