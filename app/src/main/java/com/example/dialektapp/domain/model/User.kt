package com.example.dialektapp.domain.model

data class User(
    val id: String = "",
    val username: String,
    val email: String,
    val fullName: String,
    val profileImageUrl: String? = null,
    val disabled: Boolean = false,
    val role: String? = null,
)