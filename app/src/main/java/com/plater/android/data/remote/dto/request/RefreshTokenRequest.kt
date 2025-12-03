package com.plater.android.data.remote.dto.request

import com.google.gson.annotations.SerializedName

data class RefreshTokenRequest(
    @SerializedName("refreshToken")
    val refreshToken: String,
    @SerializedName("expiresInMins")
    val expiresInMins: Int = 30 // Optional, defaults to 30 minutes
)

