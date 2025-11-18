package com.example.dialektapp.domain.repository

import com.example.dialektapp.domain.model.Achievement
import com.example.dialektapp.domain.util.NetworkError
import com.example.dialektapp.domain.util.Result

interface AchievementsRepository {
    suspend fun getMyAchievements(): Result<List<Achievement>, NetworkError>
    suspend fun unlockAchievement(achievementId: Int): Result<Achievement, NetworkError>
}
