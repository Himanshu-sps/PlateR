package com.plater.android.domain.models

import kotlinx.serialization.Serializable

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
