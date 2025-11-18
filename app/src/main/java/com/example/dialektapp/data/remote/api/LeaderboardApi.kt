package com.example.dialektapp.data.remote.api

import com.example.dialektapp.data.remote.dto.LeaderboardResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LeaderboardApi {
    @GET("/leaderboard/")
    suspend fun getLeaderboard(@Query("period") period: String): Response<LeaderboardResponseDto>
}
