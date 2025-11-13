package com.example.dialektapp.domain.model

data class Achievement(
    val id: String,
    val userId: String,
    val title: String,
    val description: String,
    val iconUrl: String? = null,
    val isUnlocked: Boolean = false,
    val category: AchievementCategory = AchievementCategory.GENERAL,
    val rarity: AchievementRarity = AchievementRarity.COMMON,
)

enum class AchievementCategory {
    GENERAL,
    LEARNING,
    SOCIAL,
    STREAK,
    SPECIAL
}

enum class AchievementRarity {
    COMMON,
    RARE,
    EPIC,
    LEGENDARY
}