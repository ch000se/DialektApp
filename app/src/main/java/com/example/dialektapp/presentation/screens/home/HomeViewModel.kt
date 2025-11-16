package com.example.dialektapp.presentation.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dialektapp.domain.model.Course
import com.example.dialektapp.domain.model.DailyStreakData
import com.example.dialektapp.domain.model.User
import com.example.dialektapp.domain.model.UserStats
import com.example.dialektapp.domain.usecases.auth.GetCurrentUserUseCase
import com.example.dialektapp.domain.usecases.courses.GetMyCoursesUseCase
import com.example.dialektapp.domain.usecases.leaderboard.GetLeaderboardUseCase
import com.example.dialektapp.domain.usecases.streak.ClaimStreakUseCase
import com.example.dialektapp.domain.usecases.streak.GetStreakUseCase
import com.example.dialektapp.domain.util.onError
import com.example.dialektapp.domain.util.onSuccess
import com.example.dialektapp.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
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
    private val getMyCoursesUseCase: GetMyCoursesUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getStreakUseCase: GetStreakUseCase,
    private val claimStreakUseCase: ClaimStreakUseCase,
    private val getLeaderboardUseCase: GetLeaderboardUseCase
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
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    // Exposed flows for optimized observation
    val isLoading: StateFlow<Boolean> = _isLoadingCourses
        .map { it || _uiState.value.isLoadingStreak }
        .stateIn(
            viewModelScope,
            kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
            false
        )

    private val TAG = "HomeViewModel"
    private var loadDataJob: Job? = null

    init {
        Log.d("HomeViewModel", "HomeViewModel initialized")
        loadAllData()
    }

    // Паралельне завантаження всіх даних
    private fun loadAllData() {
        loadDataJob?.cancel() // Скасовуємо попередню завантажку якщо є
        loadDataJob = viewModelScope.launch {
            Log.d(TAG, "Loading all data in parallel...")

            // Запускаємо всі операції паралельно
            val userDeferred = async { loadUserDataInternal() }
            val coursesDeferred = async { loadCoursesInternal() }
            val streakDeferred = async { loadStreakInternal() }

            // Очікуємо завершення всіх операцій
            awaitAll(userDeferred, coursesDeferred, streakDeferred)

            Log.d(TAG, "All data loaded")
        }
    }

    private suspend fun loadUserDataInternal() {
        Log.d(TAG, "Loading user data...")
        getCurrentUserUseCase()
            .onSuccess { user ->
                Log.d(TAG, "User loaded: ${user.fullName} (${user.email})")
                _user.value = user
                loadUserCoinsInternal()
            }
            .onError { error ->
                Log.e(TAG, "Failed to load user: $error")
            }
    }

    private suspend fun loadUserCoinsInternal() {
        when (val result =
            getLeaderboardUseCase(com.example.dialektapp.domain.model.LeaderboardPeriod.ALL_TIME)) {
            is Result.Success -> {
                val coins = result.data.currentUserEntry?.coins ?: 0
                Log.d(TAG, "User coins loaded: $coins")
                _stats.update {
                    it.copy(
                        userId = _user.value?.id ?: "",
                        totalCoins = coins,
                        weeklyCoins = 320  // Mock поки немає API
                    )
                }
            }

            is Result.Error -> {
                Log.e(TAG, "Failed to load user coins: ${result.error}")
                _stats.update {
                    it.copy(
                        userId = _user.value?.id ?: "",
                        totalCoins = 1247,
                        weeklyCoins = 320
                    )
                }
            }
        }
    }

    private suspend fun loadCoursesInternal() {
        Log.d(TAG, "Loading courses...")
        _isLoadingCourses.value = true

        getMyCoursesUseCase()
            .onSuccess { courses ->
                Log.d(TAG, "Courses loaded: ${courses.size} courses")
                courses.forEachIndexed { index, course ->
                    Log.d(
                        TAG,
                        "  $index. ${course.name} (${course.completedModules}/${course.totalModules} modules)"
                    )
                }
                _courses.value = courses
            }
            .onError { error ->
                Log.e(TAG, "Failed to load courses: $error")
            }

        _isLoadingCourses.value = false
    }

    private suspend fun loadStreakInternal() {
        Log.d(TAG, "Loading streak data")
        _uiState.update { it.copy(isLoadingStreak = true, streakError = null) }

        when (val result = getStreakUseCase()) {
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

    fun refreshData() {
        Log.d(TAG, "Refreshing data...")
        loadAllData()
    }

    fun loadStreak() {
        viewModelScope.launch {
            loadStreakInternal()
        }
    }

    fun claimStreak() {
        viewModelScope.launch {
            Log.d(TAG, "Claiming streak reward")
            _uiState.update { it.copy(isClaimingStreak = true) }

            when (val result = claimStreakUseCase()) {
                is Result.Success -> {
                    Log.d(
                        TAG,
                        "Streak claimed successfully: ${result.data.todayRewardAmount} coins"
                    )

                    // Оновлюємо баланс оптимістично
                    val currentCoins = _stats.value.totalCoins
                    val newCoins = currentCoins + result.data.todayRewardAmount
                    _stats.update {
                        it.copy(totalCoins = newCoins)
                    }

                    // Асинхронно перезавантажуємо реальний баланс
                    viewModelScope.launch {
                        loadUserCoinsInternal()
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

    override fun onCleared() {
        super.onCleared()
        loadDataJob?.cancel()
        Log.d(TAG, "HomeViewModel cleared, jobs cancelled")
    }
}
