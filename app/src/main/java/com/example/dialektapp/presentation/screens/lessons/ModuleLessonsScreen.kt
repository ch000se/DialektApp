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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dialektapp.presentation.screens.lessons.components.*

private val BackgroundDark = Color(0xFF15161A)
private val CardBackground = Color(0xFF1F2025)
private val TextWhite = Color.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModuleLessonsScreen(
    moduleId: String,
    moduleTitle: String,
    moduleSubtitle: String = "Вітання та знайомство",
    moduleDescription: String = "Після цього модуля ви зможете вітатися, представляти себе та користуватися базовими формулами ввічливості.",
    onBackClick: () -> Unit,
    onActivityClick: (lessonId: String, activityId: String) -> Unit,
    viewModel: ModuleLessonsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

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
                            duration = "0h 0m" // TODO: додати duration в CourseModule
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