package com.example.dialektapp.data.remote.dto

import com.google.gson.annotations.SerializedName

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
    val category: String, // "GENERAL", "LEARNING", "SOCIAL", "STREAK", "SPECIAL"
    @SerializedName("rarity")
    val rarity: String // "COMMON", "RARE", "EPIC", "LEGENDARY"
)
