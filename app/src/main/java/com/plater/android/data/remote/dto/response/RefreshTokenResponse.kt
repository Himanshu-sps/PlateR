package com.plater.android.data.remote.dto.response

import com.google.gson.annotations.SerializedName

data class RefreshTokenResponse(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String
)

