package com.example.dialektapp.data.mappers

import com.example.dialektapp.data.remote.dto.LeaderboardEntryDto
import com.example.dialektapp.data.remote.dto.LeaderboardResponseDto
import com.example.dialektapp.data.remote.dto.LeaderboardPeriod as DtoPeriod
import com.example.dialektapp.domain.model.LeaderboardData
import com.example.dialektapp.domain.model.LeaderboardEntry
import com.example.dialektapp.domain.model.LeaderboardPeriod as DomainPeriod

fun LeaderboardResponseDto.toDomain(): LeaderboardData {
    return LeaderboardData(
        currentUserEntry = currentUserEntry?.toDomain(),
        topEntries = topEntries.map { it.toDomain() },
        userRankInfo = userRankInfo,
        period = period.toDomainPeriod()
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

private fun DtoPeriod.toDomainPeriod(): DomainPeriod {
    return when (this) {
        DtoPeriod.ALL_TIME -> DomainPeriod.ALL_TIME
        DtoPeriod.WEEKLY -> DomainPeriod.WEEKLY
    }
}
