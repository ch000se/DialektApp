package com.example.dialektapp.presentation.screens.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dialektapp.domain.usecases.RegisterUseCase
import com.example.dialektapp.domain.usecases.ValidateSignUpUseCase
import com.example.dialektapp.domain.util.ValidationResult
import com.example.dialektapp.domain.util.onError
import com.example.dialektapp.domain.util.onSuccess
import com.example.dialektapp.presentation.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val registerUser: RegisterUseCase,
    private val validateSignUp: ValidateSignUpUseCase,
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val _signUpState = MutableStateFlow(SignUpState())
    val signUpState: StateFlow<SignUpState> = _signUpState.asStateFlow()

    fun updateUsername(username: String) {
        _signUpState.value = _signUpState.value.copy(username = username)
        checkInputValidation()
    }

    fun updateFullName(fullname: String) {
        _signUpState.value = _signUpState.value.copy(fullname = fullname)
        checkInputValidation()
    }

    fun updateAgreeToTerms(agreeToTerms: Boolean) {
        _signUpState.value = _signUpState.value.copy(agreeToTerms = agreeToTerms)
        checkInputValidation()
    }

    fun updateEmail(email: String) {
        _signUpState.value = _signUpState.value.copy(email = email)
        checkInputValidation()
    }

    fun updatePassword(password: String) {
        _signUpState.value = _signUpState.value.copy(password = password)
        checkInputValidation()
    }

    fun updateConfirmPassword(confirmPassword: String) {
        _signUpState.value = _signUpState.value.copy(confirmPassword = confirmPassword)
        checkInputValidation()
    }

    fun togglePasswordVisibility() {
        _signUpState.value =
            _signUpState.value.copy(isPasswordVisible = !_signUpState.value.isPasswordVisible)
    }

    fun toggleConfirmPasswordVisibility() {
        _signUpState.value =
            _signUpState.value.copy(isConfirmPasswordVisible = !_signUpState.value.isConfirmPasswordVisible)
    }

    private fun checkInputValidation() {
        val validationResult = validateSignUp(
            username = _signUpState.value.username,
            fullName = _signUpState.value.fullname,
            email = _signUpState.value.email,
            password = _signUpState.value.password,
            confirmPassword = _signUpState.value.confirmPassword,
            agreeToTerms = _signUpState.value.agreeToTerms
        )
        processInputValidationType(validationResult)
    }

    private fun processInputValidationType(validationResult: ValidationResult) {
        _signUpState.value = when (validationResult) {
            is ValidationResult.Success -> _signUpState.value.copy(
                isInputValid = true,
                errorMessageInput = emptyList()
            )

            is ValidationResult.Error -> _signUpState.value.copy(
                isInputValid = false,
                errorMessageInput = validationResult.errors
            )
        }
    }

    fun register() {
        val validationResult = validateSignUp(
            username = _signUpState.value.username,
            fullName = _signUpState.value.fullname,
            email = _signUpState.value.email,
            password = _signUpState.value.password,
            confirmPassword = _signUpState.value.confirmPassword,
            agreeToTerms = _signUpState.value.agreeToTerms
        )

        if (validationResult is ValidationResult.Error) {
            processInputValidationType(validationResult)
            return
        }

        viewModelScope.launch {
            _signUpState.value = _signUpState.value.copy(isLoading = true)

            val result = registerUser(
                username = _signUpState.value.username,
                fullName = _signUpState.value.fullname,
                email = _signUpState.value.email,
                password = _signUpState.value.password
            )

            _signUpState.value = _signUpState.value.copy(isLoading = false)

            result.onSuccess {
                _signUpState.value = _signUpState.value.copy(isSuccess = true)
                _uiEvent.emit(UiEvent.Navigate)
            }.onError { error ->
                _signUpState.value = _signUpState.value.copy(
                    errorMessageSignUpProcess = error
                )
                _uiEvent.emit(UiEvent.ShowSnackbar(error))
            }
        }
    }
}