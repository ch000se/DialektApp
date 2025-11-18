package com.example.dialektapp.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Routes {
    @Serializable
    data object Auth : Routes()
    @Serializable
    data object Login : Routes()

    @Serializable
    data object SignUp : Routes()

    @Serializable
    data object ForgotPassword : Routes()

    @Serializable
    data object Home : Routes()

    @Serializable
    data object Profile : Routes()

    @Serializable
    data object Leaderboard : Routes()

    @Serializable
    data object Achievements : Routes()

    @Serializable
    data class CourseDetail(
        val courseId: String,
        val courseName: String,
    ) : Routes()

    @Serializable
    data class ModuleLessons(
        val courseId: String,
        val moduleId: String,
        val moduleTitle: String,
    ) : Routes()

    @Serializable
    data class Activity(
        val courseId: String,
        val lessonId: String,
        val activityId: String,
    ) : Routes()
}