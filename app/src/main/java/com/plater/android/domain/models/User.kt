package com.plater.android.domain.models

import kotlinx.serialization.Serializable

/**
 * Domain model representing a user in the application.
 * Contains user profile information and authentication-related data.
 *
 * @property id Unique identifier for the user
 * @property email User's email address
 * @property firstName User's first name
 * @property lastName User's last name
 * @property username User's unique username
 * @property image URL or path to the user's profile image
 * @property gender User's gender (optional)
 */
@Serializable
data class User(
    val id: Int?,
    val email: String?,
    val firstName: String?,
    val lastName: String?,
    val username: String?,
    val image: String?,
    val gender: String?,
)
