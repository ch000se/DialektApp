package com.example.dialektapp.data.mappers

import com.example.dialektapp.data.remote.dto.UserDto
import com.example.dialektapp.domain.model.User

fun UserDto.toDomain(): User {
    return User(
        id = username,
        username = username,
        email = email,
        fullName = fullName,
        profileImageUrl = profileImageUrl,
        disabled = disabled,
        role = role
    )
}