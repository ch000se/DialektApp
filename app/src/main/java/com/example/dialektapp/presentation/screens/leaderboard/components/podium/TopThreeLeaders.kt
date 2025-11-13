package com.example.dialektapp.presentation.screens.leaderboard.components.podium

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dialektapp.domain.model.LeaderboardEntry
import com.example.dialektapp.ui.theme.TopLeadersContainerBackground

@Composable
fun TopThreeLeaders(
    leaders: List<LeaderboardEntry>,
) {
    if (leaders.size < 3) return

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        TopLeadersContainerBackground,
                        TopLeadersContainerBackground
                    )
                )
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.Top
        ) {
            TopLeaderCard(
                entry = leaders[1],
                rank = 2,
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 40.dp)
            )

            TopLeaderCard(
                entry = leaders[0],
                rank = 1,
                modifier = Modifier.weight(1f)
            )

            TopLeaderCard(
                entry = leaders[2], // Third place
                rank = 3,
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 60.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopThreeLeadersPreview() {
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
        )
    )

    TopThreeLeaders(
        leaders = mockLeaders
    )
}