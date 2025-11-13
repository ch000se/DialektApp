package com.example.dialektapp.presentation.screens.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dialektapp.domain.model.Course
import com.example.dialektapp.domain.model.User
import com.example.dialektapp.domain.model.UserStats
import com.example.dialektapp.domain.repository.AuthRepository
import com.example.dialektapp.domain.repository.CoursesRepository
import com.example.dialektapp.domain.util.onError
import com.example.dialektapp.domain.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val coursesRepository: CoursesRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    private val _stats = MutableStateFlow(UserStats(userId = "", totalCoins = 0, weeklyCoins = 0))
    val stats: StateFlow<UserStats> = _stats.asStateFlow()

    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses: StateFlow<List<Course>> = _courses.asStateFlow()

    private val _isLoadingCourses = MutableStateFlow(false)
    val isLoadingCourses: StateFlow<Boolean> = _isLoadingCourses.asStateFlow()

    init {
        Log.d("HomeViewModel", "HomeViewModel initialized")
        loadUserData()
        loadCourses()
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
}
