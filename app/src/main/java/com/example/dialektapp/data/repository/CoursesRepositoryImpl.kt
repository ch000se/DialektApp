package com.example.dialektapp.data.repository

import android.util.Log
import com.example.dialektapp.data.mappers.toDomain
import com.example.dialektapp.data.remote.api.CoursesApi
import com.example.dialektapp.data.remote.util.safeCall
import com.example.dialektapp.domain.model.Course
import com.example.dialektapp.domain.model.LessonActivity
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

    override suspend fun getMyCourse(courseId: Int): Result<Course, NetworkError> {
        Log.d("CoursesRepo", "API call: GET /courses/me/$courseId")
        return safeCall {
            api.getMyCourse(courseId)
        }.map {
            Log.d("CoursesRepo", "Received course: ${it.name}")
            it.toDomain()
        }
    }

    override suspend fun getLessonActivities(lessonId: Int): Result<List<LessonActivity>, NetworkError> {
        Log.d("CoursesRepo", "API call: GET /courses/lessons/$lessonId/activities/list")
        return safeCall {
            api.getLessonActivities(lessonId)
        }.map { activities ->
            Log.d("CoursesRepo", "Received ${activities.size} activities for lesson $lessonId")
            activities.map { it.toDomain() }
        }
    }

    override suspend fun refreshLessonActivities(lessonId: Int): Result<List<LessonActivity>, NetworkError> {
        return getLessonActivities(lessonId)
    }
}
