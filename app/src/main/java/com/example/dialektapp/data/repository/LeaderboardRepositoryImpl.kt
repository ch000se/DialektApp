package com.example.dialektapp.data.repository

import android.util.Log
import com.example.dialektapp.data.mappers.toDomain
import com.example.dialektapp.data.remote.api.LeaderboardApi
import com.example.dialektapp.data.remote.util.safeCall
import com.example.dialektapp.domain.model.LeaderboardData
import com.example.dialektapp.domain.model.LeaderboardPeriod
import com.example.dialektapp.domain.repository.LeaderboardRepository
import com.example.dialektapp.domain.util.NetworkError
import com.example.dialektapp.domain.util.Result
import com.example.dialektapp.domain.util.map
import javax.inject.Inject

class LeaderboardRepositoryImpl @Inject constructor(
    private val api: LeaderboardApi
) : LeaderboardRepository {

    override suspend fun getLeaderboard(period: LeaderboardPeriod): Result<LeaderboardData, NetworkError> {
        val periodParam = when (period) {
            LeaderboardPeriod.ALL_TIME -> "ALL_TIME"
            LeaderboardPeriod.WEEKLY -> "WEEKLY"
        }

        Log.d("LeaderboardRepo", "API call: GET /leaderboard/?period=$periodParam")
        return safeCall {
            api.getLeaderboard(periodParam)
        }.map {
            Log.d("LeaderboardRepo", "Received leaderboard: ${it.topEntries.size} entries")
            it.toDomain()
        }
    }
}
