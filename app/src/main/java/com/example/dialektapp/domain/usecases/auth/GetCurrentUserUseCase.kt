package com.example.dialektapp.domain.usecases.auth

import com.example.dialektapp.domain.model.User
import com.example.dialektapp.domain.repository.AuthRepository
import com.example.dialektapp.domain.util.NetworkError
import com.example.dialektapp.domain.util.Result
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke(): Result<User, NetworkError> {
        return repository.getCurrentUser()
    }
}
