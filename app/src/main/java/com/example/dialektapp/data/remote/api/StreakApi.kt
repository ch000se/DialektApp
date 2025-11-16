package com.example.dialektapp.data.remote.api

import com.example.dialektapp.data.remote.dto.StreakDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface StreakApi {
    @GET("/streak/me")
    suspend fun getMyStreak(): Response<StreakDto>

    @POST("/streak/me/claim")
    suspend fun claimStreak(): Response<StreakDto>
}
