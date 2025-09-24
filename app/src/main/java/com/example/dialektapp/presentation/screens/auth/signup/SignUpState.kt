package com.example.dialektapp.presentation.screens.auth.signup

import com.example.dialektapp.domain.util.NetworkError
import com.example.dialektapp.domain.util.ValidationError

data class SignUpState(
    val email: String = "",
    val password: String = "",
    val username: String = "",
    val fullname: String = "",
    val confirmPassword: String = "",
    val agreeToTerms: Boolean = false,
    val isInputValid: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val isConfirmPasswordVisible: Boolean = false,
    val errorMessageInput: List<ValidationError> = emptyList(),
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessageSignUpProcess: NetworkError = NetworkError.NONE
)
