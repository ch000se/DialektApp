package com.example.dialektapp.data.mappers

import com.example.dialektapp.data.remote.dto.StreakDto
import com.example.dialektapp.domain.model.DailyStreakData

fun StreakDto.toDomain(): DailyStreakData {
    return DailyStreakData(
        activeDay = activeDay,
        totalDays = totalDays,
        isTodayClaimAvailable = isTodayClaimAvailable,
        todayRewardAmount = todayRewardAmount
    )
}
