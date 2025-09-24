package com.example.dialektapp.domain.model

data class User(
    val username: String,
    val email: String,
    val fullName: String,
    val disabled: Boolean = false,
    val role: String? = null
)