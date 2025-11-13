package com.example.dialektapp.data.repository

import android.util.Log
import com.example.dialektapp.data.mappers.toDomain
import com.example.dialektapp.data.remote.StreakApi
import com.example.dialektapp.data.remote.safeCall
import com.example.dialektapp.domain.model.DailyStreakData
import com.example.dialektapp.domain.repository.StreakRepository
import com.example.dialektapp.domain.util.NetworkError
import com.example.dialektapp.domain.util.Result
import com.example.dialektapp.domain.util.map
import javax.inject.Inject

class StreakRepositoryImpl @Inject constructor(
    private val api: StreakApi
) : StreakRepository {

    override suspend fun getMyStreak(): Result<DailyStreakData, NetworkError> {
        Log.d("StreakRepo", "API call: GET /streak/me")
        return safeCall {
            api.getMyStreak()
        }.map {
            Log.d(
                "StreakRepo",
                "Received streak: day ${it.activeDay}/${it.totalDays}, claimable: ${it.isTodayClaimAvailable}"
            )
            it.toDomain()
        }
    }

    override suspend fun claimStreak(): Result<DailyStreakData, NetworkError> {
        Log.d("StreakRepo", "API call: POST /streak/me/claim")
        return safeCall {
            api.claimStreak()
        }.map {
            Log.d(
                "StreakRepo",
                "Streak claimed successfully, reward: ${it.todayRewardAmount} coins"
            )
            it.toDomain()
        }
    }
}
