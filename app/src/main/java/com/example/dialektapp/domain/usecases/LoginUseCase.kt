package com.example.dialektapp.domain.usecases

import com.example.dialektapp.domain.model.User
import com.example.dialektapp.domain.repository.AuthRepository
import com.example.dialektapp.domain.util.NetworkError
import com.example.dialektapp.domain.util.Result
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke(
        username: String,
        password: String,
    ): Result<Unit, NetworkError> {
        return repository.login(username, password)
    }
}