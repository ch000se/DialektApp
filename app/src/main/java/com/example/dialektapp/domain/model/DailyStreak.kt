package com.example.dialektapp.domain.model

data class DailyStreakData(
    val activeDay: Int,
    val totalDays: Int,
    val isTodayClaimAvailable: Boolean,
    val todayRewardAmount: Int,
)

data class StreakReward(
    val day: Int,
    val amount: Int,
    val isAvailable: Boolean,
)

enum class DayState {
    Completed,
    Active,
    Upcoming
}