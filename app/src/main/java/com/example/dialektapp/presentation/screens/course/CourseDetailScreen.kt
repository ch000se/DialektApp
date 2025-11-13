package com.example.dialektapp.presentation.screens.course

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.dialektapp.R
import com.example.dialektapp.domain.model.Course
import com.example.dialektapp.domain.model.CourseModule
import com.example.dialektapp.presentation.screens.course.components.*
import com.example.dialektapp.ui.theme.*

@DrawableRes
private fun getCourseBackgroundImage(courseId: String): Int = when (courseId) {
    "transcarpathian" -> R.drawable.zakarpatya_back
    "galician" -> R.drawable.galychyna_back
    "kuban" -> R.drawable.kuban_back
    else -> R.drawable.background
}

// Тимчасовий UI State до підключення ViewModel
data class CourseDetailState(
    val course: Course? = null,
    val isLoading: Boolean = false,
    val error: Throwable? = null,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseDetailScreen(
    courseId: String,
    courseName: String,
    onBackClick: () -> Unit,
    onModuleClick: (String) -> Unit,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    val context = LocalContext.current
    var expandedModuleId by remember { mutableStateOf("") }

    // Тимчасові mock дані
    val mockCourse = remember(courseId) {
        Course(
            id = courseId,
            name = courseName,
            description = "Опис курсу",
            imageUrl = "",
            imageUrlBack = "",
            totalModules = 3,
            completedModules = 0,
            modules = listOf(
                CourseModule(
                    id = "module1",
                    courseId = courseId,
                    title = "Модуль 1",
                    subtitle = "Вітання та знайомство",
                    description = "Базові фрази для вітання",
                    isUnlocked = true,
                    isCompleted = false,
                    progress = 0,
                    lessons = emptyList()
                ),
                CourseModule(
                    id = "module2",
                    courseId = courseId,
                    title = "Модуль 2",
                    subtitle = "Розмова про погоду",
                    description = "Вивчення погодних термінів",
                    isUnlocked = false,
                    isCompleted = false,
                    progress = 0,
                    lessons = emptyList()
                ),
                CourseModule(
                    id = "module3",
                    courseId = courseId,
                    title = "Модуль 3",
                    subtitle = "У магазині",
                    description = "Практичні фрази для покупок",
                    isUnlocked = false,
                    isCompleted = false,
                    progress = 0,
                    lessons = emptyList()
                )
            )
        )
    }

    val state = CourseDetailState(
        course = mockCourse,
        isLoading = false,
        error = null
    )

    val modules = state.course?.modules ?: emptyList()

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = getCourseBackgroundImage(courseId)),
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            BackColor.copy(alpha = 0.7f),
                            BackColor.copy(alpha = 0.9f)
                        )
                    )
                )
        )

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = state.course?.name ?: courseName,
                            color = TextPrimaryDark,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = TextPrimaryDark
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            },
            snackbarHost = { SnackbarHost(snackbarHostState) },
            containerColor = Color.Transparent
        ) { paddingValues ->
            Box(modifier = Modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    itemsIndexed(modules) { index, module ->
                        ModuleCard(
                            module = module,
                            isExpanded = expandedModuleId == module.id,
                            onCardClick = {
                                expandedModuleId =
                                    if (expandedModuleId == module.id) "" else module.id
                            },
                            onLessonClick = {
                                onModuleClick(module.id)
                            }
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }

                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = Primary
                    )
                }
            }
        }
    }
}