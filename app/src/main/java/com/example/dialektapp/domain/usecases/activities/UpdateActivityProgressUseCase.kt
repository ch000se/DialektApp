package com.example.dialektapp.domain.usecases.activities

import com.example.dialektapp.data.remote.dto.ActivityStatus
import com.example.dialektapp.domain.repository.ActivitiesRepository
import com.example.dialektapp.domain.util.NetworkError
import com.example.dialektapp.domain.util.Result
import javax.inject.Inject

class UpdateActivityProgressUseCase @Inject constructor(
    private val activitiesRepository: ActivitiesRepository
) {
    suspend operator fun invoke(
        activityId: Int,
        status: ActivityStatus? = null,
        isUnlocked: Boolean? = null,
        score: Int? = null,
        addAttempt: Boolean? = null
    ): Result<Unit, NetworkError> {
        return activitiesRepository.updateActivityProgress(
            activityId,
            status,
            isUnlocked,
            score,
            addAttempt
        )
    }
}
