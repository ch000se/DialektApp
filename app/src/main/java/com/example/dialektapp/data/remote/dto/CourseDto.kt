package com.example.dialektapp.data.remote.dto

import com.google.gson.annotations.SerializedName

enum class ActivityType {
    @SerializedName("INTRODUCTION")
    INTRODUCTION,
    @SerializedName("READING")
    READING,
    @SerializedName("EXPLAINING")
    EXPLAINING,
    @SerializedName("TEST")
    TEST
}

data class CourseDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("totalModules")
    val totalModules: Int,
    @SerializedName("completedModules")
    val completedModules: Int,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("image_url_back")
    val imageUrlBack: String,
    @SerializedName("modules")
    val modules: List<ModuleDto>
)

data class ModuleDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("course_id")
    val courseId: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("subtitle")
    val subtitle: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("is_unlocked")
    val isUnlocked: Boolean,
    @SerializedName("is_completed")
    val isCompleted: Boolean,
    @SerializedName("lessons")
    val lessons: List<LessonDto>
)

data class LessonDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("module_id")
    val moduleId: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("is_unlocked")
    val isUnlocked: Boolean,
    @SerializedName("is_completed")
    val isCompleted: Boolean,
    @SerializedName("activities")
    val activities: List<ActivityDto>
)

data class ActivityDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("lesson_id")
    val lessonId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("type")
    val type: ActivityType,
    @SerializedName("duration")
    val duration: String,
    @SerializedName("is_unlocked")
    val isUnlocked: Boolean,
    @SerializedName("is_completed")
    val isCompleted: Boolean
)
