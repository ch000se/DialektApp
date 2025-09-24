package com.example.dialektapp.presentation.screens.auth.login

import com.example.dialektapp.domain.util.NetworkError
import com.example.dialektapp.domain.util.ValidationError

data class LoginState (
    val username: String = "",
    val password: String = "",
    val rememberMe: Boolean = false,
    val isInputValid: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val errorMessageInput: List<ValidationError> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessageLoginProcess: NetworkError = NetworkError.NONE
)