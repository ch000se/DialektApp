package com.example.dialektapp.presentation.screens.leaderboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dialektapp.R
import com.example.dialektapp.domain.model.LeaderboardEntry
import com.example.dialektapp.presentation.components.UserAvatar
import com.example.dialektapp.presentation.screens.leaderboard.components.podium.CoinsDisplay
import com.example.dialektapp.ui.theme.*

@Composable
fun LeaderboardListItem(
    entry: LeaderboardEntry,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp, horizontal = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier.size(42.dp)
            ) {
                UserAvatar(
                    imageUrl = entry.profileImageUrl,
                    contentDescription = "Avatar",
                    size = 42.dp,
                    borderWidth = 2.dp,
                    borderColor = LeaderboardListAvatarBorder,
                    backgroundColor = LeaderboardListAvatarBackground
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 4.dp, y = (-4).dp)
                        .size(19.dp)
                        .background(LeaderboardListRankBadgeBackground, CircleShape)
                        .border(1.5.dp, LeaderboardListRankBadgeBorder, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = entry.rank.toString(),
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        lineHeight = 11.sp
                    )
                }
            }

            Column(
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = entry.fullName,
                    color = LeaderboardListUserNameColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 14.sp
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = entry.userName,
                    color = LeaderboardListUserClassColor,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 11.sp
                )
            }
        }

        CoinsDisplay(
            coins = entry.coins
        )
    }
}

@Preview
@Composable
private fun LeaderboardListItemPreview() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        LeaderboardListItem(
            entry = LeaderboardEntry(
                rank = 4,
                userId = "4",
                userName = "@jack",
                fullName = "Jack",
                coins = 7984,
                profileImageUrl = ""
            )
        )
        LeaderboardListItem(
            entry = LeaderboardEntry(
                rank = 5,
                userId = "5",
                userName = "@kitty",
                fullName = "Maddie Kity",
                coins = 7932,
                profileImageUrl = ""
            )
        )
    }
}