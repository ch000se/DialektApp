package com.example.dialektapp.presentation.screens.activity

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dialektapp.domain.model.ActivityDetail
import com.example.dialektapp.domain.usecases.activities.GetActivityDetailUseCase
import com.example.dialektapp.domain.usecases.activities.UpdateActivityProgressUseCase
import com.example.dialektapp.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ActivityUiState(
    val activityDetail: ActivityDetail? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isCompleted: Boolean = false,
    val currentScore: Int = 0
)

@HiltViewModel
class ActivityViewModel @Inject constructor(
    private val getActivityDetailUseCase: GetActivityDetailUseCase,
    private val updateActivityProgressUseCase: UpdateActivityProgressUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ActivityUiState())
    val uiState = _uiState.asStateFlow()

    private val TAG = "ActivityViewModel"
    private var loadJob: Job? = null
    private var completeJob: Job? = null
    private var unlockJob: Job? = null

    fun loadActivity(activityId: String) {
        loadJob?.cancel() // Скасовуємо попереднє завантаження
        loadJob = viewModelScope.launch {
            Log.d(TAG, "Loading activity: $activityId")
            _uiState.update { it.copy(isLoading = true, error = null) }

            when (val result = getActivityDetailUseCase(activityId.toInt())) {
                is Result.Success -> {
                    Log.d(TAG, "Activity loaded successfully: ${result.data.activity.name}")
                    _uiState.update {
                        it.copy(
                            activityDetail = result.data,
                            isLoading = false,
                            isCompleted = result.data.activity.isCompleted
                        )
                    }
                }

                is Result.Error -> {
                    val errorMessage = when (result.error) {
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
                    Log.e(TAG, "Error loading activity: $errorMessage")
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

    fun completeActivity(score: Int? = null) {
        val currentActivity = _uiState.value.activityDetail?.activity ?: return

        completeJob?.cancel() // Скасовуємо попереднє завершення
        completeJob = viewModelScope.launch {
            Log.d(TAG, "Completing activity: ${currentActivity.id}, score: $score")

            _uiState.update { it.copy(isLoading = true) }

            val result = updateActivityProgressUseCase(
                activityId = currentActivity.id.toInt(),
                status = "completed",
                score = score,
                addAttempt = true
            )

            when (result) {
                is Result.Success -> {
                    Log.d(TAG, "Activity completed successfully")
                    _uiState.update {
                        it.copy(
                            isCompleted = true,
                            isLoading = false,
                            currentScore = score ?: 0
                        )
                    }
                }

                is Result.Error -> {
                    Log.e(TAG, "Error completing activity: ${result.error}")
                    // Все одно позначаємо як виконане локально
                    _uiState.update {
                        it.copy(
                            isCompleted = true,
                            isLoading = false,
                            currentScore = score ?: 0
                        )
                    }
                }
            }
        }
    }

    fun unlockActivity(activityId: Int) {
        unlockJob?.cancel() // Скасовуємо попереднє розблокування
        unlockJob = viewModelScope.launch {
            Log.d(TAG, "Unlocking activity: $activityId")

            updateActivityProgressUseCase(
                activityId = activityId,
                isUnlocked = true
            )
        }
    }

    fun retry() {
        val activityId = _uiState.value.activityDetail?.activity?.id
        if (activityId != null) {
            loadActivity(activityId.toString())
        }
    }

    override fun onCleared() {
        super.onCleared()
        loadJob?.cancel()
        completeJob?.cancel()
        unlockJob?.cancel()
        Log.d(TAG, "ActivityViewModel cleared, jobs cancelled")
    }
}
