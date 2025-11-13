package com.example.dialektapp.presentation.screens.leaderboard.components.podium

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dialektapp.domain.model.LeaderboardEntry
import com.example.dialektapp.ui.theme.TopLeadersCardBorderColor
import com.example.dialektapp.ui.theme.TopLeadersFirstPlaceGradientEnd
import com.example.dialektapp.ui.theme.TopLeadersFirstPlaceGradientStart
import com.example.dialektapp.ui.theme.TopLeadersFirstPlaceShadowColor
import com.example.dialektapp.ui.theme.TopLeadersGlassOverlayEnd
import com.example.dialektapp.ui.theme.TopLeadersGlassOverlayMiddle
import com.example.dialektapp.ui.theme.TopLeadersGlassOverlayStart
import com.example.dialektapp.ui.theme.TopLeadersSecondPlaceGradientEnd
import com.example.dialektapp.ui.theme.TopLeadersSecondPlaceGradientStart
import com.example.dialektapp.ui.theme.TopLeadersSecondPlaceShadowColor
import com.example.dialektapp.ui.theme.TopLeadersThirdPlaceGradientEnd
import com.example.dialektapp.ui.theme.TopLeadersThirdPlaceGradientStart
import com.example.dialektapp.ui.theme.TopLeadersThirdPlaceShadowColor

@Composable
fun TopLeaderCard(
    entry: LeaderboardEntry,
    rank: Int,
    modifier: Modifier = Modifier,
) {
    val (gradientColors, shadowColor, shadowElevation) = when (rank) {
        1 -> Triple(
            listOf(TopLeadersFirstPlaceGradientStart, TopLeadersFirstPlaceGradientEnd),
            TopLeadersFirstPlaceShadowColor,
            20.dp
        )

        2 -> Triple(
            listOf(TopLeadersSecondPlaceGradientStart, TopLeadersSecondPlaceGradientEnd),
            TopLeadersSecondPlaceShadowColor,
            16.dp
        )

        else -> Triple(
            listOf(TopLeadersThirdPlaceGradientStart, TopLeadersThirdPlaceGradientEnd),
            TopLeadersThirdPlaceShadowColor,
            12.dp
        )
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .shadow(
                elevation = shadowElevation,
                shape = RoundedCornerShape(20.dp),
                spotColor = shadowColor,
                ambientColor = shadowColor
            ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(
                    brush = Brush.verticalGradient(gradientColors),
                    shape = RoundedCornerShape(20.dp)
                )
                .border(
                    width = 1.dp,
                    color = TopLeadersCardBorderColor,
                    shape = RoundedCornerShape(20.dp)
                ),
            contentAlignment = Alignment.TopCenter
        ) {
            // Glass morphism overlay
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                TopLeadersGlassOverlayStart,
                                TopLeadersGlassOverlayMiddle,
                                TopLeadersGlassOverlayEnd
                            )
                        ),
                        shape = RoundedCornerShape(20.dp)
                    )
            )

            TopLeaderProfile(
                entry = entry,
                rank = rank
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopLeaderCardPreview() {
    val mockEntry = LeaderboardEntry(
        rank = 1,
        userId = "1",
        fullName = "Nikita Putri",
        userName = "2A",
        coins = 8040,
        profileImageUrl = null
    )

    Box(modifier = Modifier.padding(16.dp)) {
        TopLeaderCard(
            entry = mockEntry,
            rank = 1
        )
    }
}