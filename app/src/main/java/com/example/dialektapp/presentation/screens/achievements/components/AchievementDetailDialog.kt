package com.example.dialektapp.presentation.screens.achievements.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.dialektapp.domain.model.Achievement
import com.example.dialektapp.domain.model.AchievementRarity
import com.example.dialektapp.ui.theme.*

@Composable
fun AchievementDetailDialog(
    achievement: Achievement,
    onDismiss: () -> Unit,
    onActionClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    val infiniteTransition = rememberInfiniteTransition(label = "glow")
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow"
    )

    val scale by animateFloatAsState(
        targetValue = if (achievement.isUnlocked) 1f else 0.95f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )

    val gradientColors = getDialogGradient(achievement.rarity, achievement.isUnlocked)

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = gradientColors
                    )
                )
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .statusBarsPadding(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FilledIconButton(
                        onClick = onDismiss,
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = Color.White.copy(alpha = 0.25f)
                        )
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад",
                            tint = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = "Деталі досягнення",
                        color = TextWhite,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(40.dp))

                    Box(
                        modifier = Modifier.size(180.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        // Animated glow for unlocked achievements
                        if (achievement.isUnlocked) {
                            Box(
                                modifier = Modifier
                                    .size(180.dp)
                                    .scale(1.2f)
                                    .background(
                                        brush = Brush.radialGradient(
                                            colors = listOf(
                                                Color.White.copy(alpha = glowAlpha),
                                                Color.Transparent
                                            )
                                        ),
                                        shape = CircleShape
                                    )
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(160.dp)
                                .scale(scale)
                                .background(
                                    brush = Brush.radialGradient(
                                        colors = if (achievement.isUnlocked) {
                                            listOf(
                                                Color.White,
                                                Color(0xFFF5F5F5)
                                            )
                                        } else {
                                            listOf(
                                                Color(0xFF616161),
                                                Color(0xFF757575)
                                            )
                                        }
                                    ),
                                    shape = CircleShape
                                )
                                .border(
                                    width = 6.dp,
                                    brush = if (achievement.isUnlocked) {
                                        Brush.linearGradient(
                                            colors = getRarityColors(achievement.rarity)
                                        )
                                    } else {
                                        Brush.linearGradient(
                                            colors = listOf(
                                                StateLockedPrimary,
                                                StateLockedSecondary
                                            )
                                        )
                                    },
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (achievement.isUnlocked) {
                                    achievement.iconUrl ?: "🏆"
                                } else {
                                    "🔒"
                                },
                                fontSize = 72.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    if (achievement.isUnlocked) {
                        RarityChip(rarity = achievement.rarity)
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    Text(
                        text = achievement.title,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White.copy(alpha = 0.2f)
                        ),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text(
                            text = achievement.description,
                            fontSize = 16.sp,
                            color = TextWhite,
                            textAlign = TextAlign.Center,
                            lineHeight = 24.sp,
                            modifier = Modifier.padding(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    if (achievement.isUnlocked && onActionClick != null) {
                        Button(
                            onClick = onActionClick,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(64.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AccentPrimary
                            ),
                            shape = RoundedCornerShape(32.dp),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 8.dp,
                                pressedElevation = 12.dp
                            )
                        ) {
                            Text(
                                text = "ПРОДОВЖИТИ",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}

@Composable
private fun RarityChip(rarity: AchievementRarity) {
    val (text, colors) = when (rarity) {
        AchievementRarity.COMMON -> "ЗВИЧАЙНЕ" to listOf(RarityCommonPrimary, RarityCommonSecondary)
        AchievementRarity.RARE -> "РІДКІСНЕ" to listOf(RarityRarePrimary, RarityRareSecondary)
        AchievementRarity.EPIC -> "ЕПІЧНЕ" to listOf(RarityEpicPrimary, RarityEpicSecondary)
        AchievementRarity.LEGENDARY -> "ЛЕГЕНДАРНЕ" to listOf(RarityLegendaryPrimary, RarityLegendarySecondary)
    }

    Surface(
        color = Color.Transparent,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.border(
            width = 2.dp,
            brush = Brush.linearGradient(colors),
            shape = RoundedCornerShape(16.dp)
        )
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        colors = colors.map { it.copy(alpha = 0.3f) }
                    )
                )
                .padding(horizontal = 20.dp, vertical = 8.dp)
        ) {
            Text(
                text = text,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextWhite
            )
        }
    }
}

private fun getDialogGradient(rarity: AchievementRarity, isUnlocked: Boolean): List<Color> {
    if (!isUnlocked) {
        return listOf(
            StateLockedPrimary,
            StateLockedSecondary,
            StateLockedBorder
        )
    }

    return when (rarity) {
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
}

private fun getRarityColors(rarity: AchievementRarity): List<Color> {
    return when (rarity) {
        AchievementRarity.COMMON -> listOf(RarityCommonPrimary, RarityCommonSecondary)
        AchievementRarity.RARE -> listOf(RarityRarePrimary, RarityRareSecondary)
        AchievementRarity.EPIC -> listOf(RarityEpicPrimary, RarityEpicSecondary)
        AchievementRarity.LEGENDARY -> listOf(RarityLegendaryPrimary, RarityLegendarySecondary)
    }
}