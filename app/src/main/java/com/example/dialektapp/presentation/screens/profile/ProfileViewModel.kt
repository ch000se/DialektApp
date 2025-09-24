package com.example.dialektapp.presentation.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dialektapp.domain.model.User
import com.example.dialektapp.domain.usecases.GetCurrentUserUseCase
import com.example.dialektapp.domain.usecases.LogoutUseCase
import com.example.dialektapp.domain.util.NetworkError
import com.example.dialektapp.domain.util.onError
import com.example.dialektapp.domain.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val logoutUseCase: LogoutUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadProfile()
    }

    fun loadProfile() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            getCurrentUserUseCase()
                .onSuccess { user ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        user = user,
                        error = null
                    )
                }
                .onError { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = error
                    )
                }
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
            _uiState.value = _uiState.value.copy(
                user = null,
                isLoggedOut = true
            )
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}

data class ProfileUiState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: NetworkError? = null,
    val isLoggedOut: Boolean = false,
)