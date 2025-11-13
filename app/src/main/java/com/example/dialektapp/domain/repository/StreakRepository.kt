package com.example.dialektapp.domain.repository

import com.example.dialektapp.domain.model.DailyStreakData
import com.example.dialektapp.domain.util.NetworkError
import com.example.dialektapp.domain.util.Result

interface StreakRepository {
    suspend fun getMyStreak(): Result<DailyStreakData, NetworkError>
    suspend fun claimStreak(): Result<DailyStreakData, NetworkError>
}
