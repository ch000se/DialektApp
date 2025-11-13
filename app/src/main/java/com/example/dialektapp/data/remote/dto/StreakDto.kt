package com.example.dialektapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class StreakDto(
    @SerializedName("activeDay")
    val activeDay: Int,
    @SerializedName("totalDays")
    val totalDays: Int,
    @SerializedName("isTodayClaimAvailable")
    val isTodayClaimAvailable: Boolean,
    @SerializedName("todayRewardAmount")
    val todayRewardAmount: Int
)
