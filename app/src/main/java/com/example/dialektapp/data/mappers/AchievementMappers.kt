package com.example.dialektapp.data.mappers

import com.example.dialektapp.data.remote.dto.AchievementDto
import com.example.dialektapp.domain.model.Achievement
import com.example.dialektapp.domain.model.AchievementCategory as DomainCategory
import com.example.dialektapp.domain.model.AchievementRarity as DomainRarity
import com.example.dialektapp.data.remote.dto.AchievementCategory as DtoCategory
import com.example.dialektapp.data.remote.dto.AchievementRarity as DtoRarity

fun AchievementDto.toDomain(): Achievement {
    return Achievement(
        id = id.toString(),
        userId = userId.toString(),
        title = title,
        description = description,
        iconUrl = iconUrl,
        isUnlocked = isUnlocked,
        category = category.toDomainCategory(),
        rarity = rarity.toDomainRarity()
    )
}

private fun DtoCategory.toDomainCategory(): DomainCategory {
    return when (this) {
        DtoCategory.GENERAL -> DomainCategory.GENERAL
        DtoCategory.LEARNING -> DomainCategory.LEARNING
        DtoCategory.SOCIAL -> DomainCategory.SOCIAL
        DtoCategory.STREAK -> DomainCategory.STREAK
        DtoCategory.SPECIAL -> DomainCategory.SPECIAL
    }
}

private fun DtoRarity.toDomainRarity(): DomainRarity {
    return when (this) {
        DtoRarity.COMMON -> DomainRarity.COMMON
        DtoRarity.RARE -> DomainRarity.RARE
        DtoRarity.EPIC -> DomainRarity.EPIC
        DtoRarity.LEGENDARY -> DomainRarity.LEGENDARY
    }
}
