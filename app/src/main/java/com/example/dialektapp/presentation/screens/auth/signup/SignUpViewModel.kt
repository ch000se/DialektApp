package com.example.dialektapp.presentation.screens.auth.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dialektapp.domain.usecases.auth.RegisterUseCase
import com.example.dialektapp.domain.usecases.validation.ValidateSignUpUseCase
import com.example.dialektapp.domain.util.ValidationResult
import com.example.dialektapp.domain.util.onError
import com.example.dialektapp.domain.util.onSuccess
import com.example.dialektapp.presentation.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val registerUser: RegisterUseCase,
    private val validateSignUp: ValidateSignUpUseCase,
) : ViewModel() {

    // Використовуємо Channel замість SharedFlow для one-time events
    private val _uiEvent = Channel<UiEvent>(Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _signUpState = MutableStateFlow(SignUpState())
    val signUpState: StateFlow<SignUpState> = _signUpState.asStateFlow()

    private val TAG = "SignUpViewModel"
    private var registerJob: Job? = null

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
            Log.d(TAG, "Validation failed: ${validationResult.errors}")
            return
        }

        registerJob?.cancel() // Скасовуємо попередню реєстрацію якщо є
        registerJob = viewModelScope.launch {
            Log.d(TAG, "Starting registration for: ${_signUpState.value.username}")
            _signUpState.value = _signUpState.value.copy(isLoading = true)

            val result = registerUser(
                username = _signUpState.value.username,
                fullName = _signUpState.value.fullname,
                email = _signUpState.value.email,
                password = _signUpState.value.password
            )

            _signUpState.value = _signUpState.value.copy(isLoading = false)

            result.onSuccess {
                Log.d(TAG, "Registration successful")
                _signUpState.value = _signUpState.value.copy(isSuccess = true)
                _uiEvent.send(UiEvent.Navigate)
            }.onError { error ->
                Log.e(TAG, "Registration error: $error")
                _signUpState.value = _signUpState.value.copy(
                    errorMessageSignUpProcess = error
                )
                _uiEvent.send(UiEvent.ShowErrorSnackbar(error))
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        registerJob?.cancel()
        _uiEvent.close()
        Log.d(TAG, "SignUpViewModel cleared, jobs cancelled")
    }
}