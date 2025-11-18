package com.example.dialektapp.domain.usecases.courses

import com.example.dialektapp.domain.model.Course
import com.example.dialektapp.domain.repository.CoursesRepository
import com.example.dialektapp.domain.util.NetworkError
import com.example.dialektapp.domain.util.Result
import javax.inject.Inject

class GetAllCoursesUseCase @Inject constructor(
    private val repository: CoursesRepository
) {
    suspend operator fun invoke(): Result<List<Course>, NetworkError> {
        return repository.getAllCourses()
    }
}
