package com.example.dialektapp.presentation.screens.home.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Rocket
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dialektapp.R
import com.example.dialektapp.domain.model.DayState
import com.example.dialektapp.ui.theme.*

private val TileSize = 56.dp
private val TileCornerRadius = 14.dp
private val InnerPadding = 12.dp

@Composable
fun DayStreakItem(
    dayNumber: Int,
    dayState: DayState,
    hasReward: Boolean = false,
    onRewardClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val infiniteTransition = rememberInfiniteTransition(label = "activeDayPulse")
    val activePulse by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = when {
            hasReward -> 1.2f
            dayState == DayState.Active -> 1.15f
            else -> 1f
        },
        animationSpec = infiniteRepeatable(
            animation = tween(if (hasReward) 800 else 1000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "activePulse"
    )

    val cardColor by animateColorAsState(
        targetValue = when {
            hasReward -> AccentGold
            dayState == DayState.Completed -> AccentPrimaryLight  // Світліший жовтий для завершених
            dayState == DayState.Active -> AccentPrimary          // Яскравий жовтий для активного
            else -> StreakUpcomingTileColor
        },
        animationSpec = tween(500),
        label = "cardColor"
    )

    val elevation by animateDpAsState(
        targetValue = when {
            hasReward -> 12.dp
            dayState == DayState.Active -> 8.dp
            dayState == DayState.Completed -> 4.dp
            else -> 0.dp
        },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "elevation"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Card(
            modifier = Modifier
                .size(TileSize)
                .graphicsLayer {
                    scaleX = if (hasReward) activePulse else 1f
                    scaleY = if (hasReward) activePulse else 1f
                    rotationZ = if (hasReward) {
                        // Більше покачування для нагороди
                        (activePulse - 1f) * 10f
                    } else 0f
                }
                .clickable(enabled = hasReward) {
                    onRewardClick()
                },
            shape = RoundedCornerShape(TileCornerRadius),
            colors = CardDefaults.cardColors(containerColor = cardColor),
            elevation = CardDefaults.cardElevation(defaultElevation = elevation),
            border = if (hasReward) {
                BorderStroke(
                    2.dp,
                    AccentGold
                )
            } else null
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(InnerPadding),
                contentAlignment = Alignment.Center
            ) {
                when (dayState) {
                    DayState.Completed -> {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = "Completed",
                            tint = TextPrimaryDark,
                            modifier = Modifier.size(20.dp),
                        )
                    }

                    DayState.Active -> {
                        Icon(
                            imageVector = if (hasReward) {
                                ImageVector.vectorResource(R.drawable.coins)
                            } else {
                                ImageVector.vectorResource(R.drawable.rocket_launch)
                            },
                            contentDescription = if (hasReward) "Reward" else "Active",
                            tint = TextPrimaryDark,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    DayState.Upcoming -> {
                        Icon(
                            imageVector = Icons.Default.Rocket,
                            contentDescription = "Upcoming",
                            tint = TextPrimaryDark.copy(alpha = 0.8f),
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        val textColor by animateColorAsState(
            targetValue = when {
                hasReward -> AccentGold
                dayState == DayState.Completed -> TextPrimaryDark.copy(alpha = 0.7f)
                dayState == DayState.Active -> TextPrimaryDark
                else -> TextPrimaryDark.copy(alpha = 0.5f)
            },
            animationSpec = tween(500),
            label = "textColor"
        )

        Text(
            text = "День $dayNumber",
            style = MaterialTheme.typography.labelMedium,
            letterSpacing = 0.1.sp,
            color = textColor,
            fontWeight = if (hasReward || dayState == DayState.Active) FontWeight.Bold else FontWeight.Normal,
            modifier = Modifier.graphicsLayer {
                scaleX = if (hasReward) activePulse * 0.9f else 1f
                scaleY = if (hasReward) activePulse * 0.9f else 1f
            }
        )
    }
}

@Preview
@Composable
private fun DayStreakItemPreview() {
    DayStreakItem(
        dayNumber = 5,
        dayState = DayState.Active,
        hasReward = true
    )
}