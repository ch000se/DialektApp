package com.example.dialektapp.presentation.screens.auth.forgotpass

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dialektapp.domain.usecases.validation.ValidateEmailUseCase
import com.example.dialektapp.domain.util.NetworkError
import com.example.dialektapp.domain.util.Result
import com.example.dialektapp.domain.util.ValidationResult
import com.example.dialektapp.presentation.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val validateEmail: ValidateEmailUseCase,
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>(Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _forgotPasswordState = MutableStateFlow(ForgotPasswordState())
    val forgotPasswordState: StateFlow<ForgotPasswordState> = _forgotPasswordState.asStateFlow()

    private val TAG = "ForgotPasswordVM"
    private var resetJob: Job? = null

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
        Log.d(TAG, "Reset password attempt for: $email")

        val validationResult = validateEmail(email)
        if (validationResult is ValidationResult.Error) {
            processInputValidationType(validationResult)
            Log.d(TAG, "Validation failed: ${validationResult.errors}")
            return
        }

        resetJob?.cancel()
        resetJob = viewModelScope.launch {
            _forgotPasswordState.value = _forgotPasswordState.value.copy(isLoading = true)

            try {
                delay(2000)
                Log.d(TAG, "Password reset link sent successfully")
                _forgotPasswordState.value = _forgotPasswordState.value.copy(isLoading = false)
                _uiEvent.send(UiEvent.ShowSnackbar("Посилання для скидання пароля надіслано на ваш email"))
                _uiEvent.send(UiEvent.Navigate)
            } catch (e: Exception) {
                Log.e(TAG, "Error sending password reset: ${e.message}")
                _forgotPasswordState.value = _forgotPasswordState.value.copy(
                    isLoading = false,
                    errorMessageResetProcess = NetworkError.UNKNOWN
                )
                _uiEvent.send(UiEvent.ShowErrorSnackbar(NetworkError.UNKNOWN))
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        resetJob?.cancel()
        _uiEvent.close()
        Log.d(TAG, "ForgotPasswordViewModel cleared, jobs cancelled")
    }
}