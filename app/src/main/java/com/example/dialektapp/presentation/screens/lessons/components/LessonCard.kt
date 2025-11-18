package com.example.dialektapp.presentation.screens.lessons.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dialektapp.domain.model.ActivityType
import com.example.dialektapp.domain.model.Lesson
import com.example.dialektapp.domain.model.LessonActivity

private val CardBackground = Color(0xFF1F2025)
private val TextWhite = Color.White
private val TextGray = Color(0xFFE0E0E0)
private val GreenAccent = Color(0xFFD1F501)
private val GrayAccent = Color(0xFF969699)

@Composable
fun LessonCard(
    lesson: Lesson,
    isExpanded: Boolean,
    onCardClick: () -> Unit,
    onActivityClick: (String) -> Unit,
) {
    val borderBrush = if (lesson.isUnlocked) {
        Brush.linearGradient(
            colors = listOf(GreenAccent, GreenAccent)
        )
    } else {
        Brush.linearGradient(
            colors = listOf(GrayAccent, GrayAccent)
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                brush = borderBrush,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { onCardClick() },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = if (lesson.isUnlocked) Icons.Default.LockOpen else Icons.Default.Lock,
                        contentDescription = if (lesson.isUnlocked) "Unlocked" else "Locked",
                        tint = if (lesson.isUnlocked) GreenAccent else GrayAccent,
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Column {
                        Text(
                            text = lesson.title,
                            style = MaterialTheme.typography.titleMedium,
                            color = TextWhite,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )

                        if (lesson.description.isNotEmpty()) {
                            Text(
                                text = lesson.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = TextGray,
                                fontSize = 14.sp
                            )
                        }
                    }
                }

                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                    tint = TextGray,
                    modifier = Modifier.size(24.dp)
                )
            }

            if (isExpanded && lesson.isUnlocked && lesson.activities.isNotEmpty()) {
                Spacer(modifier = Modifier.height(30.dp))

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Колонка з кружками та лініями
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.width(24.dp)
                    ) {
                        lesson.activities.forEachIndexed { index, activity ->
                            // Визначаємо чи це поточна активність (наступна після завершених)
                            val isCurrentActivity = !activity.isCompleted &&
                                    (index == 0 || lesson.activities[index - 1].isCompleted)

                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .clip(CircleShape)
                                    .background(CardBackground)
                                    .border(
                                        width = 2.dp,
                                        color = when {
                                            activity.isCompleted -> GreenAccent
                                            isCurrentActivity -> GreenAccent
                                            else -> GrayAccent
                                        },
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                when {
                                    activity.isCompleted -> {
                                        Icon(
                                            Icons.Default.Check,
                                            contentDescription = "Completed",
                                            tint = GreenAccent,
                                            modifier = Modifier.size(12.dp)
                                        )
                                    }
                                    isCurrentActivity -> {
                                        // Зелений заповнений кружок для поточної активності
                                        Box(
                                            modifier = Modifier
                                                .size(10.dp)
                                                .clip(CircleShape)
                                                .background(GreenAccent)
                                        )
                                    }

                                    else -> {
                                        // Сірий кружок для майбутніх активностей
                                        Box(
                                            modifier = Modifier
                                                .size(10.dp)
                                                .clip(CircleShape)
                                                .background(GrayAccent)
                                        )
                                    }
                                }
                            }

                            // Лінія до наступної активності (між кружками)
                            if (index < lesson.activities.size - 1) {
                                Box(
                                    modifier = Modifier
                                        .width(2.dp)
                                        .height(30.dp)
                                        .background(
                                            if (activity.isCompleted)
                                                GreenAccent
                                            else
                                                GrayAccent
                                        )
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(20.dp))

                    // Колонка з текстом активностей
                    Column(
                        verticalArrangement = Arrangement.spacedBy(30.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        lesson.activities.forEach { activity ->
                            ActivityItemExpanded(
                                activity = activity,
                                onClick = { onActivityClick(activity.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ActivityItemExpanded(
    activity: LessonActivity,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (activity.isUnlocked) {
                    Modifier.clickable { onClick() }
                } else {
                    Modifier
                }
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            if (!activity.isUnlocked) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Locked",
                    tint = GrayAccent,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }

            Text(
                text = activity.name,
                style = MaterialTheme.typography.bodyLarge,
                color = if (activity.isUnlocked) TextWhite else GrayAccent,
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp,
                letterSpacing = 0.4.sp
            )
        }

        Text(
            text = activity.duration,
            style = MaterialTheme.typography.bodyMedium,
            color = if (activity.isUnlocked) TextWhite else GrayAccent,
            fontWeight = FontWeight.Light,
            fontSize = 18.sp
        )
    }
}