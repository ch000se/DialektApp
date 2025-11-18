package com.example.dialektapp.presentation.screens.home.components

import android.util.Log
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
import com.example.dialektapp.presentation.screens.home.HomeViewModel
import com.example.dialektapp.ui.theme.*

private val TileSpacing = 12.dp
private const val TAG = "DailyStreakSection"

@Composable
fun DailyStreakSection(
    viewModel: HomeViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val streakData = uiState.streakData

    val listState = rememberLazyListState()
    var isVisible by remember { mutableStateOf(false) }
    var showRewardDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isVisible = true
    }

    LaunchedEffect(streakData?.activeDay) {
        streakData?.let {
            // Прокручуємо до activeDay (поточний день стріку)
            if (it.activeDay > 2) {
                listState.animateScrollToItem(
                    index = maxOf(0, it.activeDay - 3),
                    scrollOffset = 0
                )
            }
        }
    }

    // Показуємо loader якщо дані завантажуються
    if (uiState.isLoadingStreak) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = StreakCardBackground)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = AccentGold)
            }
        }
        return
    }

    // Показуємо помилку якщо є
    if (uiState.streakError != null) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = StreakCardBackground)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = uiState.streakError ?: "Помилка завантаження",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextPrimaryDark
                    )
                    Button(
                        onClick = { viewModel.loadStreak() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AccentGold,
                            contentColor = TextPrimaryDark
                        )
                    ) {
                        Text("Спробувати знову")
                    }
                }
            }
        }
        return
    }

    // Показуємо streak якщо дані завантажені
    streakData?.let { data ->
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
                    containerColor = if (data.isTodayClaimAvailable) {
                        StreakCardBackground.copy(alpha = 0.9f)
                    } else StreakCardBackground
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = if (data.isTodayClaimAvailable) 12.dp else 8.dp
                ),
                border = if (data.isTodayClaimAvailable) {
                    BorderStroke(
                        2.dp,
                        Brush.horizontalGradient(
                            colors = listOf(
                                AccentGold.copy(alpha = 0.6f),
                                AccentGold,
                                AccentGold.copy(alpha = 0.6f)
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
                    if (data.isTodayClaimAvailable) {
                        // activeDay - це ПОТОЧНИЙ день стріку (день за який можна взяти нагороду)
                        // activeDay = 1 -> стрік день 1, можна взяти нагороду за день 1
                        // activeDay = 2 -> стрік день 2, можна взяти нагороду за день 2
                        RewardNotification(
                            activeDay = data.activeDay,
                            modifier = Modifier.align(Alignment.TopEnd)
                        )
                    }

                    Column {
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
                            // Визначаємо скільки днів показувати:
                            // 1. Якщо totalDays > 0, показуємо max(totalDays, activeDay + 7)
                            // 2. Якщо totalDays = 0 або дуже малий, показуємо activeDay + 7
                            // 3. Завжди мінімум 7 днів
                            val minDaysToShow = 7
                            val futureDaysBuffer = 7
                            val daysToShow = when {
                                data.totalDays > data.activeDay -> {
                                    // Якщо totalDays більший за activeDay, показуємо totalDays + buffer
                                    maxOf(data.totalDays + futureDaysBuffer, minDaysToShow)
                                }

                                else -> {
                                    // Інакше показуємо activeDay + buffer
                                    maxOf(data.activeDay + futureDaysBuffer, minDaysToShow)
                                }
                            }

                            Log.d(
                                TAG,
                                "Streak UI: activeDay=${data.activeDay}, totalDays=${data.totalDays}, showing $daysToShow days, claimAvailable=${data.isTodayClaimAvailable}"
                            )

                            itemsIndexed((1..daysToShow).toList()) { index, dayNumber ->
                                // Логіка стану днів:
                                // activeDay - це ПОТОЧНИЙ день стріку
                                // - Дні < activeDay: Completed (попередні дні)
                                // - День == activeDay: Active (поточний день стріку, можна взяти нагороду)
                                // - Дні > activeDay: Upcoming (майбутні дні)
                                val dayState = when {
                                    dayNumber < data.activeDay -> DayState.Completed
                                    dayNumber == data.activeDay -> DayState.Active
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
                                    // День з нагородою - це activeDay (поточний день стріку)
                                    DayStreakItem(
                                        dayNumber = dayNumber,
                                        dayState = dayState,
                                        hasReward = data.isTodayClaimAvailable && dayNumber == data.activeDay,
                                        onRewardClick = {
                                            if (data.isTodayClaimAvailable && dayNumber == data.activeDay) {
                                                viewModel.showRewardDialog()
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
    }

    // Показуємо діалог нагороди
    if (uiState.showRewardDialog && streakData != null) {
        // День для claim - це activeDay (поточний день стріку)
        RewardDialog(
            day = streakData.activeDay,
            amount = streakData.todayRewardAmount,
            isClaimingReward = uiState.isClaimingStreak,
            claimErrorMessage = uiState.claimError,
            onDismiss = { viewModel.hideRewardDialog() },
            onClaim = { coins ->
                viewModel.claimStreak()
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
            color = AccentGold,
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
                            AccentGold,
                            AccentGold.copy(alpha = 0.7f)
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