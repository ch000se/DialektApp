package com.example.dialektapp.presentation.screens.home.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dialektapp.R
import com.example.dialektapp.domain.model.DailyStreakData
import com.example.dialektapp.domain.model.DayState
import com.example.dialektapp.ui.theme.*

private val TileSpacing = 12.dp

@Composable
fun DailyStreakSection(
) {
    // Тимчасові mock дані
    val mockStreakData = remember {
        DailyStreakData(
            activeDay = 3,
            totalDays = 7,
            isTodayClaimAvailable = true,
            todayRewardAmount = 50
        )
    }
    val listState = rememberLazyListState()
    var isVisible by remember { mutableStateOf(false) }
    var showRewardDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isVisible = true
    }

    LaunchedEffect(mockStreakData.activeDay) {
        if (mockStreakData.activeDay > 2) {
            listState.animateScrollToItem(
                index = maxOf(0, mockStreakData.activeDay - 3),
                scrollOffset = 0
            )
        }
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(
            animationSpec = tween(800, delayMillis = 300),
            initialOffsetY = { it / 2 }
        ) + fadeIn(
            animationSpec = tween(800, delayMillis = 300)
        ) + scaleIn(
            animationSpec = tween(800, delayMillis = 300),
            initialScale = 0.9f
        )
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (mockStreakData.isTodayClaimAvailable) {
                    StreakCardBackground.copy(alpha = 0.9f)
                } else StreakCardBackground
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = if (mockStreakData.isTodayClaimAvailable) 12.dp else 8.dp
            ),
            border = if (mockStreakData.isTodayClaimAvailable) {
                BorderStroke(
                    2.dp,
                    Brush.horizontalGradient(
                        colors = listOf(
                            StreakRewardGold.copy(alpha = 0.6f),
                            StreakRewardGold,
                            StreakRewardGold.copy(alpha = 0.6f)
                        )
                    )
                )
            } else null
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                if (mockStreakData.isTodayClaimAvailable) {
                    RewardNotification(
                        activeDay = mockStreakData.activeDay,
                        modifier = Modifier.align(Alignment.TopEnd)
                    )
                }

                Column {
                    // Анімований заголовок
                    AnimatedVisibility(
                        visible = isVisible,
                        enter = slideInHorizontally(
                            animationSpec = tween(600, delayMillis = 600),
                            initialOffsetX = { -it / 2 }
                        ) + fadeIn(
                            animationSpec = tween(600, delayMillis = 600)
                        )
                    ) {
                        Text(
                            text = "Щоденна активність",
                            style = MaterialTheme.typography.titleMedium,
                            color = TextPrimaryDark,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    LazyRow(
                        state = listState,
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(TileSpacing),
                        contentPadding = PaddingValues(horizontal = 8.dp)
                    ) {
                        itemsIndexed((1..mockStreakData.totalDays).toList()) { index, dayNumber ->
                            val dayState = when {
                                dayNumber < mockStreakData.activeDay -> DayState.Completed
                                dayNumber == mockStreakData.activeDay -> DayState.Active
                                else -> DayState.Upcoming
                            }

                            AnimatedVisibility(
                                visible = isVisible,
                                enter = scaleIn(
                                    animationSpec = tween(
                                        durationMillis = 400,
                                        delayMillis = minOf(800 + index * 50, 2000)
                                    ),
                                    initialScale = 0.5f
                                ) + fadeIn(
                                    animationSpec = tween(
                                        durationMillis = 400,
                                        delayMillis = minOf(800 + index * 50, 2000)
                                    )
                                )
                            ) {
                                DayStreakItem(
                                    dayNumber = dayNumber,
                                    dayState = dayState,
                                    hasReward = mockStreakData.isTodayClaimAvailable && dayNumber == mockStreakData.activeDay,
                                    onRewardClick = {
                                        if (mockStreakData.isTodayClaimAvailable && dayNumber == mockStreakData.activeDay) {
                                            showRewardDialog = true
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (showRewardDialog) {
        RewardDialog(
            day = mockStreakData.activeDay,
            amount = mockStreakData.todayRewardAmount,
            onDismiss = { showRewardDialog = false },
            onClaim = { coins ->
                // TODO: When ViewModel is ready, call viewModel.claimReward()
                showRewardDialog = false
            }
        )
    }
}

@Composable
private fun RewardNotification(
    activeDay: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Натисни на день $activeDay!",
            style = MaterialTheme.typography.bodySmall,
            color = StreakRewardGold,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.width(4.dp))

        Box(
            modifier = Modifier
                .size(32.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            StreakRewardGold,
                            StreakRewardGold.copy(alpha = 0.7f)
                        )
                    ),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.coins),
                contentDescription = "Reward Available",
                tint = TextPrimaryDark,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Preview
@Composable
private fun DailyStreakSectionPreview() {
    DailyStreakSection()
}