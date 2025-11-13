package com.example.dialektapp.presentation.screens.achievements.components.content

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dialektapp.domain.model.Achievement
import com.example.dialektapp.presentation.screens.achievements.components.AchievementItem

@Composable
fun AchievementsGrid(
    achievements: List<Achievement>,
    onAchievementClick: (Achievement) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Всі досягнення",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            items(achievements, key = { it.id }) { achievement ->
                AchievementItem(
                    achievement = achievement,
                    onClick = onAchievementClick
                )
            }
        }
    }
}
