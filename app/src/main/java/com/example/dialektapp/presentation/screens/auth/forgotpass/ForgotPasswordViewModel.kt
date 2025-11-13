package com.example.dialektapp.presentation.screens.auth.forgotpass

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dialektapp.domain.usecases.ValidateEmailUseCase
import com.example.dialektapp.domain.util.NetworkError
import com.example.dialektapp.domain.util.Result
import com.example.dialektapp.domain.util.ValidationResult
import com.example.dialektapp.domain.util.onError
import com.example.dialektapp.domain.util.onSuccess
import com.example.dialektapp.presentation.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val validateEmail: ValidateEmailUseCase,
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val _forgotPasswordState = MutableStateFlow(ForgotPasswordState())
    val forgotPasswordState: StateFlow<ForgotPasswordState> = _forgotPasswordState.asStateFlow()

    fun updateEmail(email: String) {
        _forgotPasswordState.value = _forgotPasswordState.value.copy(email = email)
        checkInputValidation()
    }

    private fun checkInputValidation() {
        val validationResult = validateEmail(_forgotPasswordState.value.email)
        processInputValidationType(validationResult)
    }

    private fun processInputValidationType(validationResult: ValidationResult) {
        _forgotPasswordState.value = when (validationResult) {
            is ValidationResult.Success -> _forgotPasswordState.value.copy(
                isInputValid = true,
                errorMessageInput = emptyList()
            )

            is ValidationResult.Error -> _forgotPasswordState.value.copy(
                isInputValid = false,
                errorMessageInput = validationResult.errors
            )
        }
    }

    fun resetPassword() {
        val email = _forgotPasswordState.value.email

        val validationResult = validateEmail(email)
        if (validationResult is ValidationResult.Error) {
            processInputValidationType(validationResult)
            return
        }

        viewModelScope.launch {
            _forgotPasswordState.value = _forgotPasswordState.value.copy(isLoading = true)

            try {
                delay(2000) // Симуляція мережевого запиту
                _forgotPasswordState.value = _forgotPasswordState.value.copy(isLoading = false)
                _uiEvent.emit(UiEvent.ShowSnackbar("Посилання для скидання пароля надіслано на ваш email"))
                _uiEvent.emit(UiEvent.Navigate)
            } catch (e: Exception) {
                _forgotPasswordState.value = _forgotPasswordState.value.copy(
                    isLoading = false,
                    errorMessageResetProcess = NetworkError.UNKNOWN
                )
                _uiEvent.emit(UiEvent.ShowErrorSnackbar(NetworkError.UNKNOWN))
            }
        }
    }
}