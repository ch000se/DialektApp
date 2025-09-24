package com.example.dialektapp.presentation.screens.auth.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dialektapp.domain.model.User
import com.example.dialektapp.domain.usecases.LoginUseCase
import com.example.dialektapp.domain.usecases.ValidateLoginUseCase
import com.example.dialektapp.domain.util.NetworkError
import com.example.dialektapp.domain.util.Result
import com.example.dialektapp.domain.util.ValidationResult
import com.example.dialektapp.domain.util.onError
import com.example.dialektapp.domain.util.onSuccess
import com.example.dialektapp.presentation.util.UiEvent
import com.example.dialektapp.presentation.util.toUserMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUser: LoginUseCase,
    private val validateLogin: ValidateLoginUseCase,
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val _loginState = MutableStateFlow(LoginState())
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    fun updateUsername(username: String) {
        _loginState.value = _loginState.value.copy(username = username)
        checkInputValidation()
    }

    fun updatePassword(password: String) {
        _loginState.value = _loginState.value.copy(password = password)
        checkInputValidation()
    }

    fun togglePasswordVisibility() {
        _loginState.value =
            _loginState.value.copy(isPasswordVisible = !_loginState.value.isPasswordVisible)
    }

    fun toggleRememberMe() {
        _loginState.value =
            _loginState.value.copy(rememberMe = !_loginState.value.rememberMe)
    }

    private fun checkInputValidation() {
        val validationResult = validateLogin(
            _loginState.value.username,
            _loginState.value.password
        )
        processInputValidationType(validationResult)
    }

    private fun processInputValidationType(validationResult: ValidationResult) {
        _loginState.value = when (validationResult) {
            is ValidationResult.Success -> _loginState.value.copy(
                isInputValid = true,
                errorMessageInput = emptyList()
            )

            is ValidationResult.Error -> _loginState.value.copy(
                isInputValid = false,
                errorMessageInput = validationResult.errors
            )
        }
    }


    fun login() {
        val username = _loginState.value.username
        val password = _loginState.value.password
        Log.d("LoginViewModel", "Login attempt: username=$username")

        val validationResult = validateLogin(username, password)
        if (validationResult is ValidationResult.Error) {
            processInputValidationType(validationResult)
            Log.d("LoginViewModel", "Validation failed: ${validationResult.errors}")
            return
        }

        viewModelScope.launch {
            _loginState.value =
                _loginState.value.copy(isLoading = true)
            Log.d("LoginViewModel", "Calling LoginUseCase...")
            val result: Result<Unit, NetworkError> =
                loginUser.invoke(username, password)

            result
                .onSuccess { user ->
                    _loginState.value = _loginState.value.copy(isLoading = false)
                    Log.d("LoginViewModel", "Login successful: $user")
                    _uiEvent.emit(UiEvent.Navigate)
                }
                .onError { error ->
                    _loginState.value = _loginState.value.copy(
                        isLoading = false,
                        errorMessageLoginProcess = error
                    )
                    Log.d("LoginViewModel", "Login error: $error")

                    _uiEvent.emit(
                        UiEvent.ShowSnackbar(error)
                    )
                }
        }
    }
}