package com.example.dialektapp.presentation.screens.leaderboard.components.header

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dialektapp.ui.theme.*

@Composable
fun PeriodButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val textColor by animateColorAsState(
        targetValue = if (isSelected) {
            LeaderboardSelectorBackgroundColor
        } else {
            LeaderboardUnselectedButtonColor
        },
        animationSpec = tween(durationMillis = 300),
        label = "text_color"
    )

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .padding(vertical = 1.dp),
        contentAlignment = Alignment.Center
    ) {
        TextButton(
            onClick = onClick,
            colors = ButtonDefaults.textButtonColors(
                contentColor = textColor
            ),
            contentPadding = PaddingValues(
                horizontal = 8.dp,
                vertical = 0.dp
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = textColor
            )
        }
    }
}

@Preview
@Composable
private fun PeriodButtonPreview() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        PeriodButton(
            text = "Весь час",
            isSelected = true,
            onClick = {}
        )
        PeriodButton(
            text = "Тиждень",
            isSelected = false,
            onClick = {}
        )
    }
}