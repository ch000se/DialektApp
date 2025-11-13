package com.example.dialektapp.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.dialektapp.domain.model.AchievementRarity

// ==================== GRADIENT BRUSHES ====================

/**
 * Vertical gradient for main background
 */
val backgroundGradient: Brush
    @Composable
    @ReadOnlyComposable
    get() = Brush.verticalGradient(
        colors = listOf(
            BackgroundGradientStart,
            BackgroundGradientMiddle,
            BackgroundGradientEnd
        )
    )

/**
 * Radial gradient for glow effects
 */
fun glowGradient(color: Color, alpha: Float = 0.3f): Brush = Brush.radialGradient(
    colors = listOf(
        color.copy(alpha = alpha),
        Color.Transparent
    )
)

/**
 * Linear gradient for rarity badges
 */
fun rarityGradient(rarity: AchievementRarity): Brush {
    val colors = when (rarity) {
        AchievementRarity.COMMON -> listOf(RarityCommonPrimary, RarityCommonSecondary)
        AchievementRarity.RARE -> listOf(RarityRarePrimary, RarityRareSecondary)
        AchievementRarity.EPIC -> listOf(RarityEpicPrimary, RarityEpicSecondary)
        AchievementRarity.LEGENDARY -> listOf(RarityLegendaryPrimary, RarityLegendarySecondary)
    }
    return Brush.linearGradient(colors)
}

/**
 * Achievement background gradient
 */
fun achievementBackgroundGradient(rarity: AchievementRarity, isUnlocked: Boolean): Brush {
    if (!isUnlocked) {
        return Brush.verticalGradient(
            colors = listOf(StateLockedPrimary, StateLockedSecondary)
        )
    }

    val colors = when (rarity) {
        AchievementRarity.COMMON -> listOf(
            GradientAchievementCommonStart,
            GradientAchievementCommonMiddle,
            GradientAchievementCommonEnd
        )

        AchievementRarity.RARE -> listOf(
            GradientAchievementRareStart,
            GradientAchievementRareMiddle,
            GradientAchievementRareEnd
        )

        AchievementRarity.EPIC -> listOf(
            GradientAchievementEpicStart,
            GradientAchievementEpicMiddle,
            GradientAchievementEpicEnd
        )

        AchievementRarity.LEGENDARY -> listOf(
            GradientAchievementLegendaryStart,
            GradientAchievementLegendaryMiddle,
            GradientAchievementLegendaryEnd
        )
    }
    return Brush.verticalGradient(colors)
}

/**
 * Glass morphism overlay
 */
val glassMorphismOverlay: Brush
    get() = Brush.verticalGradient(
        colors = listOf(
            Color.White.copy(alpha = 0.2f),
            Color.White.copy(alpha = 0.1f),
            Color.Transparent
        )
    )

/**
 * Card background with subtle gradient
 */
val cardGradient: Brush
    @Composable
    @ReadOnlyComposable
    get() = Brush.verticalGradient(
        colors = listOf(
            AppTheme.colors.cardBackground,
            AppTheme.colors.cardBackground.copy(alpha = 0.95f)
        )
    )

// ==================== COLOR UTILITIES ====================

/**
 * Get rarity colors for achievements
 */
data class RarityColors(
    val primary: Color,
    val secondary: Color,
    val border: Color
)

fun getRarityColors(rarity: AchievementRarity): RarityColors {
    return when (rarity) {
        AchievementRarity.COMMON -> RarityColors(
            primary = RarityCommonPrimary,
            secondary = RarityCommonSecondary,
            border = RarityCommonBorder
        )

        AchievementRarity.RARE -> RarityColors(
            primary = RarityRarePrimary,
            secondary = RarityRareSecondary,
            border = RarityRareBorder
        )

        AchievementRarity.EPIC -> RarityColors(
            primary = RarityEpicPrimary,
            secondary = RarityEpicSecondary,
            border = RarityEpicBorder
        )

        AchievementRarity.LEGENDARY -> RarityColors(
            primary = RarityLegendaryPrimary,
            secondary = RarityLegendarySecondary,
            border = RarityLegendaryBorder
        )
    }
}

/**
 * Get rarity name in Ukrainian
 */
fun getRarityName(rarity: AchievementRarity): String {
    return when (rarity) {
        AchievementRarity.COMMON -> "Звичайне"
        AchievementRarity.RARE -> "Рідкісне"
        AchievementRarity.EPIC -> "Епічне"
        AchievementRarity.LEGENDARY -> "Легендарне"
    }
}

// ==================== SHADOW COLORS ====================

/**
 * Get shadow color based on rarity
 */
fun getShadowColor(rarity: AchievementRarity): Color {
    return when (rarity) {
        AchievementRarity.COMMON -> RarityCommonBorder.copy(alpha = 0.3f)
        AchievementRarity.RARE -> RarityRareBorder.copy(alpha = 0.3f)
        AchievementRarity.EPIC -> RarityEpicBorder.copy(alpha = 0.3f)
        AchievementRarity.LEGENDARY -> RarityLegendaryBorder.copy(alpha = 0.3f)
    }
}
