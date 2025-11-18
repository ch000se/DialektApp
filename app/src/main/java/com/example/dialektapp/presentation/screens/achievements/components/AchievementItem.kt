package com.example.dialektapp.presentation.screens.achievements.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dialektapp.domain.model.Achievement
import com.example.dialektapp.domain.model.AchievementRarity
import com.example.dialektapp.ui.theme.*

@Composable
fun AchievementItem(
    achievement: Achievement,
    onClick: (Achievement) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = getAchievementColors(achievement.rarity, achievement.isUnlocked)
    var isPressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )

    Card(
        modifier = modifier
            .scale(scale)
            .clickable {
                isPressed = true
                onClick(achievement)
            },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.15f)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (achievement.isUnlocked) 8.dp else 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Achievement icon with glow effect
            Box(
                modifier = Modifier.size(90.dp),
                contentAlignment = Alignment.Center
            ) {
                // Glow effect for unlocked achievements
                if (achievement.isUnlocked) {
                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .background(
                                brush = Brush.radialGradient(
                                    colors = listOf(
                                        colors.borderColor.copy(alpha = 0.3f),
                                        Color.Transparent
                                    )
                                ),
                                shape = CircleShape
                            )
                    )
                }

                // Icon circle
                Box(
                    modifier = Modifier
                        .size(75.dp)
                        .background(
                            brush = Brush.radialGradient(
                                colors = colors.backgroundColors
                            ),
                            shape = CircleShape
                        )
                        .border(
                            width = 3.dp,
                            color = colors.borderColor,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (achievement.isUnlocked) {
                        Text(
                            text = achievement.iconUrl ?: "🏆",
                            fontSize = 36.sp
                        )
                    } else {
                        Text(
                            text = "🔒",
                            fontSize = 32.sp,
                            color = Color.Gray.copy(alpha = 0.6f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = achievement.title,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = TextWhite,
                textAlign = TextAlign.Center,
                maxLines = 2,
                lineHeight = 16.sp
            )

            if (achievement.isUnlocked) {
                Spacer(modifier = Modifier.height(4.dp))

                RarityBadge(rarity = achievement.rarity)
            }
        }
    }

    LaunchedEffect(isPressed) {
        if (isPressed) {
            kotlinx.coroutines.delay(100)
            isPressed = false
        }
    }
}

@Composable
private fun RarityBadge(rarity: AchievementRarity) {
    val (text, color) = when (rarity) {
        AchievementRarity.COMMON -> "Звичайне" to RarityCommonPrimary
        AchievementRarity.RARE -> "Рідкісне" to RarityRarePrimary
        AchievementRarity.EPIC -> "Епічне" to RarityEpicPrimary
        AchievementRarity.LEGENDARY -> "Легендарне" to RarityLegendaryPrimary
    }

    Surface(
        color = color.copy(alpha = 0.9f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = text,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = TextWhite,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
        )
    }
}

@Composable
private fun getAchievementColors(
    rarity: AchievementRarity,
    isUnlocked: Boolean,
): AchievementColors {
    return when {
        !isUnlocked -> AchievementColors(
            backgroundColors = listOf(
                StateLockedPrimary,
                StateLockedSecondary
            ),
            borderColor = StateLockedBorder
        )

        rarity == AchievementRarity.COMMON -> AchievementColors(
            backgroundColors = listOf(
                RarityCommonPrimary,
                RarityCommonSecondary
            ),
            borderColor = RarityCommonBorder
        )

        rarity == AchievementRarity.RARE -> AchievementColors(
            backgroundColors = listOf(
                RarityRarePrimary,
                RarityRareSecondary
            ),
            borderColor = RarityRareBorder
        )

        rarity == AchievementRarity.EPIC -> AchievementColors(
            backgroundColors = listOf(
                RarityEpicPrimary,
                RarityEpicSecondary
            ),
            borderColor = RarityEpicBorder
        )

        rarity == AchievementRarity.LEGENDARY -> AchievementColors(
            backgroundColors = listOf(
                RarityLegendaryPrimary,
                RarityLegendarySecondary
            ),
            borderColor = RarityLegendaryBorder
        )

        else -> AchievementColors(
            backgroundColors = listOf(
                RarityCommonPrimary,
                RarityCommonSecondary
            ),
            borderColor = RarityCommonBorder
        )
    }
}

private data class AchievementColors(
    val backgroundColors: List<Color>,
    val borderColor: Color,
)