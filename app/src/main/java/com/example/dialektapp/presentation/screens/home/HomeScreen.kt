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
import com.example.dialektapp.domain.model.Course
import com.example.dialektapp.domain.model.User
import com.example.dialektapp.domain.model.UserStats
import com.example.dialektapp.presentation.screens.home.components.*
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
) {
    var isVisible by remember { mutableStateOf(false) }

    // Тимчасові mock дані
    val mockStats = remember {
        UserStats(
            userId = "1",
            totalCoins = 1247,
            weeklyCoins = 320
        )
    }

    val mockUser = remember {
        User(
            id = "1",
            username = "user123",
            email = "user@example.com",
            fullName = "Іван Петренко",
            profileImageUrl = null,
            role = "User"
        )
    }

    val mockCourses = remember {
        listOf(
            Course(
                id = "transcarpathian",
                name = "Закарпатський діалект",
                description = "Вивчайте закарпатську говірку",
                imageUrl = "",
                imageUrlBack = "",
                totalModules = 5,
                completedModules = 2
            ),
            Course(
                id = "galician",
                name = "Галицький діалект",
                description = "Вивчайте галицьку говірку",
                imageUrl = "",
                imageUrlBack = "",
                totalModules = 5,
                completedModules = 0
            ),
            Course(
                id = "kuban",
                name = "Кубанський діалект",
                description = "Вивчайте кубанську говірку",
                imageUrl = "",
                imageUrlBack = "",
                totalModules = 5,
                completedModules = 0
            )
        )
    }

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
                    user = mockUser,
                    onProfileClick = onProfileClick,
                    coinsCount = mockStats.totalCoins
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
                                courses = mockCourses,
                                isLoading = false,
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
