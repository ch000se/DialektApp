package com.example.dialektapp.data.remote.api

import com.example.dialektapp.data.remote.dto.ActivityDto
import com.example.dialektapp.data.remote.dto.CourseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CoursesApi {
    /**
     * Отримати всі доступні курси з прогресом користувача
     * Повертає повну структуру: курси → модулі → уроки → активності
     */
    @GET("/courses/")
    suspend fun getAllCourses(): Response<List<CourseDto>>

    /**
     * Отримати курси користувача (на яких він зареєстрований)
     * Повертає повну структуру: курси → модулі → уроки → активності
     */
    @GET("/courses/me")
    suspend fun getMyCourses(): Response<List<CourseDto>>

    /**
     * Отримати конкретний курс користувача з повним деревом даних
     * Використовуйте цей endpoint для детальної інформації про курс
     */
    @GET("/courses/me/{course_id}")
    suspend fun getMyCourse(@Path("course_id") courseId: Int): Response<CourseDto>

    /**
     * Отримати список активностей для конкретного уроку
     * Корисно для lazy loading активностей
     */
    @GET("/courses/lessons/{lesson_id}/activities/list")
    suspend fun getLessonActivities(@Path("lesson_id") lessonId: Int): Response<List<ActivityDto>>
}
