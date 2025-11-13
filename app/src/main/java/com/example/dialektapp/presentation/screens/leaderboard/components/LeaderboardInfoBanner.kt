package com.example.dialektapp.presentation.screens.leaderboard.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dialektapp.R
import com.example.dialektapp.ui.theme.*

@Composable
fun LeaderboardInfoBanner(
    message: String,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = LeaderboardListInfoBannerBackground
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.face_sad_tear),
                contentDescription = "Info",
                tint = LeaderboardListInfoIconColor,
                modifier = Modifier.size(20.dp)
            )

            Text(
                text = message,
                color = LeaderboardListInfoTextColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Start,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview
@Composable
private fun LeaderboardInfoBannerPreview() {
    LeaderboardInfoBanner(
        message = "Sorry you still haven't earned a spot on the Leaderboard"
    )
}