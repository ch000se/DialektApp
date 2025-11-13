package com.example.dialektapp.presentation.screens.activity.components.test

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val CardBackground = Color(0xFF1F2025)
private val TextWhite = Color.White
private val TextGray = Color(0xFFE0E0E0)
private val GreenAccent = Color(0xFFD1F501)
private val RedAccent = Color(0xFFFF5252)

@Composable
fun AnswerOption(
    text: String,
    isSelected: Boolean,
    isCorrect: Boolean?,
    onClick: () -> Unit
) {
    val backgroundColor = when {
        isCorrect == true -> GreenAccent.copy(alpha = 0.2f)
        isCorrect == false && isSelected -> RedAccent.copy(alpha = 0.2f)
        isSelected -> GreenAccent.copy(alpha = 0.1f)
        else -> CardBackground
    }

    val borderColor = when {
        isCorrect == true -> GreenAccent
        isCorrect == false && isSelected -> RedAccent
        isSelected -> GreenAccent
        else -> TextGray.copy(alpha = 0.3f)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(2.dp, borderColor, RoundedCornerShape(12.dp))
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                fontSize = 16.sp,
                color = TextWhite,
                modifier = Modifier.weight(1f)
            )

            if (isCorrect != null) {
                Icon(
                    imageVector = if (isCorrect) Icons.Default.Check else Icons.Default.Close,
                    contentDescription = null,
                    tint = if (isCorrect) GreenAccent else RedAccent,
                    modifier = Modifier.size(24.dp)
                )
            } else if (isSelected) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(GreenAccent),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}
