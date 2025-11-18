package com.example.dialektapp.presentation.screens.activity

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dialektapp.data.remote.dto.ActivityStatus
import com.example.dialektapp.domain.model.ActivityDetail
import com.example.dialektapp.domain.model.ActivityType
import com.example.dialektapp.domain.usecases.activities.GetActivityDetailUseCase
import com.example.dialektapp.domain.usecases.activities.UpdateActivityProgressUseCase
import com.example.dialektapp.domain.usecases.stats.GetMyStatsUseCase
import com.example.dialektapp.domain.repository.CoursesRepository
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
    val currentScore: Int = 0,
    val coinsEarned: Int? = null,
    val showRewardDialog: Boolean = false
)

@HiltViewModel
class ActivityViewModel @Inject constructor(
    private val getActivityDetailUseCase: GetActivityDetailUseCase,
    private val updateActivityProgressUseCase: UpdateActivityProgressUseCase,
    private val getMyStatsUseCase: GetMyStatsUseCase,
    private val coursesRepository: CoursesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ActivityUiState())
    val uiState = _uiState.asStateFlow()

    private val TAG = "ActivityViewModel"
    private var loadJob: Job? = null
    private var completeJob: Job? = null
    private var currentLessonId: String? = null
    private var currentCourseId: String? = null

    fun loadActivity(activityId: String, lessonId: String, courseId: String) {
        loadJob?.cancel() // Скасовуємо попереднє завантаження
        loadJob = viewModelScope.launch {
            Log.d(TAG, "Loading activity: $activityId from lesson: $lessonId, course: $courseId")
            currentLessonId = lessonId
            currentCourseId = courseId
            _uiState.update { it.copy(isLoading = true, error = null) }

            // Отримуємо isCompleted через /courses/me/{course_id}
            var isCompleted = false

            val courseResult = coursesRepository.getMyCourse(courseId.toInt())
            if (courseResult is Result.Success) {
                val course = courseResult.data
                // Знаходимо активність в структурі курсу
                course.modules.forEach { module ->
                    module.lessons.forEach { lesson ->
                        lesson.activities.forEach { activity ->
                            if (activity.id.toString() == activityId) {
                                isCompleted = activity.isCompleted
                                Log.d(
                                    TAG,
                                    "Found activity in course structure: isCompleted=$isCompleted"
                                )
                            }
                        }
                    }
                }
            } else {
                Log.e(TAG, "Failed to get course: ${(courseResult as? Result.Error)?.error}")
            }

            Log.d(TAG, "Activity isCompleted from server: $isCompleted")

            // Тепер завантажуємо деталі активності
            when (val result = getActivityDetailUseCase(activityId.toInt())) {
                is Result.Success -> {
                    Log.d(TAG, "Activity loaded successfully: ${result.data.activity.name}")
                    _uiState.update {
                        it.copy(
                            activityDetail = result.data,
                            isLoading = false,
                            isCompleted = isCompleted
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

        Log.d(TAG, "=== completeActivity called ===")
        Log.d(TAG, "Activity ID: ${currentActivity.id}")
        Log.d(TAG, "Activity name: ${currentActivity.name}")
        Log.d(TAG, "Activity isCompleted: ${_uiState.value.isCompleted}")
        Log.d(TAG, "Current isLoading: ${_uiState.value.isLoading}")
        Log.d(TAG, "Score: $score")

        // Якщо активність вже завершена - не обробляємо повторно
        if (_uiState.value.isCompleted) {
            Log.d(TAG, "❌ Activity already completed, ignoring")
            return
        }

        // Якщо вже йде процес завершення - не дозволяємо подвійне натискання
        if (_uiState.value.isLoading) {
            Log.d(TAG, "❌ Completion already in progress, ignoring")
            return
        }

        completeJob?.cancel() // Скасовуємо попереднє завершення
        completeJob = viewModelScope.launch {
            Log.d(TAG, "✅ Starting activity completion process...")

            _uiState.update { it.copy(isLoading = true) }

            // Отримуємо баланс монет ДО завершення активності
            val coinsBefore = when (val statsResult = getMyStatsUseCase()) {
                is Result.Success -> statsResult.data.totalCoins
                is Result.Error -> {
                    Log.e(TAG, "Failed to get stats before completion")
                    null
                }
            }
            Log.d(TAG, "Coins before completion: $coinsBefore")

            val result = updateActivityProgressUseCase(
                activityId = currentActivity.id.toInt(),
                status = ActivityStatus.COMPLETED,
                score = score,
                addAttempt = true
            )

            when (result) {
                is Result.Success -> {
                    Log.d(TAG, "✅ Server confirmed activity completion")

                    // Отримуємо баланс монет ПІСЛЯ завершення активності
                    val coinsAfter = when (val statsResult = getMyStatsUseCase()) {
                        is Result.Success -> statsResult.data.totalCoins
                        is Result.Error -> {
                            Log.e(TAG, "Failed to get stats after completion")
                            null
                        }
                    }
                    Log.d(TAG, "Coins after completion: $coinsAfter")

                    // Рахуємо скільки монет нараховано (різниця)
                    val coinsEarned = if (coinsBefore != null && coinsAfter != null) {
                        (coinsAfter - coinsBefore).coerceAtLeast(0)
                    } else {
                        null
                    }

                    _uiState.update {
                        it.copy(
                            isCompleted = true,
                            isLoading = false,
                            currentScore = score ?: 0,
                            coinsEarned = coinsEarned,
                            showRewardDialog = coinsEarned != null && coinsEarned > 0,
                            // Оновлюємо activity як завершену
                            activityDetail = it.activityDetail?.copy(
                                activity = it.activityDetail.activity.copy(isCompleted = true)
                            )
                        )
                    }
                    Log.d(
                        TAG,
                        "✅ UI state updated with coins: $coinsEarned, showDialog: ${coinsEarned != null && coinsEarned > 0}"
                    )
                }

                is Result.Error -> {
                    Log.e(TAG, "Error completing activity: ${result.error}")
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = "Помилка завершення активності"
                        )
                    }
                }
            }
        }
    }

    fun dismissRewardDialog() {
        _uiState.update { it.copy(showRewardDialog = false) }
    }

    fun retry() {
        val activityId = _uiState.value.activityDetail?.activity?.id
        val lessonId = currentLessonId
        val courseId = currentCourseId
        if (activityId != null && lessonId != null && courseId != null) {
            loadActivity(activityId.toString(), lessonId, courseId)
        }
    }

    override fun onCleared() {
        super.onCleared()
        loadJob?.cancel()
        completeJob?.cancel()
        Log.d(TAG, "ActivityViewModel cleared, jobs cancelled")
    }
}
