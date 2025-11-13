package com.example.dialektapp.data.remote

import com.example.dialektapp.data.remote.dto.CourseDto
import com.example.dialektapp.data.remote.dto.LessonDto
import com.example.dialektapp.data.remote.dto.ModuleDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CoursesApi {

    // GET /courses/ - Список усіх курсів з прогресом
    @GET("courses/")
    suspend fun getAllCourses(): Response<List<CourseDto>>

    // GET /courses/me - Мої курси
    @GET("courses/me")
    suspend fun getMyCourses(): Response<List<CourseDto>>

    // GET /courses/me/{course_id} - Деталі мого курсу
    @GET("courses/me/{course_id}")
    suspend fun getMyCourse(@Path("course_id") courseId: Int): Response<CourseDto>

    // GET /courses/{course_id} - Деталі курсу
    @GET("courses/{course_id}")
    suspend fun getCourse(@Path("course_id") courseId: Int): Response<CourseDto>

    // GET /courses/{course_id}/modules/list - Список модулів курсу
    @GET("courses/{course_id}/modules/list")
    suspend fun getCourseModules(@Path("course_id") courseId: Int): Response<List<ModuleDto>>

    // GET /courses/modules/{module_id} - Деталі модуля
    @GET("courses/modules/{module_id}")
    suspend fun getModule(@Path("module_id") moduleId: Int): Response<ModuleDto>

    // GET /courses/modules/{module_id}/lessons/list - Список уроків модуля
    @GET("courses/modules/{module_id}/lessons/list")
    suspend fun getModuleLessons(@Path("module_id") moduleId: Int): Response<List<LessonDto>>

    // GET /courses/lessons/{lesson_id} - Деталі уроку
    @GET("courses/lessons/{lesson_id}")
    suspend fun getLesson(@Path("lesson_id") lessonId: Int): Response<LessonDto>
}
