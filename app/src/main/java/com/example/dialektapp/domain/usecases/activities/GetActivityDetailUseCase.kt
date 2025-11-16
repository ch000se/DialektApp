package com.example.dialektapp.domain.usecases.activities

import com.example.dialektapp.domain.model.ActivityDetail
import com.example.dialektapp.domain.repository.ActivitiesRepository
import com.example.dialektapp.domain.util.NetworkError
import com.example.dialektapp.domain.util.Result
import javax.inject.Inject

class GetActivityDetailUseCase @Inject constructor(
    private val activitiesRepository: ActivitiesRepository
) {
    suspend operator fun invoke(activityId: Int): Result<ActivityDetail, NetworkError> {
        return activitiesRepository.getActivityDetail(activityId)
    }
}
