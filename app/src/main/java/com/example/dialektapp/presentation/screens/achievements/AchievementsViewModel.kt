package com.example.dialektapp.presentation.screens.achievements

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dialektapp.domain.model.Achievement
import com.example.dialektapp.domain.usecases.achievements.GetMyAchievementsUseCase
import com.example.dialektapp.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AchievementsUiState(
    val achievements: List<Achievement> = emptyList(),
    val selectedAchievement: Achievement? = null,
    val unlockedCount: Int = 0,
    val totalCount: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class AchievementsViewModel @Inject constructor(
    private val getMyAchievementsUseCase: GetMyAchievementsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AchievementsUiState())
    val uiState = _uiState.asStateFlow()

    private val TAG = "AchievementsViewModel"
    private var loadJob: Job? = null

    init {
        loadAchievements()
    }

    fun loadAchievements() {
        loadJob?.cancel() // Скасовуємо попереднє завантаження
        loadJob = viewModelScope.launch {
            Log.d(TAG, "Loading achievements")
            _uiState.update { it.copy(isLoading = true, error = null) }

            when (val result = getMyAchievementsUseCase()) {
                is Result.Success -> {
                    val achievements = result.data
                    Log.d(TAG, "Achievements loaded: ${achievements.size} total")
                    val unlockedCount = achievements.count { it.isUnlocked }
                    Log.d(TAG, "Unlocked: $unlockedCount/${achievements.size}")

                    _uiState.update {
                        it.copy(
                            achievements = achievements,
                            unlockedCount = unlockedCount,
                            totalCount = achievements.size,
                            isLoading = false
                        )
                    }
                }

                is Result.Error -> {
                    val errorMessage = mapNetworkError(result.error)
                    Log.e(TAG, "Error loading achievements: $errorMessage")
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

    fun retry() {
        loadAchievements()
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
        Log.d(TAG, "AchievementsViewModel cleared, jobs cancelled")
    }
}
