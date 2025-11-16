package com.example.dialektapp.domain.usecases.courses

import com.example.dialektapp.domain.model.CourseModule
import com.example.dialektapp.domain.repository.CoursesRepository
import com.example.dialektapp.domain.util.NetworkError
import com.example.dialektapp.domain.util.Result
import javax.inject.Inject

class GetCourseModulesUseCase @Inject constructor(
    private val repository: CoursesRepository
) {
    suspend operator fun invoke(courseId: Int): Result<List<CourseModule>, NetworkError> {
        return repository.getCourseModules(courseId)
    }
}
