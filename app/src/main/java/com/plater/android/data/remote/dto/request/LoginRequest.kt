package com.plater.android.data.remote.dto.request

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("expiresInMins")
    val expiresInMins: Int = 2
)
