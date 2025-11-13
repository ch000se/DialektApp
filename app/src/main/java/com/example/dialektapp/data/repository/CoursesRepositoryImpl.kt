package com.example.dialektapp.data.repository

import android.util.Log
import com.example.dialektapp.data.mappers.toDomain
import com.example.dialektapp.data.remote.CoursesApi
import com.example.dialektapp.data.remote.safeCall
import com.example.dialektapp.domain.model.Course
import com.example.dialektapp.domain.model.CourseModule
import com.example.dialektapp.domain.model.Lesson
import com.example.dialektapp.domain.repository.CoursesRepository
import com.example.dialektapp.domain.util.NetworkError
import com.example.dialektapp.domain.util.Result
import com.example.dialektapp.domain.util.map
import javax.inject.Inject

class CoursesRepositoryImpl @Inject constructor(
    private val api: CoursesApi
) : CoursesRepository {

    override suspend fun getAllCourses(): Result<List<Course>, NetworkError> {
        Log.d("CoursesRepo", "API call: GET /courses/")
        return safeCall {
            api.getAllCourses()
        }.map { courses ->
            Log.d("CoursesRepo", "Received ${courses.size} courses from API")
            courses.map { it.toDomain() }
        }
    }

    override suspend fun getMyCourses(): Result<List<Course>, NetworkError> {
        Log.d("CoursesRepo", "API call: GET /courses/me")
        return safeCall {
            api.getMyCourses()
        }.map { courses ->
            Log.d("CoursesRepo", "Received ${courses.size} my courses from API")
            courses.map { it.toDomain() }
        }
    }

    override suspend fun getCourse(courseId: Int): Result<Course, NetworkError> {
        Log.d("CoursesRepo", "API call: GET /courses/$courseId")
        return safeCall {
            api.getCourse(courseId)
        }.map {
            Log.d("CoursesRepo", "Received course: ${it.name}")
            it.toDomain()
        }
    }

    override suspend fun getCourseModules(courseId: Int): Result<List<CourseModule>, NetworkError> {
        Log.d("CoursesRepo", "API call: GET /courses/$courseId/modules/list")
        return safeCall {
            api.getCourseModules(courseId)
        }.map { modules ->
            Log.d("CoursesRepo", "Received ${modules.size} modules")
            modules.map { it.toDomain() }
        }
    }

    override suspend fun getModule(moduleId: Int): Result<CourseModule, NetworkError> {
        Log.d("CoursesRepo", "API call: GET /courses/modules/$moduleId")
        return safeCall {
            api.getModule(moduleId)
        }.map {
            Log.d("CoursesRepo", "Received module: ${it.title}")
            it.toDomain()
        }
    }

    override suspend fun getModuleLessons(moduleId: Int): Result<List<Lesson>, NetworkError> {
        Log.d("CoursesRepo", "API call: GET /courses/modules/$moduleId/lessons/list")
        return safeCall {
            api.getModuleLessons(moduleId)
        }.map { lessons ->
            Log.d("CoursesRepo", "Received ${lessons.size} lessons")
            lessons.map { it.toDomain() }
        }
    }

    override suspend fun getLesson(lessonId: Int): Result<Lesson, NetworkError> {
        Log.d("CoursesRepo", "API call: GET /courses/lessons/$lessonId")
        return safeCall {
            api.getLesson(lessonId)
        }.map {
            Log.d("CoursesRepo", "Received lesson: ${it.title}")
            it.toDomain()
        }
    }
}
