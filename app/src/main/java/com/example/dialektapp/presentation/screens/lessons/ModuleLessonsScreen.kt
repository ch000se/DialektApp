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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.dialektapp.domain.model.ActivityType
import com.example.dialektapp.domain.model.Lesson
import com.example.dialektapp.domain.model.LessonActivity
import com.example.dialektapp.presentation.screens.lessons.components.*

private val BackgroundDark = Color(0xFF15161A)
private val CardBackground = Color(0xFF1F2025)
private val TextWhite = Color.White

 // Тимчасовий UI State до підключення ViewModel
 data class ModuleLessonsUiState(
     val lessons: List<Lesson> = emptyList(),
     val moduleTitle: String = "",
     val moduleSubtitle: String = "",
     val moduleDescription: String = "",
     val moduleDuration: String = "",
     val expandedLessonId: String? = null,
     val isLoading: Boolean = false,
     val error: Throwable? = null,
 )

 @OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModuleLessonsScreen(
    moduleId: String,
    moduleTitle: String,
    moduleSubtitle: String = "Вітання та знайомство",
    moduleDescription: String = "Після цього модуля ви зможете вітатися, представляти себе та користуватися базовими формулами ввічливості.",
    onBackClick: () -> Unit,
    onActivityClick: (String) -> Unit,
) {
    val context = LocalContext.current
    var expandedLessonId by remember { mutableStateOf<String?>(null) }

    // Тимчасові mock дані
    val mockLessons = remember(moduleId) {
        listOf(
            Lesson(
                id = "lesson1",
                moduleId = moduleId,
                title = "Урок 1: Базові вітання",
                description = "Навчитеся вітатися українською",
                isUnlocked = true,
                isCompleted = false,
                totalActivities = 4,
                completedActivities = 0,
                activities = listOf(
                    LessonActivity(
                        id = "act1",
                        lessonId = "lesson1",
                        name = "Вступ",
                        type = ActivityType.INTRODUCTION,
                        duration = "5 хв",
                        order = 1,
                        isUnlocked = true,
                        isCompleted = false
                    ),
                    LessonActivity(
                        id = "act2",
                        lessonId = "lesson1",
                        name = "Читання",
                        type = ActivityType.READING,
                        duration = "10 хв",
                        order = 2,
                        isUnlocked = true,
                        isCompleted = false
                    ),
                    LessonActivity(
                        id = "act3",
                        lessonId = "lesson1",
                        name = "Пояснення",
                        type = ActivityType.EXPLAINING,
                        duration = "8 хв",
                        order = 3,
                        isUnlocked = false,
                        isCompleted = false
                    ),
                    LessonActivity(
                        id = "act4",
                        lessonId = "lesson1",
                        name = "Тест",
                        type = ActivityType.TEST,
                        duration = "15 хв",
                        order = 4,
                        isUnlocked = false,
                        isCompleted = false
                    )
                )
            ),
            Lesson(
                id = "lesson2",
                moduleId = moduleId,
                title = "Урок 2: Знайомство",
                description = "Як представитися іншим людям",
                isUnlocked = false,
                isCompleted = false,
                totalActivities = 4,
                completedActivities = 0,
                activities = emptyList()
            ),
            Lesson(
                id = "lesson3",
                moduleId = moduleId,
                title = "Урок 3: Формули ввічливості",
                description = "Вчимося бути ввічливими",
                isUnlocked = false,
                isCompleted = false,
                totalActivities = 3,
                completedActivities = 0,
                activities = emptyList()
            )
        )
    }

    val uiState = ModuleLessonsUiState(
        lessons = mockLessons,
        moduleTitle = moduleTitle,
        moduleSubtitle = moduleSubtitle,
        moduleDescription = moduleDescription,
        moduleDuration = "2h 30m",
        expandedLessonId = expandedLessonId,
        isLoading = false,
        error = null
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = uiState.moduleTitle.ifEmpty { moduleTitle },
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
                            onClick = { /* retry */ },
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
                            title = uiState.moduleTitle.ifEmpty { moduleTitle },
                            subtitle = uiState.moduleSubtitle.ifEmpty { moduleSubtitle },
                            description = uiState.moduleDescription.ifEmpty { moduleDescription },
                            duration = uiState.moduleDuration.ifEmpty { "0h 0m" }
                        )
                    }

                    items(uiState.lessons, key = { it.id }) { lesson ->
                        LessonCard(
                            lesson = lesson,
                            isExpanded = uiState.expandedLessonId == lesson.id,
                            onCardClick = {
                                expandedLessonId =
                                    if (expandedLessonId == lesson.id) null else lesson.id
                            },
                            onActivityClick = onActivityClick
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