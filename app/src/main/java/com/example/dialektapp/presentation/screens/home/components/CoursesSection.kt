package com.example.dialektapp.presentation.screens.home.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dialektapp.ui.theme.DialektAppTheme
import com.example.dialektapp.R
import com.example.dialektapp.domain.model.Course
import com.example.dialektapp.ui.theme.*

@Composable
fun CoursesSection(
    courses: List<Course>,
    isLoading: Boolean = false,
    onCourseClick: (String) -> Unit
) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isVisible = true
    }

    Column {
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(
                animationSpec = tween(800, delayMillis = 200)
            ) + slideInVertically(
                animationSpec = tween(800, delayMillis = 200),
                initialOffsetY = { -it / 2 }
            )
        ) {
            Text(
                text = "Курси діалектів",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = TextPrimaryLight,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = AccentBlue)
            }
        } else {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 4.dp)
            ) {
                itemsIndexed(courses, key = { _, course -> course.id }) { index, course ->
                    AnimatedVisibility(
                        visible = isVisible,
                        enter = fadeIn(
                            animationSpec = tween(600, delayMillis = 400 + index * 200)
                        ) + slideInHorizontally(
                            animationSpec = tween(600, delayMillis = 400 + index * 200),
                            initialOffsetX = { it / 2 }
                        ) + scaleIn(
                            animationSpec = tween(600, delayMillis = 400 + index * 200),
                            initialScale = 0.8f
                        )
                    ) {
                        CourseCard(
                            course = course,
                            onClick = { onCourseClick(course.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CourseCard(
    course: Course,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "cardScale"
    )

    val elevation by animateDpAsState(
        targetValue = if (isPressed) 4.dp else 12.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "cardElevation"
    )

    val imageRes = getCourseImageResource(course.imageUrl)

    Card(
        modifier = Modifier
            .width(240.dp)
            .height(160.dp)
            .scale(scale)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { onClick() },
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.7f)
                            ),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY
                        )
                    )
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Card(
                    modifier = Modifier.align(Alignment.TopEnd),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = HomeProgressBackground.copy(alpha = 0.95f)
                    )
                ) {
                    val progress = if (course.totalModules > 0) {
                        (course.completedModules.toFloat() / course.totalModules.toFloat() * 100).toInt()
                    } else 0

                    Text(
                        text = "$progress%",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = HomeProgressTextBlack,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp
                    )
                }

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = course.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = HomeCardTextWhite,
                        fontSize = 20.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${course.totalModules} модулів",
                            style = MaterialTheme.typography.bodySmall,
                            color = HomeCardTextWhite.copy(alpha = 0.9f),
                            fontSize = 12.sp
                        )

                        Text(
                            text = "${course.completedModules}/${course.totalModules}",
                            style = MaterialTheme.typography.bodySmall,
                            color = HomeCardTextWhite,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    val progress = if (course.totalModules > 0) {
                        course.completedModules.toFloat() / course.totalModules.toFloat()
                    } else 0f

                    val animatedProgress by animateFloatAsState(
                        targetValue = progress,
                        animationSpec = tween(
                            durationMillis = 1000,
                            delayMillis = 600,
                            easing = EaseOutQuart
                        ),
                        label = "progress"
                    )

                    LinearProgressIndicator(
                        progress = { animatedProgress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(4.dp)
                            .clip(RoundedCornerShape(2.dp)),
                        color = HomeCardTextWhite,
                        trackColor = HomeCardTextWhite.copy(alpha = 0.3f)
                    )
                }
            }
        }
    }
}

@DrawableRes
private fun getCourseImageResource(imageUrl: String): Int {
    return when (imageUrl) {
        "zakarpatya" -> R.drawable.zakarpatya
        "galychyna" -> R.drawable.galychyna
        "kuban" -> R.drawable.kuban
        else -> R.drawable.background
    }
}

@Preview(showBackground = true)
@Composable
private fun CourseSectionPreview() {
    DialektAppTheme {
        CoursesSection(
            courses = listOf(
                Course(
                    id = "transcarpathian",
                    name = "Закарпатський",
                    description = "",
                    imageUrl = "zakarpatya",
                    imageUrlBack = "zakarpatya_back",
                    totalModules = 12,
                    completedModules = 5
                ),
                Course(
                    id = "galician",
                    name = "Галицький",
                    description = "",
                    imageUrl = "galychyna",
                    imageUrlBack = "galychyna_back",
                    totalModules = 10,
                    completedModules = 7
                ),
                Course(
                    id = "kuban",
                    name = "Кубанський",
                    description = "",
                    imageUrl = "kuban",
                    imageUrlBack = "kuban_back",
                    totalModules = 8,
                    completedModules = 3
                )
            ),
            isLoading = false,
            onCourseClick = {}
        )
    }
}