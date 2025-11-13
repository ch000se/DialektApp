package com.example.dialektapp.presentation.screens.activity.components.explaining

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
import com.example.dialektapp.presentation.screens.activity.components.common.ExampleCard

private val CardBackground = Color(0xFF1F2025)
private val TextWhite = Color.White
private val GreenAccent = Color(0xFFD1F501)

@Composable
fun ExplainingContent(
    content: ActivityContent.Explaining,
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
            ExplainingTitle(title = content.title)
        }

        item {
            ExplanationCard(explanation = content.explanation)
        }

        if (content.examples.isNotEmpty()) {
            item {
                Text(
                    text = "Приклади:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite
                )
            }

            items(content.examples) { example ->
                ExampleCard(example)
            }
        }

        if (content.tips.isNotEmpty()) {
            item {
                Text(
                    text = "Корисні поради:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite
                )
            }

            items(content.tips) { tip ->
                TipCard(tip = tip)
            }
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
private fun ExplainingTitle(title: String) {
    Text(
        text = title,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = TextWhite
    )
}

@Composable
private fun ExplanationCard(explanation: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = explanation,
            fontSize = 16.sp,
            color = TextWhite,
            modifier = Modifier.padding(16.dp),
            lineHeight = 24.sp
        )
    }
}

@Composable
private fun TipCard(tip: String) {
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
                text = "💡",
                fontSize = 20.sp
            )
            Text(
                text = tip,
                fontSize = 16.sp,
                color = TextWhite
            )
        }
    }
}
