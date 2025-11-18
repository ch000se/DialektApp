package com.example.dialektapp.data.remote.api

import com.example.dialektapp.data.remote.dto.ActivityDetailDto
import com.example.dialektapp.data.remote.dto.ActivityProgressRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface ActivitiesApi {
    @GET("/activities/{activity_id}/detail")
    suspend fun getActivityDetail(@Path("activity_id") activityId: Int): Response<ActivityDetailDto>

    @PATCH("/courses/activities/{activity_id}/progress")
    suspend fun updateActivityProgress(
        @Path("activity_id") activityId: Int,
        @Body request: ActivityProgressRequest
    ): Response<Unit>
}
