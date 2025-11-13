package com.example.dialektapp.presentation.screens.leaderboard.components.header

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.dialektapp.ui.theme.*

@Composable
fun LeaderboardTitle() {
    Text(
        text = "Таблиця лідерів",
        fontSize = 22.sp,
        fontWeight = FontWeight.ExtraBold,
        color = LeaderboardTitleColor,
        style = androidx.compose.ui.text.TextStyle(
            shadow = androidx.compose.ui.graphics.Shadow(
                color = LeaderboardTitleShadowColor,
                offset = androidx.compose.ui.geometry.Offset(1f, 1f),
                blurRadius = 3f
            )
        )
    )
}

@Preview
@Composable
private fun LeaderboardTitlePreview() {
    LeaderboardTitle()
}