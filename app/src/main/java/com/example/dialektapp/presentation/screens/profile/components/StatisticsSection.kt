package com.example.dialektapp.presentation.screens.profile.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class StatData(
    val title: String,
    val value: String,
    val icon: String? = null,
    val iconVector: ImageVector? = null,
    val color: Color,
)

@Composable
fun StatisticsSection(
    statistics: List<StatData>,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        statistics.forEach { stat ->
            StatCard(
                title = stat.title,
                value = stat.value,
                icon = stat.icon,
                iconVector = stat.iconVector,
                color = stat.color,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    icon: String?,
    iconVector: ImageVector?,
    color: Color,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (iconVector != null) {
                Icon(
                    imageVector = iconVector,
                    contentDescription = title,
                    tint = color,
                    modifier = Modifier.size(28.dp)
                )
            } else if (icon != null) {
                Text(
                    text = icon,
                    fontSize = 28.sp
                )
            }

            Text(
                text = value,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )

            Text(
                text = title,
                fontSize = 11.sp,
                color = Color(0xFF666666),
                maxLines = 1
            )
        }
    }
}