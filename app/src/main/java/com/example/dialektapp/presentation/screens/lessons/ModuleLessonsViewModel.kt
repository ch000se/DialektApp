package com.example.dialektapp.presentation.screens.lessons

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dialektapp.domain.model.CourseModule
import com.example.dialektapp.domain.model.Lesson
import com.example.dialektapp.domain.usecases.courses.GetModuleWithLessonsUseCase
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

data class ModuleLessonsUiState(
    val module: CourseModule? = null,
    val lessons: List<Lesson> = emptyList(),
    val expandedLessonId: String? = null,
    val isLoading: Boolean = false,
    val error: NetworkError? = null
)

/**
 * ViewModel для відображення уроків модуля
 *
 * Завантажує модуль разом з усіма уроками через API курсів.
 * API повертає повну структуру курсу, з якої витягується потрібний модуль.
 */
@HiltViewModel
class ModuleLessonsViewModel @Inject constructor(
    private val getModuleWithLessonsUseCase: GetModuleWithLessonsUseCase,
    private val coursesRepository: com.example.dialektapp.domain.repository.CoursesRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val courseId: String = savedStateHandle.get<String>("courseId") ?: ""
    private val moduleId: String = savedStateHandle.get<String>("moduleId") ?: ""

    private val _uiState = MutableStateFlow(ModuleLessonsUiState(isLoading = true))
    val uiState: StateFlow<ModuleLessonsUiState> = _uiState.asStateFlow()

    private val TAG = "ModuleLessonsVM"
    private var loadJob: Job? = null

    init {
        Log.d(TAG, "Initialized with courseId: $courseId, moduleId: $moduleId")
        loadModuleData()
    }

    private fun loadModuleData() {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            Log.d(TAG, "Loading module data for courseId: $courseId, moduleId: $moduleId")

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

            // Завантажуємо модуль з курсу (модуль вже містить всі уроки)
            getModuleWithLessonsUseCase(courseIdInt, moduleId)
                .onSuccess { module ->
                    Log.d(TAG, "Module loaded: ${module.title} with ${module.lessons.size} lessons")
                    module.lessons.forEachIndexed { index, lesson ->
                        Log.d(
                            TAG,
                            "  $index. ${lesson.title} (${lesson.completedActivities}/${lesson.totalActivities} activities, unlocked: ${lesson.isUnlocked})"
                        )
                    }

                    _uiState.update {
                        it.copy(
                            module = module,
                            lessons = module.lessons,
                            isLoading = false,
                            error = null
                        )
                    }
                }
                .onError { error ->
                    Log.e(TAG, "Failed to load module: $error")
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = error
                        )
                    }
                }
        }
    }

    fun toggleLessonExpansion(lessonId: String) {
        _uiState.update {
            it.copy(
                expandedLessonId = if (it.expandedLessonId == lessonId) null else lessonId
            )
        }
    }

    fun retry() {
        Log.d(TAG, "Retrying to load module data")
        loadModuleData()
    }

    fun refresh() {
        Log.d(TAG, "Refreshing module data")
        // Завжди перезавантажуємо повні дані через правильний endpoint
        loadModuleData()
    }

    override fun onCleared() {
        super.onCleared()
        loadJob?.cancel()
        Log.d(TAG, "ModuleLessonsViewModel cleared, jobs cancelled")
    }
}
