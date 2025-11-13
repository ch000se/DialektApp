package com.example.dialektapp.data.remote

import com.example.dialektapp.data.remote.dto.StreakDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface StreakApi {

    // GET /streak/me - Отримати поточний streak (backend uses /streak not /streaks)
    @GET("/streak/me")
    suspend fun getMyStreak(): Response<StreakDto>

    // POST /streak/me/claim - Забрати нагороду за день
    @POST("/streak/me/claim")
    suspend fun claimStreak(): Response<StreakDto>
}
