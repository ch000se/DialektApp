package com.example.dialektapp.domain.usecases.courses

import com.example.dialektapp.domain.model.Lesson
import com.example.dialektapp.domain.repository.CoursesRepository
import com.example.dialektapp.domain.util.NetworkError
import com.example.dialektapp.domain.util.Result
import javax.inject.Inject

class GetLessonUseCase @Inject constructor(
    private val coursesRepository: CoursesRepository
) {
    suspend operator fun invoke(lessonId: Int): Result<Lesson, NetworkError> {
        return coursesRepository.getLesson(lessonId)
    }
}
