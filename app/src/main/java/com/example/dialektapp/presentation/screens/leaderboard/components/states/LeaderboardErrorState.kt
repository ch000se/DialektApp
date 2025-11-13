package com.example.dialektapp.presentation.screens.leaderboard.components.states

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.dialektapp.ui.theme.LeaderboardErrorIconColor
import com.example.dialektapp.ui.theme.LeaderboardErrorTextColor

@Composable
fun LeaderboardErrorState(
    errorMessage: String,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.face_sad_tear),
                contentDescription = "Error",
                tint = LeaderboardErrorIconColor,
                modifier = Modifier.size(64.dp)
            )

            Text(
                text = errorMessage,
                color = LeaderboardErrorTextColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LeaderboardErrorStatePreview() {
    LeaderboardErrorState(
        errorMessage = "Failed to load leaderboard data. Please try again."
    )
}