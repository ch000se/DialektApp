package com.example.dialektapp.domain.repository

import com.example.dialektapp.data.remote.dto.ActivityStatus
import com.example.dialektapp.domain.model.ActivityDetail
import com.example.dialektapp.domain.util.NetworkError
import com.example.dialektapp.domain.util.Result

interface ActivitiesRepository {
    suspend fun getActivityDetail(activityId: Int): Result<ActivityDetail, NetworkError>
    suspend fun updateActivityProgress(
        activityId: Int,
        status: ActivityStatus? = null,
        isUnlocked: Boolean? = null,
        score: Int? = null,
        addAttempt: Boolean? = null
    ): Result<Unit, NetworkError>
}
