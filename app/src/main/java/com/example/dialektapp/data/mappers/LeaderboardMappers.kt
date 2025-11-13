package com.example.dialektapp.data.mappers

import com.example.dialektapp.data.remote.dto.LeaderboardEntryDto
import com.example.dialektapp.data.remote.dto.LeaderboardResponseDto
import com.example.dialektapp.domain.model.LeaderboardData
import com.example.dialektapp.domain.model.LeaderboardEntry
import com.example.dialektapp.domain.model.LeaderboardPeriod

fun LeaderboardResponseDto.toDomain(): LeaderboardData {
    return LeaderboardData(
        currentUserEntry = currentUserEntry?.toDomain(),
        topEntries = topEntries.map { it.toDomain() },
        userRankInfo = userRankInfo,
        period = period.toLeaderboardPeriod()
    )
}

fun LeaderboardEntryDto.toDomain(): LeaderboardEntry {
    return LeaderboardEntry(
        rank = rank,
        userId = userId.toString(),
        fullName = fullName,
        userName = userName,
        coins = coins,
        profileImageUrl = profileImageUrl,
        isCurrentUser = isCurrentUser
    )
}

private fun String.toLeaderboardPeriod(): LeaderboardPeriod {
    return when (this.uppercase()) {
        "ALL_TIME" -> LeaderboardPeriod.ALL_TIME
        "WEEKLY" -> LeaderboardPeriod.WEEKLY
        else -> LeaderboardPeriod.ALL_TIME
    }
}
