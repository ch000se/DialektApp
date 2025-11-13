package com.example.dialektapp.domain.model

enum class ActivityType {
    INTRODUCTION,
    READING,
    EXPLAINING,
    TEST
}

data class LessonActivity(
    val id: String,
    val lessonId: String,
    val name: String,
    val type: ActivityType,
    val duration: String,
    val order: Int,
    val isCompleted: Boolean = false,
    val isUnlocked: Boolean = false,
)

data class Lesson(
    val id: String,
    val moduleId: String,
    val title: String,
    val description: String,
    val isUnlocked: Boolean = false,
    val isCompleted: Boolean = false,
    val activities: List<LessonActivity> = emptyList(),
    val totalActivities: Int = 0,
    val completedActivities: Int = 0,
)
