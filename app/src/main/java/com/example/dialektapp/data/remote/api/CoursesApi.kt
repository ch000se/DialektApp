package com.example.dialektapp.data.remote.api

import com.example.dialektapp.data.remote.dto.CourseDto
import com.example.dialektapp.data.remote.dto.LessonDto
import com.example.dialektapp.data.remote.dto.ModuleDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CoursesApi {
    @GET("/courses/")
    suspend fun getAllCourses(): Response<List<CourseDto>>

    @GET("/courses/me")
    suspend fun getMyCourses(): Response<List<CourseDto>>

    @GET("/courses/me/{course_id}")
    suspend fun getMyCourse(@Path("course_id") courseId: Int): Response<CourseDto>

    @GET("/courses/{course_id}")
    suspend fun getCourse(@Path("course_id") courseId: Int): Response<CourseDto>

    @GET("/courses/{course_id}/modules/list")
    suspend fun getCourseModules(@Path("course_id") courseId: Int): Response<List<ModuleDto>>

    @GET("/courses/modules/{module_id}")
    suspend fun getModule(@Path("module_id") moduleId: Int): Response<ModuleDto>

    @GET("/courses/modules/{module_id}/lessons/list")
    suspend fun getModuleLessons(@Path("module_id") moduleId: Int): Response<List<LessonDto>>

    @GET("/courses/lessons/{lesson_id}")
    suspend fun getLesson(@Path("lesson_id") lessonId: Int): Response<LessonDto>
}
