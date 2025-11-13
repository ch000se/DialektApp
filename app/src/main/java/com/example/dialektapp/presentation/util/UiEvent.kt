package com.example.dialektapp.presentation.util

import com.example.dialektapp.domain.util.NetworkError

sealed class UiEvent {
    data class ShowSnackbar(
        val message: String,
        val actionLabel: String? = null,
        val isError: Boolean = false,
    ) : UiEvent()

    data class ShowErrorSnackbar(
        val error: NetworkError,
        val actionLabel: String? = null,
    ) : UiEvent()

    data object Navigate: UiEvent()
}