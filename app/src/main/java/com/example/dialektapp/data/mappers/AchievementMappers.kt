package com.example.dialektapp.data.mappers

import com.example.dialektapp.data.remote.dto.AchievementDto
import com.example.dialektapp.domain.model.Achievement
import com.example.dialektapp.domain.model.AchievementCategory
import com.example.dialektapp.domain.model.AchievementRarity

fun AchievementDto.toDomain(): Achievement {
    return Achievement(
        id = id.toString(),
        userId = userId.toString(),
        title = title,
        description = description,
        iconUrl = iconUrl,
        isUnlocked = isUnlocked,
        category = category.toAchievementCategory(),
        rarity = rarity.toAchievementRarity()
    )
}

private fun String.toAchievementCategory(): AchievementCategory {
    return when (this.uppercase()) {
        "GENERAL" -> AchievementCategory.GENERAL
        "LEARNING" -> AchievementCategory.LEARNING
        "SOCIAL" -> AchievementCategory.SOCIAL
        "STREAK" -> AchievementCategory.STREAK
        "SPECIAL" -> AchievementCategory.SPECIAL
        else -> AchievementCategory.GENERAL
    }
}

private fun String.toAchievementRarity(): AchievementRarity {
    return when (this.uppercase()) {
        "COMMON" -> AchievementRarity.COMMON
        "RARE" -> AchievementRarity.RARE
        "EPIC" -> AchievementRarity.EPIC
        "LEGENDARY" -> AchievementRarity.LEGENDARY
        else -> AchievementRarity.COMMON
    }
}
