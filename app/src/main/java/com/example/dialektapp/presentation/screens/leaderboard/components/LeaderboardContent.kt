package com.example.dialektapp.presentation.screens.leaderboard.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dialektapp.domain.model.LeaderboardData
import com.example.dialektapp.domain.model.LeaderboardEntry
import com.example.dialektapp.domain.model.LeaderboardPeriod
import com.example.dialektapp.presentation.screens.leaderboard.LeaderboardUiState
import com.example.dialektapp.presentation.screens.leaderboard.components.header.LeaderboardHeader
import com.example.dialektapp.presentation.screens.leaderboard.components.podium.TopThreeLeaders
import com.example.dialektapp.presentation.screens.leaderboard.components.states.LeaderboardLoadingState
import kotlinx.coroutines.delay

@Composable
fun LeaderboardContent(
    uiState: LeaderboardUiState,
    isVisible: Boolean,
    onPeriodChanged: (LeaderboardPeriod) -> Unit,
) {
    var showHeader by remember { mutableStateOf(false) }
    var showContent by remember { mutableStateOf(false) }

    LaunchedEffect(isVisible) {
        if (isVisible) {
            showHeader = false
            showContent = false

            delay(100) // Невелика затримка
            showHeader = true

            delay(300) // Затримка між заголовком та контентом
            showContent = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
    ) {
        AnimatedVisibility(
            visible = showHeader,
            enter = slideInVertically(
                animationSpec = tween(600),
                initialOffsetY = { -it / 2 }
            ) + fadeIn(
                animationSpec = tween(600)
            )
        ) {
            LeaderboardHeader(
                selectedPeriod = uiState.selectedPeriod,
                onPeriodChanged = onPeriodChanged
            )
        }

        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        AnimatedVisibility(
            visible = showContent,
            enter = slideInVertically(
                animationSpec = tween(800),
                initialOffsetY = { it / 3 }
            ) + fadeIn(
                animationSpec = tween(800)
            )
        ) {
            AnimatedContent(
                targetState = uiState.selectedPeriod to uiState.isLoading,
                transitionSpec = {
                    val isGoingToWeekly = targetState.first == LeaderboardPeriod.WEEKLY

                    val enter = slideInHorizontally(
                        animationSpec = tween(800),
                        initialOffsetX = { fullWidth ->
                            if (isGoingToWeekly) fullWidth / 2 else -fullWidth / 2
                        }
                    ) + fadeIn(
                        animationSpec = tween(900, delayMillis = 200)
                    )

                    val exit = slideOutHorizontally(
                        animationSpec = tween(700),
                        targetOffsetX = { fullWidth ->
                            if (isGoingToWeekly) -fullWidth / 2 else fullWidth / 2
                        }
                    ) + fadeOut(
                        animationSpec = tween(600)
                    )

                    enter togetherWith exit
                },
                label = "leaderboard_content"
            ) { (period, isLoading) ->
                key(period) {
                    if (isLoading) {
                        LeaderboardLoadingState()
                    } else {
                        Box(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            if (uiState.leaderboardData.topEntries.size >= 3) {
                                TopThreeLeaders(
                                    leaders = uiState.leaderboardData.topEntries.take(3)
                                )
                            }

                            if (uiState.leaderboardData.topEntries.size > 3) {
                                LeaderboardList(
                                    entries = uiState.leaderboardData.topEntries,
                                    userRankInfo = uiState.leaderboardData.userRankInfo
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LeaderboardContentPreview() {
    LeaderboardContent(
        uiState = LeaderboardUiState(),
        isVisible = true,
        onPeriodChanged = {}

    ) 
}