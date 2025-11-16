package com.example.dialektapp.presentation.screens.lessons

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dialektapp.domain.model.CourseModule
import com.example.dialektapp.domain.model.Lesson
import com.example.dialektapp.domain.usecases.courses.GetModuleLessonsUseCase
import com.example.dialektapp.domain.usecases.courses.GetModuleUseCase
import com.example.dialektapp.domain.util.NetworkError
import com.example.dialektapp.domain.util.onError
import com.example.dialektapp.domain.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
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
    private val getModuleUseCase: GetModuleUseCase,
    private val getModuleLessonsUseCase: GetModuleLessonsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val moduleId: String = savedStateHandle.get<String>("moduleId") ?: ""

    private val _uiState = MutableStateFlow(ModuleLessonsUiState(isLoading = true))
    val uiState: StateFlow<ModuleLessonsUiState> = _uiState.asStateFlow()

    private val TAG = "ModuleLessonsVM"
    private var loadJob: Job? = null

    init {
        Log.d(TAG, "Initialized with moduleId: $moduleId")
        loadModuleData()
    }

    private fun loadModuleData() {
        loadJob?.cancel() // Скасовуємо попереднє завантаження
        loadJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            Log.d(TAG, "Loading module data for moduleId: $moduleId")

            // Спробуємо отримати ID як Int
            val moduleIdInt = moduleId.toIntOrNull()
            if (moduleIdInt == null) {
                Log.e(TAG, "Invalid moduleId: $moduleId")
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = NetworkError.UNKNOWN
                    )
                }
                return@launch
            }

            // Завантажуємо модуль і уроки паралельно
            val moduleDeferred = async {
                getModuleUseCase(moduleIdInt)
            }
            val lessonsDeferred = async {
                getModuleLessonsUseCase(moduleIdInt)
            }

            val moduleResult = moduleDeferred.await()
            val lessonsResult = lessonsDeferred.await()

            // Обробляємо результат завантаження модуля
            moduleResult
                .onSuccess { module ->
                    Log.d(TAG, "Module loaded: ${module.title}")
                    _uiState.update { it.copy(module = module) }
                }
                .onError { error ->
                    Log.e(TAG, "Failed to load module: $error")
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = error
                        )
                    }
                    return@launch
                }

            // Обробляємо результат завантаження уроків
            lessonsResult
                .onSuccess { lessons ->
                    Log.d(TAG, "Lessons loaded: ${lessons.size} lessons")
                    lessons.forEachIndexed { index, lesson ->
                        Log.d(
                            TAG,
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
                    Log.e(TAG, "Failed to load lessons: $error")
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

    override fun onCleared() {
        super.onCleared()
        loadJob?.cancel()
        Log.d(TAG, "ModuleLessonsViewModel cleared, jobs cancelled")
    }
}
