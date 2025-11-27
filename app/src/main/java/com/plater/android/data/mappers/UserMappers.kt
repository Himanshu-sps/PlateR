package com.plater.android.data.mappers

import com.plater.android.data.remote.dto.response.UserDto
import com.plater.android.domain.models.AuthSession
import com.plater.android.domain.models.User

fun UserDto.toDomain(): User {
    return User(
        id = this.id,
        email = this.email,
        firstName = this.firstName,
        lastName = this.lastName,
        username = this.username,
        image = this.image,
        gender = this.gender
        // Don't map tokens to domain model
    )
}

fun User.toDto(): UserDto {
    return UserDto(
        id = this.id
    )
}

fun UserDto.toAuthSession(): AuthSession {
    return AuthSession(
        user = this.toDomain(),
        accessToken = this.accessToken,
        refreshToken = this.refreshToken
    )
}

