package com.example.dialektapp.domain.usecases.leaderboard

import com.example.dialektapp.domain.model.LeaderboardData
import com.example.dialektapp.domain.model.LeaderboardPeriod
import com.example.dialektapp.domain.repository.LeaderboardRepository
import com.example.dialektapp.domain.util.NetworkError
import com.example.dialektapp.domain.util.Result
import javax.inject.Inject

class GetLeaderboardUseCase @Inject constructor(
    private val leaderboardRepository: LeaderboardRepository
) {
    suspend operator fun invoke(period: LeaderboardPeriod): Result<LeaderboardData, NetworkError> {
        return leaderboardRepository.getLeaderboard(period)
    }
}
