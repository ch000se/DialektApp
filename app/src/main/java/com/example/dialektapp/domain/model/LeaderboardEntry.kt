package com.example.dialektapp.domain.model

data class LeaderboardEntry(
    val rank: Int,
    val userId: String,
    val fullName: String,
    val userName: String,
    val coins: Int,
    val profileImageUrl: String? = null,
    val isCurrentUser: Boolean = false,
)

enum class LeaderboardPeriod {
    ALL_TIME,
    WEEKLY
}

data class LeaderboardData(
    val currentUserEntry: LeaderboardEntry? = null,
    val topEntries: List<LeaderboardEntry> = emptyList(),
    val userRankInfo: String = "",
    val period: LeaderboardPeriod = LeaderboardPeriod.ALL_TIME,
)