package com.example.dialektapp.domain.usecases.streak

import com.example.dialektapp.domain.model.DailyStreakData
import com.example.dialektapp.domain.repository.StreakRepository
import com.example.dialektapp.domain.util.NetworkError
import com.example.dialektapp.domain.util.Result
import javax.inject.Inject

class ClaimStreakUseCase @Inject constructor(
    private val streakRepository: StreakRepository
) {
    suspend operator fun invoke(): Result<DailyStreakData, NetworkError> {
        return streakRepository.claimStreak()
    }
}
