package com.example.dialektapp.presentation.screens.activity.components.test

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dialektapp.domain.model.ActivityContent
import com.example.dialektapp.domain.model.Question

private val TextWhite = Color.White
private val TextGray = Color(0xFFE0E0E0)
private val GreenAccent = Color(0xFFD1F501)

@Composable
fun TestContent(
    content: ActivityContent.Test,
    onComplete: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    var userAnswers by remember { mutableStateOf<Map<String, String>>(emptyMap()) }
    var showResults by remember { mutableStateOf(false) }
    var isAnswered by remember { mutableStateOf(false) }

    val currentQuestion = content.questions.getOrNull(currentQuestionIndex)
    val progress = (currentQuestionIndex + 1).toFloat() / content.questions.size.toFloat()

    if (showResults) {
        TestResultsScreen(
            content = content,
            userAnswers = userAnswers,
            onComplete = onComplete,
            onRetry = {
                currentQuestionIndex = 0
                userAnswers = emptyMap()
                showResults = false
                isAnswered = false
            },
            modifier = modifier
        )
    } else if (currentQuestion != null) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                TestProgressBar(
                    progress = progress,
                    currentQuestion = currentQuestionIndex + 1,
                    totalQuestions = content.questions.size
                )

                Spacer(modifier = Modifier.height(24.dp))

                when (currentQuestion) {
                    is Question.MultipleChoice -> {
                        MultipleChoiceQuestion(
                            question = currentQuestion,
                            selectedAnswer = userAnswers[currentQuestion.id],
                            onAnswerSelected = { answer ->
                                userAnswers = userAnswers + (currentQuestion.id to answer)
                                isAnswered = true
                            },
                            showExplanation = isAnswered && userAnswers[currentQuestion.id] != null
                        )
                    }

                    is Question.TrueFalse -> {
                        TrueFalseQuestion(
                            question = currentQuestion,
                            selectedAnswer = userAnswers[currentQuestion.id],
                            onAnswerSelected = { answer ->
                                userAnswers = userAnswers + (currentQuestion.id to answer)
                                isAnswered = true
                            },
                            showExplanation = isAnswered && userAnswers[currentQuestion.id] != null
                        )
                    }

                    is Question.FillInTheBlank -> {
                        FillInTheBlankQuestion(
                            question = currentQuestion,
                            userAnswer = userAnswers[currentQuestion.id] ?: "",
                            onAnswerChanged = { answer ->
                                userAnswers = userAnswers + (currentQuestion.id to answer)
                                isAnswered = answer.isNotBlank()
                            },
                            showExplanation = isAnswered && userAnswers[currentQuestion.id] != null
                        )
                    }
                }
            }

            TestNavigationButtons(
                currentQuestionIndex = currentQuestionIndex,
                totalQuestions = content.questions.size,
                hasAnswer = userAnswers.containsKey(currentQuestion.id),
                onPreviousClick = {
                    currentQuestionIndex--
                    isAnswered = userAnswers.containsKey(
                        content.questions[currentQuestionIndex].id
                    )
                },
                onNextClick = {
                    if (currentQuestionIndex < content.questions.size - 1) {
                        currentQuestionIndex++
                        isAnswered = userAnswers.containsKey(
                            content.questions[currentQuestionIndex].id
                        )
                    } else {
                        showResults = true
                    }
                }
            )
        }
    }
}

@Composable
private fun TestNavigationButtons(
    currentQuestionIndex: Int,
    totalQuestions: Int,
    hasAnswer: Boolean,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (currentQuestionIndex > 0) {
            OutlinedButton(
                onClick = onPreviousClick,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = TextWhite
                ),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    brush = androidx.compose.ui.graphics.SolidColor(GreenAccent)
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text("Назад", modifier = Modifier.padding(8.dp))
            }

            Spacer(modifier = Modifier.width(16.dp))
        }

        Button(
            onClick = onNextClick,
            enabled = hasAnswer,
            colors = ButtonDefaults.buttonColors(
                containerColor = GreenAccent,
                contentColor = Color.Black,
                disabledContainerColor = TextGray,
                disabledContentColor = Color.Black
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = if (currentQuestionIndex < totalQuestions - 1) "Далі" else "Завершити",
                modifier = Modifier.padding(8.dp),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
