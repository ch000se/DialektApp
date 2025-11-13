package com.example.dialektapp.data.repository

import com.example.dialektapp.data.mappers.toDomain
import com.example.dialektapp.data.remote.AuthApi
import com.example.dialektapp.data.remote.TokenManager
import com.example.dialektapp.data.remote.dto.RegisterRequest
import com.example.dialektapp.data.remote.safeCall
import com.example.dialektapp.domain.model.User
import com.example.dialektapp.domain.repository.AuthRepository
import com.example.dialektapp.domain.util.NetworkError
import com.example.dialektapp.domain.util.Result
import com.example.dialektapp.domain.util.map
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val tokenManager: TokenManager,
) : AuthRepository {

    override suspend fun register(
        username: String,
        email: String,
        password: String,
        fullName: String,
    ): Result<Unit, NetworkError> {
        return safeCall {
            api.register(
                RegisterRequest(
                    username = username,
                    email = email,
                    password = password,
                    fullName = fullName
                )
            )
        }
    }

    override suspend fun login(
        username: String,
        password: String,
    ): Result<Unit, NetworkError> {
        return safeCall {
            val response = api.login(username, password)

            val body = response.body()
            if (response.isSuccessful && body != null) {
                tokenManager.saveToken(body.accessToken)
            }
            response

        }
        .map { }
    }


    override suspend fun getCurrentUser(): Result<User, NetworkError> {
        return safeCall {
            api.getCurrentUser()
        }.map { it.toDomain() }
    }

    override suspend fun logout() {
        tokenManager.clearToken()
    }
}
