package com.example.dialektapp.presentation.screens.course

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dialektapp.domain.model.Course
import com.example.dialektapp.domain.model.CourseModule
import com.example.dialektapp.domain.usecases.courses.GetCourseUseCase
import com.example.dialektapp.domain.util.NetworkError
import com.example.dialektapp.domain.util.onError
import com.example.dialektapp.domain.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CourseDetailUiState(
    val course: Course? = null,
    val modules: List<CourseModule> = emptyList(),
    val isLoading: Boolean = false,
    val error: NetworkError? = null,
    val expandedModuleId: String? = null
)

@HiltViewModel
class CourseDetailViewModel @Inject constructor(
    private val getCourseUseCase: GetCourseUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val courseId: String = savedStateHandle.get<String>("courseId") ?: ""

    private val _uiState = MutableStateFlow(CourseDetailUiState(isLoading = true))
    val uiState: StateFlow<CourseDetailUiState> = _uiState.asStateFlow()

    private val TAG = "CourseDetailVM"
    private var loadJob: Job? = null

    init {
        Log.d(TAG, "Initialized with courseId: $courseId")
        loadCourseDetails()
    }

    private fun loadCourseDetails() {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            Log.d(TAG, "Loading course details for courseId: $courseId")

            val courseIdInt = courseId.toIntOrNull()
            if (courseIdInt == null) {
                Log.e(TAG, "Invalid courseId: $courseId")
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = NetworkError.UNKNOWN
                    )
                }
                return@launch
            }

            // Завантажуємо курс з повною структурою (модулі вже включені)
            getCourseUseCase(courseIdInt)
                .onSuccess { course ->
                    Log.d(TAG, "Course loaded: ${course.name} with ${course.modules.size} modules")
                    course.modules.forEachIndexed { index, module ->
                        Log.d(
                            TAG,
                            "  $index. ${module.title} (${module.progress}% completed, unlocked: ${module.isUnlocked})"
                        )
                    }
                    _uiState.update {
                        it.copy(
                            course = course,
                            modules = course.modules,
                            isLoading = false,
                            error = null
                        )
                    }
                }
                .onError { error ->
                    Log.e(TAG, "Failed to load course: $error")
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = error
                        )
                    }
                }
        }
    }

    fun toggleModuleExpansion(moduleId: String) {
        _uiState.update {
            it.copy(
                expandedModuleId = if (it.expandedModuleId == moduleId) null else moduleId
            )
        }
    }

    fun retry() {
        Log.d(TAG, "Retrying to load course details")
        loadCourseDetails()
    }

    override fun onCleared() {
        super.onCleared()
        loadJob?.cancel()
        Log.d(TAG, "CourseDetailViewModel cleared, jobs cancelled")
    }
}
