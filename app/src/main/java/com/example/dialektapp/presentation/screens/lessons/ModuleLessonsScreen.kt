package com.example.dialektapp.presentation.screens.lessons

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.dialektapp.presentation.screens.lessons.components.*
import kotlinx.coroutines.launch

private val BackgroundDark = Color(0xFF15161A)
private val CardBackground = Color(0xFF1F2025)
private val TextWhite = Color.White

/**
 * Розраховує загальний час з усіх активностей модуля
 * @param lessons список уроків модуля
 * @return відформатований рядок типу "0h 15m"
 */
private fun calculateTotalDuration(lessons: List<com.example.dialektapp.domain.model.Lesson>): String {
    var totalMinutes = 0

    lessons.forEach { lesson ->
        lesson.activities.forEach { activity ->
            // Парсимо duration типу "5:00" або "2:00"
            val parts = activity.duration.split(":")
            if (parts.size == 2) {
                val minutes = parts[0].toIntOrNull() ?: 0
                val seconds = parts[1].toIntOrNull() ?: 0
                totalMinutes += minutes
                // Округлюємо секунди до хвилин якщо >= 30 секунд
                if (seconds >= 30) {
                    totalMinutes += 1
                }
            }
        }
    }

    val hours = totalMinutes / 60
    val minutes = totalMinutes % 60

    return if (hours > 0) {
        "${hours}h ${minutes}m"
    } else {
        "${minutes}m"
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModuleLessonsScreen(
    courseId: String,
    moduleId: String,
    moduleTitle: String,
    moduleSubtitle: String = "Вітання та знайомство",
    moduleDescription: String = "Після цього модуля ви зможете вітатися, представляти себе та користуватися базовими формулами ввічливості.",
    onBackClick: () -> Unit,
    onActivityClick: (lessonId: String, activityId: String) -> Unit,
    viewModel: ModuleLessonsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // Перезавантажуємо дані кожен раз коли повертаємось на цей екран
    LaunchedEffect(Unit) {
        viewModel.refresh()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = uiState.module?.title ?: moduleTitle,
                        color = TextWhite,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = TextWhite
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CardBackground
                )
            )
        },
        containerColor = BackgroundDark
    ) { paddingValues ->
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color(0xFFD1F501))
                }
            }

            uiState.error != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Помилка завантаження уроків",
                            color = TextWhite,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Button(
                            onClick = { viewModel.retry() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFD1F501),
                                contentColor = Color.Black
                            )
                        ) {
                            Text("Спробувати знову")
                        }
                    }
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    item {
                        Spacer(modifier = Modifier.height(10.dp))
                        ModuleInfoCard(
                            title = uiState.module?.title ?: moduleTitle,
                            subtitle = uiState.module?.subtitle ?: moduleSubtitle,
                            description = uiState.module?.description ?: moduleDescription,
                            duration = calculateTotalDuration(uiState.lessons)
                        )
                    }

                    items(uiState.lessons, key = { it.id }) { lesson ->
                        LessonCard(
                            lesson = lesson,
                            isExpanded = uiState.expandedLessonId == lesson.id,
                            onCardClick = {
                                viewModel.toggleLessonExpansion(lesson.id)
                            },
                            onActivityClick = { activityId ->
                                onActivityClick(
                                    lesson.id,
                                    activityId
                                )
                            }
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        }
    }
}