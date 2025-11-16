package com.example.dialektapp.data.remote.api

import com.example.dialektapp.data.remote.dto.AchievementDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AchievementsApi {
    @GET("/achievements/me")
    suspend fun getMyAchievements(): Response<List<AchievementDto>>

    @GET("/achievements/{achievement_id}")
    suspend fun getAchievement(@Path("achievement_id") achievementId: Int): Response<AchievementDto>

    @POST("/achievements/{achievement_id}/unlock")
    suspend fun unlockAchievement(@Path("achievement_id") achievementId: Int): Response<AchievementDto>
}
