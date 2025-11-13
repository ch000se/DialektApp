package com.example.dialektapp.presentation.screens.leaderboard.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dialektapp.ui.theme.*

@Composable
fun LeaderboardListHeader() {
    Column(
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Text(
            text = "Топ 4 - 50 студентів",
            color = LeaderboardListHeaderTitleColor,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Заробляй більше монет, щоб піднятися вище",
            color = LeaderboardListHeaderSubtitleColor,
            fontSize = 13.sp,
            fontWeight = FontWeight.Normal
        )
    }
}

@Preview
@Composable
private fun LeaderboardListHeaderPreview() {
    LeaderboardListHeader()
}