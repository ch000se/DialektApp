package com.example.dialektapp.presentation.util

import com.example.dialektapp.domain.util.NetworkError

sealed class UiEvent {
    data class ShowSnackbar(
        val message: NetworkError,
        val actionLabel: String? = null
    ) : UiEvent()
    data object Navigate: UiEvent()
}