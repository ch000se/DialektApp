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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.dialektapp.domain.model.Achievement
import com.example.dialektapp.domain.model.AchievementCategory
import com.example.dialektapp.domain.model.AchievementRarity
import com.example.dialektapp.presentation.screens.achievements.components.AchievementDetailDialog
import com.example.dialektapp.presentation.screens.achievements.components.content.AchievementsGrid
import com.example.dialektapp.presentation.screens.achievements.components.content.AchievementsHeader

// Тимчасовий UI State до підключення ViewModel
data class AchievementsUiState(
    val achievements: List<Achievement> = emptyList(),
    val selectedAchievement: Achievement? = null,
    val unlockedCount: Int = 0,
    val totalCount: Int = 0,
)

@Composable
fun AchievementsScreen(
    onNavigateBack: () -> Unit = {},
) {
    val context = LocalContext.current

    // Тимчасові mock дані
    val mockAchievements = remember {
        listOf(
            Achievement(
                id = "registration",
                userId = "1",
                title = "Реєстрація",
                description = "Зареєструватися в додатку",
                isUnlocked = true,
                category = AchievementCategory.GENERAL,
                rarity = AchievementRarity.COMMON
            ),
            Achievement(
                id = "first_book",
                userId = "1",
                title = "Перша книга",
                description = "Закінчити перший курс",
                isUnlocked = true,
                category = AchievementCategory.LEARNING,
                rarity = AchievementRarity.RARE
            ),
            Achievement(
                id = "third_book",
                userId = "1",
                title = "Три книги",
                description = "Закінчити три курси",
                isUnlocked = false,
                category = AchievementCategory.LEARNING,
                rarity = AchievementRarity.EPIC
            ),
            Achievement(
                id = "week_streak",
                userId = "1",
                title = "Тижневий стрік",
                description = "Навчатися 7 днів підряд",
                isUnlocked = false,
                category = AchievementCategory.STREAK,
                rarity = AchievementRarity.RARE
            ),
            Achievement(
                id = "social_butterfly",
                userId = "1",
                title = "Соціальна метелик",
                description = "Додати 10 друзів",
                isUnlocked = false,
                category = AchievementCategory.SOCIAL,
                rarity = AchievementRarity.COMMON
            )
        )
    }

    var selectedAchievement by remember { mutableStateOf<Achievement?>(null) }

    val uiState = AchievementsUiState(
        achievements = mockAchievements,
        selectedAchievement = selectedAchievement,
        unlockedCount = mockAchievements.count { it.isUnlocked },
        totalCount = mockAchievements.size
    )

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
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1A237E),
                        Color(0xFF283593),
                        Color(0xFF303F9F)
                    )
                )
            )
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