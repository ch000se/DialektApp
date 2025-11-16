package com.example.dialektapp.domain.usecases.achievements

import com.example.dialektapp.domain.model.Achievement
import com.example.dialektapp.domain.repository.AchievementsRepository
import com.example.dialektapp.domain.util.NetworkError
import com.example.dialektapp.domain.util.Result
import javax.inject.Inject

class UnlockAchievementUseCase @Inject constructor(
    private val achievementsRepository: AchievementsRepository
) {
    suspend operator fun invoke(achievementId: Int): Result<Achievement, NetworkError> {
        return achievementsRepository.unlockAchievement(achievementId)
    }
}
