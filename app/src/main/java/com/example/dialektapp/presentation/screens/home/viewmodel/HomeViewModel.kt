package com.example.dialektapp.presentation.screens.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dialektapp.domain.model.Course
import com.example.dialektapp.domain.model.DailyStreakData
import com.example.dialektapp.domain.model.User
import com.example.dialektapp.domain.model.UserStats
import com.example.dialektapp.domain.repository.AuthRepository
import com.example.dialektapp.domain.repository.CoursesRepository
import com.example.dialektapp.domain.repository.StreakRepository
import com.example.dialektapp.domain.util.onError
import com.example.dialektapp.domain.util.onSuccess
import com.example.dialektapp.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val streakData: DailyStreakData? = null,
    val isLoadingStreak: Boolean = false,
    val isClaimingStreak: Boolean = false,
    val streakError: String? = null,
    val showRewardDialog: Boolean = false,
    val lastClaimedReward: Int? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val coursesRepository: CoursesRepository,
    private val authRepository: AuthRepository,
    private val streakRepository: StreakRepository
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    private val _stats = MutableStateFlow(UserStats(userId = "", totalCoins = 0, weeklyCoins = 0))
    val stats: StateFlow<UserStats> = _stats.asStateFlow()

    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses: StateFlow<List<Course>> = _courses.asStateFlow()

    private val _isLoadingCourses = MutableStateFlow(false)
    val isLoadingCourses: StateFlow<Boolean> = _isLoadingCourses.asStateFlow()

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private val TAG = "HomeViewModel"

    init {
        Log.d("HomeViewModel", "HomeViewModel initialized")
        loadUserData()
        loadCourses()
        loadStreak()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            Log.d("HomeViewModel", "Loading user data...")
            authRepository.getCurrentUser()
                .onSuccess { user ->
                    Log.d("HomeViewModel", "User loaded: ${user.fullName} (${user.email})")
                    _user.value = user
                    _stats.value = UserStats(
                        userId = user.id,
                        totalCoins = 1247, // Mock поки немає API
                        weeklyCoins = 320  // Mock поки немає API
                    )
                }
                .onError { error ->
                    Log.e("HomeViewModel", "Failed to load user: $error")
                }
        }
    }

    private fun loadCourses() {
        viewModelScope.launch {
            Log.d("HomeViewModel", "Loading courses...")
            _isLoadingCourses.value = true

            coursesRepository.getMyCourses()
                .onSuccess { courses ->
                    Log.d("HomeViewModel", "Courses loaded: ${courses.size} courses")
                    courses.forEachIndexed { index, course ->
                        Log.d(
                            "HomeViewModel",
                            "  $index. ${course.name} (${course.completedModules}/${course.totalModules} modules)"
                        )
                    }
                    _courses.value = courses
                    _isLoadingCourses.value = false
                }
                .onError { error ->
                    Log.e("HomeViewModel", "Failed to load courses: $error")
                    _isLoadingCourses.value = false

                }
        }
    }

    fun refreshData() {
        Log.d("HomeViewModel", "Refreshing data...")
        loadUserData()
        loadCourses()
    }

    fun loadStreak() {
        viewModelScope.launch {
            Log.d(TAG, "Loading streak data")
            _uiState.update { it.copy(isLoadingStreak = true, streakError = null) }

            when (val result = streakRepository.getMyStreak()) {
                is Result.Success -> {
                    Log.d(
                        TAG,
                        "Streak loaded: day ${result.data.activeDay}/${result.data.totalDays}"
                    )
                    _uiState.update {
                        it.copy(
                            streakData = result.data,
                            isLoadingStreak = false,
                            streakError = null
                        )
                    }
                }

                is Result.Error -> {
                    val errorMessage = mapNetworkError(result.error)
                    Log.e(TAG, "Error loading streak: $errorMessage")
                    _uiState.update {
                        it.copy(
                            isLoadingStreak = false,
                            streakError = errorMessage
                        )
                    }
                }
            }
        }
    }

    fun claimStreak() {
        viewModelScope.launch {
            Log.d(TAG, "Claiming streak reward")
            _uiState.update { it.copy(isClaimingStreak = true) }

            when (val result = streakRepository.claimStreak()) {
                is Result.Success -> {
                    Log.d(
                        TAG,
                        "Streak claimed successfully: ${result.data.todayRewardAmount} coins"
                    )

                    // Додаємо монети до поточного балансу
                    val currentCoins = _stats.value.totalCoins
                    val newCoins = currentCoins + result.data.todayRewardAmount
                    _stats.update {
                        it.copy(totalCoins = newCoins)
                    }

                    _uiState.update {
                        it.copy(
                            streakData = result.data,
                            isClaimingStreak = false,
                            lastClaimedReward = result.data.todayRewardAmount,
                            showRewardDialog = false
                        )
                    }
                }

                is Result.Error -> {
                    val errorMessage = mapNetworkError(result.error)
                    Log.e(TAG, "Error claiming streak: $errorMessage")
                    _uiState.update {
                        it.copy(
                            isClaimingStreak = false,
                            streakError = errorMessage,
                            showRewardDialog = false
                        )
                    }
                }
            }
        }
    }

    fun showRewardDialog() {
        _uiState.update { it.copy(showRewardDialog = true) }
    }

    fun hideRewardDialog() {
        _uiState.update { it.copy(showRewardDialog = false) }
    }

    fun clearLastClaimedReward() {
        _uiState.update { it.copy(lastClaimedReward = null) }
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
}
