package com.example.dialektapp.presentation.screens.activity.components.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dialektapp.domain.model.Example

private val CardBackground = Color(0xFF1F2025)
private val TextWhite = Color.White
private val TextGray = Color(0xFFE0E0E0)
private val GreenAccent = Color(0xFFD1F501)

@Composable
fun ExampleCard(example: Example) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = example.dialectText,
                fontSize = 16.sp,
                color = GreenAccent,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "→ ${example.literaryText}",
                fontSize = 14.sp,
                color = TextGray
            )
            example.translation?.let {
                Text(
                    text = it,
                    fontSize = 14.sp,
                    color = TextWhite.copy(alpha = 0.8f)
                )
            }
        }
    }
}
