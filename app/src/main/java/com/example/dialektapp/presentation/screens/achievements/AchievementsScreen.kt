package com.example.dialektapp.presentation.screens.achievements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.dialektapp.domain.model.Achievement
import com.example.dialektapp.presentation.screens.achievements.components.AchievementDetailDialog
import com.example.dialektapp.presentation.screens.achievements.components.content.AchievementsGrid
import com.example.dialektapp.presentation.screens.achievements.components.content.AchievementsHeader
import com.example.dialektapp.ui.theme.BackgroundDeepBlue

@Composable
fun AchievementsScreen(
    onNavigateBack: () -> Unit = {},
    viewModel: AchievementsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var selectedAchievement by remember { mutableStateOf<Achievement?>(null) }

    selectedAchievement?.let { achievement ->
        AchievementDetailDialog(
            achievement = achievement,
            onDismiss = { selectedAchievement = null },
            onActionClick = if (achievement.isUnlocked) {
                {
                    when (achievement.id) {
                        "first_book", "third_book" -> {
                        }

                        "registration" -> {
                        }
                    }
                    selectedAchievement = null
                }
            } else null
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDeepBlue)
    ) {
        SuccessContent(
            uiState = uiState,
            onAchievementClick = { achievement ->
                selectedAchievement = achievement
            }
        )
    }
}

@Composable
private fun SuccessContent(
    uiState: AchievementsUiState,
    onAchievementClick: (Achievement) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(24.dp)
    ) {
        AchievementsHeader(
            unlockedCount = uiState.unlockedCount,
            totalCount = uiState.totalCount
        )

        Spacer(modifier = Modifier.height(32.dp))

        AchievementsGrid(
            achievements = uiState.achievements,
            onAchievementClick = onAchievementClick,
            modifier = Modifier.fillMaxSize()
        )
    }
}