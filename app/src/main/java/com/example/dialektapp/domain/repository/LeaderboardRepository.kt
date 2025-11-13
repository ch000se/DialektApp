package com.example.dialektapp.domain.repository

import com.example.dialektapp.domain.model.LeaderboardData
import com.example.dialektapp.domain.model.LeaderboardPeriod
import com.example.dialektapp.domain.util.NetworkError
import com.example.dialektapp.domain.util.Result

interface LeaderboardRepository {
    suspend fun getLeaderboard(period: LeaderboardPeriod): Result<LeaderboardData, NetworkError>
}
