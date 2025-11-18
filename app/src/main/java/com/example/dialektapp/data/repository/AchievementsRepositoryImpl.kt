package com.example.dialektapp.data.repository

import android.util.Log
import com.example.dialektapp.data.mappers.toDomain
import com.example.dialektapp.data.remote.api.AchievementsApi
import com.example.dialektapp.data.remote.util.safeCall
import com.example.dialektapp.domain.model.Achievement
import com.example.dialektapp.domain.repository.AchievementsRepository
import com.example.dialektapp.domain.util.NetworkError
import com.example.dialektapp.domain.util.Result
import com.example.dialektapp.domain.util.map
import javax.inject.Inject

class AchievementsRepositoryImpl @Inject constructor(
    private val api: AchievementsApi
) : AchievementsRepository {

    override suspend fun getMyAchievements(): Result<List<Achievement>, NetworkError> {
        Log.d("AchievementsRepo", "API call: GET /achievements/me")
        return safeCall {
            api.getMyAchievements()
        }.map { achievements ->
            Log.d("AchievementsRepo", "Received ${achievements.size} achievements")
            achievements.map { it.toDomain() }
        }
    }

    override suspend fun unlockAchievement(achievementId: Int): Result<Achievement, NetworkError> {
        Log.d("AchievementsRepo", "API call: POST /achievements/$achievementId/unlock")
        return safeCall {
            api.unlockAchievement(achievementId)
        }.map {
            Log.d("AchievementsRepo", "Achievement unlocked: ${it.title}")
            it.toDomain()
        }
    }
}
