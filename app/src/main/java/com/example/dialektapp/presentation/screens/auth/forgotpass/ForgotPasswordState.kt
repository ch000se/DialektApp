package com.example.dialektapp.presentation.screens.auth.forgotpass

import com.example.dialektapp.domain.util.NetworkError
import com.example.dialektapp.domain.util.ValidationError

data class ForgotPasswordState(
    val email: String = "",
    val isInputValid: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessageInput: List<ValidationError> = emptyList(),
    val errorMessageResetProcess: NetworkError = NetworkError.NONE,
)