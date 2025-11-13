package com.example.dialektapp.presentation.screens.home

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.dialektapp.presentation.screens.home.components.*
import com.example.dialektapp.presentation.screens.home.viewmodel.HomeViewModel
import com.example.dialektapp.ui.theme.BackColor
import com.example.dialektapp.ui.theme.HomeSurfaceColor
import com.example.dialektapp.ui.theme.LeaderboardListCardBackground
import com.example.dialektapp.ui.theme.LeaderboardListCardSecondaryBackground
import com.example.dialektapp.ui.theme.LeaderboardListOverlayBottom
import com.example.dialektapp.ui.theme.LeaderboardListOverlayEnd
import com.example.dialektapp.ui.theme.LeaderboardListOverlayMiddle
import com.example.dialektapp.ui.theme.LeaderboardListOverlayStart

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onProfileClick: () -> Unit = {},
    onCourseClick: (String) -> Unit = {},
    viewModel: HomeViewModel = hiltViewModel()
) {
    var isVisible by remember { mutableStateOf(false) }

    val user by viewModel.user.collectAsStateWithLifecycle()
    val stats by viewModel.stats.collectAsStateWithLifecycle()
    val courses by viewModel.courses.collectAsStateWithLifecycle()
    val isLoadingCourses by viewModel.isLoadingCourses.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        isVisible = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackColor)
    ) {
        AnimatedVisibility(
            visible = isVisible,
            enter = slideInVertically(
                animationSpec = tween(600, delayMillis = 100),
                initialOffsetY = { -it / 2 }
            ) + fadeIn(
                animationSpec = tween(600, delayMillis = 100)
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                TopUserBar(
                    user = user,
                    onProfileClick = onProfileClick,
                    coinsCount = stats.totalCoins
                )

                DailyStreakSection()
            }
        }

        AnimatedVisibility(
            visible = isVisible,
            enter = slideInVertically(
                animationSpec = tween(800, delayMillis = 200),
                initialOffsetY = { it / 2 }
            ) + fadeIn(
                animationSpec = tween(800, delayMillis = 200)
            ) + scaleIn(
                animationSpec = tween(800, delayMillis = 200),
                initialScale = 0.95f
            )
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                colors = CardDefaults.cardColors(containerColor = HomeSurfaceColor),
                elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    LeaderboardListCardBackground,
                                    LeaderboardListCardSecondaryBackground
                                )
                            )
                        )
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    LeaderboardListOverlayStart,
                                    LeaderboardListOverlayMiddle,
                                    LeaderboardListOverlayEnd,
                                    LeaderboardListOverlayBottom
                                )
                            )
                        )
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(32.dp)
                    ) {
                        item {
                            CoursesSection(
                                courses = courses,
                                isLoading = isLoadingCourses,
                                onCourseClick = onCourseClick
                            )
                        }

                        item {
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen()
    }
}
