package com.example.dialektapp.domain.repository

import com.example.dialektapp.domain.model.User
import com.example.dialektapp.domain.util.NetworkError
import com.example.dialektapp.domain.util.Result

interface AuthRepository {
    suspend fun register(
        username: String,
        email: String,
        password: String,
        fullName: String,
    ): Result<Unit, NetworkError>

    suspend fun login(
        username: String,
        password: String,
    ): Result<Unit, NetworkError>

    suspend fun getCurrentUser(): Result<User, NetworkError>

    suspend fun logout()

}