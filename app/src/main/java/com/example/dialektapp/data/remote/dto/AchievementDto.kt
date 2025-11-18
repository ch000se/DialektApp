package com.example.dialektapp.data.remote.dto

import com.google.gson.annotations.SerializedName

enum class AchievementCategory {
    @SerializedName("GENERAL")
    GENERAL,
    @SerializedName("LEARNING")
    LEARNING,
    @SerializedName("SOCIAL")
    SOCIAL,
    @SerializedName("STREAK")
    STREAK,
    @SerializedName("SPECIAL")
    SPECIAL
}

enum class AchievementRarity {
    @SerializedName("COMMON")
    COMMON,
    @SerializedName("RARE")
    RARE,
    @SerializedName("EPIC")
    EPIC,
    @SerializedName("LEGENDARY")
    LEGENDARY
}

data class AchievementDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("icon_url")
    val iconUrl: String?,
    @SerializedName("is_unlocked")
    val isUnlocked: Boolean,
    @SerializedName("category")
    val category: AchievementCategory,
    @SerializedName("rarity")
    val rarity: AchievementRarity
)
