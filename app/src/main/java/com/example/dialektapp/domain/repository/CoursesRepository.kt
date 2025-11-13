package com.example.dialektapp.domain.repository

import com.example.dialektapp.domain.model.Course
import com.example.dialektapp.domain.model.CourseModule
import com.example.dialektapp.domain.model.Lesson
import com.example.dialektapp.domain.util.NetworkError
import com.example.dialektapp.domain.util.Result

interface CoursesRepository {
    suspend fun getAllCourses(): Result<List<Course>, NetworkError>
    suspend fun getMyCourses(): Result<List<Course>, NetworkError>
    suspend fun getCourse(courseId: Int): Result<Course, NetworkError>
    suspend fun getCourseModules(courseId: Int): Result<List<CourseModule>, NetworkError>
    suspend fun getModule(moduleId: Int): Result<CourseModule, NetworkError>
    suspend fun getModuleLessons(moduleId: Int): Result<List<Lesson>, NetworkError>
    suspend fun getLesson(lessonId: Int): Result<Lesson, NetworkError>
}
