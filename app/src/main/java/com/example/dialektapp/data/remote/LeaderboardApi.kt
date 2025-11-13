package com.example.dialektapp.data.remote

import com.example.dialektapp.data.remote.dto.LeaderboardResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LeaderboardApi {

    // GET /leaderboard/ - Отримати таблицю лідерів
    @GET("/leaderboard/")
    suspend fun getLeaderboard(
        @Query("period") period: String = "ALL_TIME" // "ALL_TIME" or "WEEKLY"
    ): Response<LeaderboardResponseDto>
}
