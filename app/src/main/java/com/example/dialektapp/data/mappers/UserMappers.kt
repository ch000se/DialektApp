package com.example.dialektapp.data.mappers

import com.example.dialektapp.data.remote.dto.UserDto
import com.example.dialektapp.data.remote.dto.UserRole
import com.example.dialektapp.domain.model.User

fun UserDto.toDomain(): User {
    return User(
        id = id.toString(),
        username = username,
        email = email,
        fullName = fullName,
        profileImageUrl = profileImageUrl,
        disabled = disabled,
        role = role.toRoleString()
    )
}

private fun UserRole.toRoleString(): String {
    return when (this) {
        UserRole.USER -> "user"
        UserRole.ADMIN -> "admin"
        UserRole.MANAGER -> "manager"
    }
}