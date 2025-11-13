package com.example.dialektapp.presentation.screens.course

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dialektapp.domain.model.Course
import com.example.dialektapp.domain.model.CourseModule
import com.example.dialektapp.domain.repository.CoursesRepository
import com.example.dialektapp.domain.util.NetworkError
import com.example.dialektapp.domain.util.onError
import com.example.dialektapp.domain.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val coursesRepository: CoursesRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val courseId: String = savedStateHandle.get<String>("courseId") ?: ""

    private val _uiState = MutableStateFlow(CourseDetailUiState(isLoading = true))
    val uiState: StateFlow<CourseDetailUiState> = _uiState.asStateFlow()

    init {
        Log.d("CourseDetailVM", "Initialized with courseId: $courseId")
        loadCourseDetails()
    }

    private fun loadCourseDetails() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            Log.d("CourseDetailVM", "Loading course details for courseId: $courseId")

            // Спробуємо отримати ID як Int
            val courseIdInt = courseId.toIntOrNull()
            if (courseIdInt == null) {
                Log.e("CourseDetailVM", "Invalid courseId: $courseId")
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = NetworkError.UNKNOWN
                    )
                }
                return@launch
            }

            // Завантажуємо курс
            coursesRepository.getCourse(courseIdInt)
                .onSuccess { course ->
                    Log.d("CourseDetailVM", "Course loaded: ${course.name}")
                    _uiState.update { it.copy(course = course) }

                    // Після успішного завантаження курсу, завантажуємо модулі
                    loadModules(courseIdInt)
                }
                .onError { error ->
                    Log.e("CourseDetailVM", "Failed to load course: $error")
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = error
                        )
                    }
                }
        }
    }

    private suspend fun loadModules(courseId: Int) {
        Log.d("CourseDetailVM", "Loading modules for course: $courseId")

        coursesRepository.getCourseModules(courseId)
            .onSuccess { modules ->
                Log.d("CourseDetailVM", "Modules loaded: ${modules.size} modules")
                modules.forEachIndexed { index, module ->
                    Log.d(
                        "CourseDetailVM",
                        "  $index. ${module.title} (${module.progress}% completed, unlocked: ${module.isUnlocked})"
                    )
                }
                _uiState.update {
                    it.copy(
                        modules = modules,
                        isLoading = false,
                        error = null
                    )
                }
            }
            .onError { error ->
                Log.e("CourseDetailVM", "Failed to load modules: $error")
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = error
                    )
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
        Log.d("CourseDetailVM", "Retrying to load course details")
        loadCourseDetails()
    }
}
