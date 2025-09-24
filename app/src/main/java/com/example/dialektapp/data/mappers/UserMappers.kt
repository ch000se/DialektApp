package com.example.dialektapp.data.mappers

import com.example.dialektapp.data.remote.dto.UserDto
import com.example.dialektapp.domain.model.User

fun UserDto.toDomain(): User {
    return User(
        username = username,
        email = email,
        fullName = fullName,
        disabled = disabled,
        role = role
    )
}