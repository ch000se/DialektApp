package com.example.dialektapp.presentation.screens.leaderboard.components.header

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dialektapp.domain.model.LeaderboardPeriod
import com.example.dialektapp.ui.theme.LeaderboardScreenBackgroundColor

@Composable
fun LeaderboardHeader(
    selectedPeriod: LeaderboardPeriod,
    onPeriodChanged: (LeaderboardPeriod) -> Unit,
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LeaderboardTitle()

        Spacer(modifier = Modifier.height(16.dp))

        LeaderboardPeriodSelector(
            selectedPeriod = selectedPeriod,
            onPeriodChanged = onPeriodChanged
        )
    }
}

@Preview
@Composable
private fun LeaderboardHeaderPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(LeaderboardScreenBackgroundColor)
            .padding(16.dp)
    ) {
        LeaderboardHeader(
            selectedPeriod = LeaderboardPeriod.ALL_TIME,
            onPeriodChanged = {}
        )
    }
}