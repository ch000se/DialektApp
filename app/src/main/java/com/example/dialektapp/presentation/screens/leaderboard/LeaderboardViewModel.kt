package com.example.dialektapp.presentation.screens.leaderboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dialektapp.domain.model.LeaderboardData
import com.example.dialektapp.domain.model.LeaderboardPeriod
import com.example.dialektapp.domain.usecases.leaderboard.GetLeaderboardUseCase
import com.example.dialektapp.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LeaderboardUiState(
    val leaderboardData: LeaderboardData = LeaderboardData(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedPeriod: LeaderboardPeriod = LeaderboardPeriod.ALL_TIME
)

@HiltViewModel
class LeaderboardViewModel @Inject constructor(
    private val getLeaderboardUseCase: GetLeaderboardUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LeaderboardUiState())
    val uiState = _uiState.asStateFlow()

    private val TAG = "LeaderboardViewModel"
    private var loadJob: Job? = null

    init {
        loadLeaderboard(LeaderboardPeriod.ALL_TIME)
    }

    fun loadLeaderboard(period: LeaderboardPeriod) {
        loadJob?.cancel() // Скасовуємо попереднє завантаження
        loadJob = viewModelScope.launch {
            Log.d(TAG, "Loading leaderboard for period: $period")
            _uiState.update { it.copy(isLoading = true, error = null, selectedPeriod = period) }

            when (val result = getLeaderboardUseCase(period)) {
                is Result.Success -> {
                    Log.d(TAG, "Leaderboard loaded: ${result.data.topEntries.size} entries")
                    result.data.currentUserEntry?.let {
                        Log.d(TAG, "Current user: rank ${it.rank}, ${it.coins} coins")
                    }
                    _uiState.update {
                        it.copy(
                            leaderboardData = result.data,
                            isLoading = false
                        )
                    }
                }

                is Result.Error -> {
                    val errorMessage = mapNetworkError(result.error)
                    Log.e(TAG, "Error loading leaderboard: $errorMessage")
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = errorMessage
                        )
                    }
                }
            }
        }
    }

    fun onPeriodChanged(period: LeaderboardPeriod) {
        if (period != _uiState.value.selectedPeriod) {
            loadLeaderboard(period)
        }
    }

    fun retry() {
        loadLeaderboard(_uiState.value.selectedPeriod)
    }

    private fun mapNetworkError(error: com.example.dialektapp.domain.util.NetworkError): String {
        return when (error) {
            com.example.dialektapp.domain.util.NetworkError.NO_INTERNET ->
                "Немає підключення до інтернету"

            com.example.dialektapp.domain.util.NetworkError.REQUEST_TIMEOUT ->
                "Час очікування вичерпано"

            com.example.dialektapp.domain.util.NetworkError.UNAUTHORIZED ->
                "Необхідна авторизація"

            com.example.dialektapp.domain.util.NetworkError.TOO_MANY_REQUESTS ->
                "Занадто багато запитів"

            com.example.dialektapp.domain.util.NetworkError.SERVER_UNAVAILABLE ->
                "Сервер недоступний"

            com.example.dialektapp.domain.util.NetworkError.SERVER_ERROR ->
                "Помилка сервера"

            com.example.dialektapp.domain.util.NetworkError.SERIALIZATION_ERROR ->
                "Помилка обробки даних"

            com.example.dialektapp.domain.util.NetworkError.UNKNOWN ->
                "Невідома помилка"

            com.example.dialektapp.domain.util.NetworkError.NONE ->
                "Помилка"
        }
    }

    override fun onCleared() {
        super.onCleared()
        loadJob?.cancel()
        Log.d(TAG, "LeaderboardViewModel cleared, jobs cancelled")
    }
}
