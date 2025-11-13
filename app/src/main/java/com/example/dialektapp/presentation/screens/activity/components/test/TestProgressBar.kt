package com.example.dialektapp.presentation.screens.activity.components.test

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val CardBackground = Color(0xFF1F2025)
private val TextWhite = Color.White
private val GreenAccent = Color(0xFFD1F501)

@Composable
fun TestProgressBar(
    progress: Float,
    currentQuestion: Int,
    totalQuestions: Int
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Питання $currentQuestion з $totalQuestions",
                fontSize = 16.sp,
                color = TextWhite,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${(progress * 100).toInt()}%",
                fontSize = 14.sp,
                color = GreenAccent,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        val animatedProgress by animateFloatAsState(
            targetValue = progress,
            animationSpec = tween(durationMillis = 300),
            label = "progress"
        )

        LinearProgressIndicator(
            progress = { animatedProgress },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = GreenAccent,
            trackColor = CardBackground,
        )
    }
}
