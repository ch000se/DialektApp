package com.example.dialektapp.presentation.screens.lessons

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dialektapp.domain.model.CourseModule
import com.example.dialektapp.domain.model.Lesson
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

data class ModuleLessonsUiState(
    val module: CourseModule? = null,
    val lessons: List<Lesson> = emptyList(),
    val expandedLessonId: String? = null,
    val isLoading: Boolean = false,
    val error: NetworkError? = null
)

@HiltViewModel
class ModuleLessonsViewModel @Inject constructor(
    private val coursesRepository: CoursesRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val moduleId: String = savedStateHandle.get<String>("moduleId") ?: ""

    private val _uiState = MutableStateFlow(ModuleLessonsUiState(isLoading = true))
    val uiState: StateFlow<ModuleLessonsUiState> = _uiState.asStateFlow()

    init {
        Log.d("ModuleLessonsVM", "Initialized with moduleId: $moduleId")
        loadModuleData()
    }

    private fun loadModuleData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            Log.d("ModuleLessonsVM", "Loading module data for moduleId: $moduleId")

            // Спробуємо отримати ID як Int
            val moduleIdInt = moduleId.toIntOrNull()
            if (moduleIdInt == null) {
                Log.e("ModuleLessonsVM", "Invalid moduleId: $moduleId")
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = NetworkError.UNKNOWN
                    )
                }
                return@launch
            }

            // Завантажуємо деталі модуля
            coursesRepository.getModule(moduleIdInt)
                .onSuccess { module ->
                    Log.d("ModuleLessonsVM", "Module loaded: ${module.title}")
                    _uiState.update { it.copy(module = module) }

                    // Після успішного завантаження модуля, завантажуємо уроки
                    loadLessons(moduleIdInt)
                }
                .onError { error ->
                    Log.e("ModuleLessonsVM", "Failed to load module: $error")
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = error
                        )
                    }
                }
        }
    }

    private suspend fun loadLessons(moduleId: Int) {
        Log.d("ModuleLessonsVM", "Loading lessons for module: $moduleId")

        coursesRepository.getModuleLessons(moduleId)
            .onSuccess { lessons ->
                Log.d("ModuleLessonsVM", "Lessons loaded: ${lessons.size} lessons")
                lessons.forEachIndexed { index, lesson ->
                    Log.d(
                        "ModuleLessonsVM",
                        "  $index. ${lesson.title} (${lesson.completedActivities}/${lesson.totalActivities} activities, unlocked: ${lesson.isUnlocked})"
                    )
                }
                _uiState.update {
                    it.copy(
                        lessons = lessons,
                        isLoading = false,
                        error = null
                    )
                }
            }
            .onError { error ->
                Log.e("ModuleLessonsVM", "Failed to load lessons: $error")
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = error
                    )
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
        Log.d("ModuleLessonsVM", "Retrying to load module data")
        loadModuleData()
    }
}
