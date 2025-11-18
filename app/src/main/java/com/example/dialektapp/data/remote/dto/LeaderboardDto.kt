package com.example.dialektapp.data.remote.dto

import com.google.gson.annotations.SerializedName

enum class LeaderboardPeriod {
    @SerializedName("ALL_TIME")
    ALL_TIME,
    @SerializedName("WEEKLY")
    WEEKLY
}

data class LeaderboardResponseDto(
    @SerializedName("currentUserEntry")
    val currentUserEntry: LeaderboardEntryDto?,
    @SerializedName("topEntries")
    val topEntries: List<LeaderboardEntryDto>,
    @SerializedName("userRankInfo")
    val userRankInfo: String,
    @SerializedName("period")
    val period: LeaderboardPeriod
)

data class LeaderboardEntryDto(
    @SerializedName("rank")
    val rank: Int,
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("fullName")
    val fullName: String,
    @SerializedName("userName")
    val userName: String,
    @SerializedName("coins")
    val coins: Int,
    @SerializedName("profileImageUrl")
    val profileImageUrl: String?,
    @SerializedName("isCurrentUser")
    val isCurrentUser: Boolean = false
)
