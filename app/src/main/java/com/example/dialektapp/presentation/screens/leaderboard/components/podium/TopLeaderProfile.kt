package com.example.dialektapp.presentation.screens.leaderboard.components.podium

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.dialektapp.R
import com.example.dialektapp.domain.model.LeaderboardEntry
import com.example.dialektapp.presentation.components.UserAvatar
import com.example.dialektapp.ui.theme.AccentBronze
import com.example.dialektapp.ui.theme.AccentGold
import com.example.dialektapp.ui.theme.AccentSilver
import com.example.dialektapp.ui.theme.TopLeadersBadgeBorderColor
import com.example.dialektapp.ui.theme.TopLeadersBadgeGradientEnd
import com.example.dialektapp.ui.theme.TopLeadersBadgeGradientStart
import com.example.dialektapp.ui.theme.TopLeadersBadgeTextColor
import com.example.dialektapp.ui.theme.TopLeadersCoinsBackgroundEnd
import com.example.dialektapp.ui.theme.TopLeadersCoinsBackgroundStart
import com.example.dialektapp.ui.theme.TopLeadersCoinsBorderColor
import com.example.dialektapp.ui.theme.TopLeadersCoinsTextColor
import com.example.dialektapp.ui.theme.TopLeadersNameTextColor
import com.example.dialektapp.ui.theme.TopLeadersProfileBackgroundColor

@Composable
fun TopLeaderProfile(
    entry: LeaderboardEntry,
    rank: Int,
) {
    val avatarBorderColor = when (rank) {
        1 -> AccentGold
        2 -> AccentSilver
        3 -> AccentBronze
        else -> AccentBronze
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        Box(
            modifier = Modifier.size(if (rank == 1) 85.dp else 75.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(if (rank == 1) 75.dp else 65.dp)
                    .background(
                        color = TopLeadersProfileBackgroundColor,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                UserAvatar(
                    imageUrl = entry.profileImageUrl,
                    contentDescription = "User Avatar",
                    size = if (rank == 1) 75.dp else 65.dp,
                    borderWidth = 3.dp,
                    borderColor = avatarBorderColor
                )
            }

            RankBadge(
                rank = rank,
                modifier = Modifier.align(Alignment.BottomEnd)
            )
        }

        Spacer(modifier = Modifier.height(7.dp))

        CoinsDisplay(
            coins = entry.coins
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = entry.fullName,
            fontSize = if (rank == 1) 15.sp else 13.sp,
            fontWeight = FontWeight.ExtraBold,
            color = TopLeadersNameTextColor,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            lineHeight = if (rank == 1) 18.sp else 16.sp,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun RankBadge(
    rank: Int,
    modifier: Modifier = Modifier,
) {
    val numberBackColor = when (rank) {
        1 -> AccentGold
        2 -> AccentSilver
        3 -> AccentBronze
        else -> AccentBronze
    }

    Box(
        modifier = modifier
            .size(30.dp)
            .background(
                shape = CircleShape,
                color = numberBackColor
            )
            .border(
                width = 2.5.dp,
                color = Color.White,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = rank.toString(),
            fontSize = 15.sp,
            fontWeight = FontWeight.ExtraBold,
            color = TopLeadersBadgeTextColor
        )
    }
}

@Composable
fun CoinsDisplay(
    coins: Int,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        TopLeadersCoinsBackgroundStart,
                        TopLeadersCoinsBackgroundEnd
                    )
                )
            )
            .border(
                width = 1.5.dp,
                color = TopLeadersCoinsBorderColor,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.coins),
            contentDescription = "Coins",
            tint = TopLeadersCoinsTextColor,
            modifier = Modifier.size(18.dp)
        )

        Text(
            text = coins.toString(),
            fontSize = 14.sp,
            fontWeight = FontWeight.ExtraBold,
            color = TopLeadersCoinsTextColor
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TopLeaderProfilePreview() {
    val mockEntry = LeaderboardEntry(
        rank = 1,
        userId = "1",
        fullName = "Nikita Putri",
        userName = "@kukakuka",
        coins = 8040,
        profileImageUrl = null
    )

    TopLeaderProfile(
        entry = mockEntry,
        rank = 1
    )
}