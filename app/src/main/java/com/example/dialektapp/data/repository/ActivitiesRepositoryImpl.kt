package com.example.dialektapp.data.repository

import android.util.Log
import com.example.dialektapp.data.mappers.toDomain
import com.example.dialektapp.data.remote.api.ActivitiesApi
import com.example.dialektapp.data.remote.dto.ActivityProgressRequest
import com.example.dialektapp.data.remote.dto.ActivityStatus
import com.example.dialektapp.data.remote.util.safeCall
import com.example.dialektapp.domain.model.ActivityDetail
import com.example.dialektapp.domain.repository.ActivitiesRepository
import com.example.dialektapp.domain.util.NetworkError
import com.example.dialektapp.domain.util.Result
import com.example.dialektapp.domain.util.map
import javax.inject.Inject

class ActivitiesRepositoryImpl @Inject constructor(
    private val api: ActivitiesApi
) : ActivitiesRepository {

    override suspend fun getActivityDetail(activityId: Int): Result<ActivityDetail, NetworkError> {
        Log.d("ActivitiesRepo", "API call: GET /activities/$activityId/detail")
        return safeCall {
            api.getActivityDetail(activityId)
        }.map {
            Log.d("ActivitiesRepo", "Received activity detail for activity ${it.activityId}")
            it.toDomain()
        }
    }

    override suspend fun updateActivityProgress(
        activityId: Int,
        status: ActivityStatus?,
        isUnlocked: Boolean?,
        score: Int?,
        addAttempt: Boolean?
    ): Result<Unit, NetworkError> {
        Log.d("ActivitiesRepo", "API call: PATCH /courses/activities/$activityId/progress")
        return safeCall {
            api.updateActivityProgress(
                activityId = activityId,
                request = ActivityProgressRequest(
                    status = status,
                    isUnlocked = isUnlocked,
                    score = score,
                    addAttempt = addAttempt
                )
            )
        }.map {
            Log.d("ActivitiesRepo", "Activity progress updated successfully")
        }
    }
}
