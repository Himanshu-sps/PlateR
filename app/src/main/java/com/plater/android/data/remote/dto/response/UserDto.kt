package com.plater.android.data.remote.dto.response

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("accessToken")
    val accessToken: String? = null,
    @SerializedName("refreshToken")
    val refreshToken: String? = null,

    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("firstName")
    val firstName: String? = null,
    @SerializedName("gender")
    val gender: String? = null,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("lastName")
    val lastName: String? = null,
    @SerializedName("username")
    val username: String? = null
)