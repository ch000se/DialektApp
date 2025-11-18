package com.example.dialektapp.domain.repository

import com.example.dialektapp.domain.model.Course
import com.example.dialektapp.domain.model.LessonActivity
import com.example.dialektapp.domain.util.NetworkError
import com.example.dialektapp.domain.util.Result

interface CoursesRepository {
    /**
     * Отримати всі доступні курси
     * Повертає курси з повною структурою (модулі, уроки, активності)
     */
    suspend fun getAllCourses(): Result<List<Course>, NetworkError>

    /**
     * Отримати курси користувача
     * Повертає курси з повною структурою та прогресом
     */
    suspend fun getMyCourses(): Result<List<Course>, NetworkError>

    /**
     * Отримати конкретний курс користувача
     * Повертає курс з повною структурою
     */
    suspend fun getMyCourse(courseId: Int): Result<Course, NetworkError>

    /**
     * Отримати активності для конкретного уроку
     * Корисно для lazy loading або оновлення даних уроку
     */
    suspend fun getLessonActivities(lessonId: Int): Result<List<LessonActivity>, NetworkError>

    /**
     * Оновити активності уроку (легший запит ніж перезавантаження курсу)
     */
    suspend fun refreshLessonActivities(lessonId: Int): Result<List<LessonActivity>, NetworkError>
}
