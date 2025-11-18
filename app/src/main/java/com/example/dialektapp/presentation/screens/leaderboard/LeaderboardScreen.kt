package com.example.dialektapp.presentation.screens.leaderboard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.dialektapp.domain.model.LeaderboardData
import com.example.dialektapp.domain.model.LeaderboardEntry
import com.example.dialektapp.domain.model.LeaderboardPeriod
import com.example.dialektapp.presentation.screens.leaderboard.components.LeaderboardContent
import com.example.dialektapp.ui.theme.LeaderboardScreenBackgroundColor

@Composable
fun LeaderboardScreen(
    viewModel: LeaderboardViewModel = hiltViewModel()
) {
    var isVisible by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        isVisible = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LeaderboardScreenBackgroundColor)
    ) {
        AnimatedVisibility(
            visible = isVisible,
            enter = slideInVertically(
                animationSpec = tween(800, delayMillis = 100),
                initialOffsetY = { it / 2 }
            ) + fadeIn(
                animationSpec = tween(800, delayMillis = 100)
            )
        ) {
            LeaderboardContent(
                uiState = uiState,
                isVisible = true,
                onPeriodChanged = { period -> viewModel.onPeriodChanged(period) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LeaderboardScreenPreview() {
    val mockLeaders = listOf(
        LeaderboardEntry(
            rank = 1,
            userId = "1",
            fullName = "Nikita Putri",
            userName = "2A",
            coins = 8040,
            profileImageUrl = null
        ),
        LeaderboardEntry(
            rank = 2,
            userId = "2",
            fullName = "Azzahra",
            userName = "1B",
            coins = 8010,
            profileImageUrl = null
        ),
        LeaderboardEntry(
            rank = 3,
            userId = "3",
            fullName = "Ipul Putra",
            userName = "3A",
            coins = 8000,
            profileImageUrl = null
        ),
        LeaderboardEntry(
            rank = 4,
            userId = "4",
            fullName = "John Doe",
            userName = "2B",
            coins = 7950,
            profileImageUrl = null
        ),
        LeaderboardEntry(
            rank = 5,
            userId = "5",
            fullName = "Jane Smith",
            userName = "3A",
            coins = 7900,
            profileImageUrl = null
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LeaderboardScreenBackgroundColor)
    ) {
        var isVisible by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            isVisible = true
        }

        when {
            true -> {
                AnimatedVisibility(
                    visible = isVisible,
                    enter = slideInVertically(
                        animationSpec = tween(800, delayMillis = 100),
                        initialOffsetY = { it / 2 }
                    ) + fadeIn(
                        animationSpec = tween(800, delayMillis = 100)
                    )
                ) {
                    LeaderboardContent(
                        uiState = LeaderboardUiState(
                            leaderboardData = LeaderboardData(
                                topEntries = mockLeaders,
                                userRankInfo = "Sorry you still haven't earned a spot in top 50",
                                period = LeaderboardPeriod.ALL_TIME
                            ),
                            isLoading = false,
                            error = null,
                            selectedPeriod = LeaderboardPeriod.ALL_TIME
                        ),
                        isVisible = true,
                        onPeriodChanged = {}
                    )
                }
            }
        }
    }
}