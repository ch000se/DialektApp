package com.example.dialektapp.presentation.screens.leaderboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dialektapp.domain.model.LeaderboardEntry
import com.example.dialektapp.ui.theme.*

@Composable
fun LeaderboardList(
    entries: List<LeaderboardEntry>,
    userRankInfo: String,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 250.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
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
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                LeaderboardInfoBanner(message = userRankInfo)

                LeaderboardListHeader()

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(entries.drop(3)) { entry ->
                        LeaderboardListItem(entry = entry)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun LeaderboardListPreview() {
    val mockEntries = listOf(
        LeaderboardEntry(1, "1", "Nikita Putri", "English Class", 8040),
        LeaderboardEntry(2, "4", "Jack", "Indonesia Class", 7984),
        LeaderboardEntry(3, "5", "Maddie Kity", "Indonesia Class", 7932),
        LeaderboardEntry(6, "6", "Yummy Tiles", "Indonesia Class", 7915),
        LeaderboardEntry(7, "7", "Yummy Tiles", "Indonesia Class", 7915),
        LeaderboardEntry(8, "8", "Yummy Tiles", "Indonesia Class", 7915)
    )

    LeaderboardList(
        entries = mockEntries,
        userRankInfo = "Sorry you still haven't earned a spot on the Leaderboard"
    )
}