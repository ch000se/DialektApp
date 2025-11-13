package com.example.dialektapp.presentation.screens.activity

import androidx.compose.foundation.layout.*
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
import com.example.dialektapp.domain.model.ActivityContent
import com.example.dialektapp.domain.model.ActivityDetail
import com.example.dialektapp.domain.model.ActivityType
import com.example.dialektapp.domain.model.LessonActivity
import com.example.dialektapp.presentation.screens.activity.components.explaining.ExplainingContent
import com.example.dialektapp.presentation.screens.activity.components.introduction.IntroductionContent
import com.example.dialektapp.presentation.screens.activity.components.reading.ReadingContent
import com.example.dialektapp.presentation.screens.activity.components.test.TestContent

private val BackgroundDark = Color(0xFF15161A)
private val CardBackground = Color(0xFF1F2025)
private val TextWhite = Color.White

// Тимчасовий UI State до підключення ViewModel
data class ActivityUiState(
    val activityDetail: ActivityDetail? = null,
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val isCompleted: Boolean = false,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityScreen(
    lessonId: String,
    activityId: String,
    onBackClick: () -> Unit,
    onActivityComplete: () -> Unit = {},
) {
    val context = LocalContext.current
    var isCompleted by remember { mutableStateOf(false) }

    // Тимчасові mock дані
    val mockActivityDetail = remember(activityId) {
        ActivityDetail(
            activity = LessonActivity(
                id = activityId,
                lessonId = lessonId,
                name = "Вступ до діалекту",
                type = ActivityType.INTRODUCTION,
                duration = "5 хв",
                order = 1,
                isCompleted = false,
                isUnlocked = true
            ),
            content = ActivityContent.Introduction(
                title = "Вітання та знайомство",
                description = "У цьому уроці ви навчитеся базовим фразам для вітання та знайомства",
                keyPoints = listOf(
                    "Базові вітання",
                    "Представлення себе",
                    "Вимова та наголоси"
                )
            )
        )
    }

    val uiState = ActivityUiState(
        activityDetail = mockActivityDetail,
        isLoading = false,
        error = null,
        isCompleted = isCompleted
    )

    LaunchedEffect(isCompleted) {
        if (isCompleted) {
            onActivityComplete()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = uiState.activityDetail?.activity?.name ?: "Активність",
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
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(32.dp)
                    ) {
                        Text(
                            text = "Помилка завантаження активності",
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

            uiState.activityDetail != null -> {
                val activityDetail = uiState.activityDetail

                when (activityDetail.content) {
                    is ActivityContent.Introduction -> {
                        IntroductionContent(
                            content = activityDetail.content,
                            onComplete = { isCompleted = true },
                            modifier = Modifier.padding(paddingValues)
                        )
                    }

                    is ActivityContent.Reading -> {
                        ReadingContent(
                            content = activityDetail.content,
                            onComplete = { isCompleted = true },
                            modifier = Modifier.padding(paddingValues)
                        )
                    }

                    is ActivityContent.Explaining -> {
                        ExplainingContent(
                            content = activityDetail.content,
                            onComplete = { isCompleted = true },
                            modifier = Modifier.padding(paddingValues)
                        )
                    }

                    is ActivityContent.Test -> {
                        TestContent(
                            content = activityDetail.content,
                            onComplete = { score -> isCompleted = true },
                            modifier = Modifier.padding(paddingValues)
                        )
                    }
                }
            }
        }
    }
}
