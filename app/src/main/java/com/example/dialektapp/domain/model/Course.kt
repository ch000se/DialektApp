package com.example.dialektapp.domain.model

data class Course(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val imageUrlBack: String,
    val totalModules: Int = 0,
    val completedModules: Int = 0,
    val modules: List<CourseModule> = emptyList()
)

data class CourseModule(
    val id: String,
    val courseId: String,
    val title: String,
    val subtitle: String,
    val description: String,
    val isUnlocked: Boolean,
    val isCompleted: Boolean,
    val progress: Int = 0,
    val lessons: List<Lesson> = emptyList()
)
