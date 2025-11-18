package com.example.dialektapp.domain.usecases.auth

import com.example.dialektapp.domain.model.User
import com.example.dialektapp.domain.repository.AuthRepository
import com.example.dialektapp.domain.util.NetworkError
import com.example.dialektapp.domain.util.Result
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        username: String,
        email: String,
        password: String,
        fullName: String,
    ): Result<Unit, NetworkError> {
        return repository.register(username, email, password, fullName)
    }
}
