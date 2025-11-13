package com.example.dialektapp.presentation.screens.activity.components.introduction

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dialektapp.domain.model.ActivityContent

private val CardBackground = Color(0xFF1F2025)
private val TextWhite = Color.White
private val TextGray = Color(0xFFE0E0E0)
private val GreenAccent = Color(0xFFD1F501)

@Composable
fun IntroductionContent(
    content: ActivityContent.Introduction,
    onComplete: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            IntroductionTitle(title = content.title)
        }

        item {
            IntroductionDescription(description = content.description)
        }

        item {
            Text(
                text = "Ключові моменти:",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextWhite
            )
        }

        items(content.keyPoints) { keyPoint ->
            KeyPointCard(keyPoint = keyPoint)
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = onComplete,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = GreenAccent,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Завершити",
                    modifier = Modifier.padding(8.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun IntroductionTitle(title: String) {
    Text(
        text = title,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = TextWhite
    )
}

@Composable
private fun IntroductionDescription(description: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = description,
            fontSize = 16.sp,
            color = TextGray,
            modifier = Modifier.padding(16.dp),
            lineHeight = 24.sp
        )
    }
}

@Composable
private fun KeyPointCard(keyPoint: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "•",
                fontSize = 18.sp,
                color = GreenAccent,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = keyPoint,
                fontSize = 16.sp,
                color = TextWhite
            )
        }
    }
}
