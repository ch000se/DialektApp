package com.example.dialektapp.presentation.screens.activity.components.test

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dialektapp.domain.model.ActivityContent
import com.example.dialektapp.domain.model.Question

private val TextWhite = Color.White
private val TextGray = Color(0xFFE0E0E0)
private val GreenAccent = Color(0xFFD1F501)
private val RedAccent = Color(0xFFFF5252)

@Composable
fun TestResultsScreen(
    content: ActivityContent.Test,
    userAnswers: Map<String, String>,
    onComplete: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val correctAnswers = content.questions.count { question ->
        val userAnswer = userAnswers[question.id] ?: ""
        when (question) {
            is Question.MultipleChoice -> userAnswer == question.correctAnswer
            is Question.TrueFalse -> userAnswer == question.correctAnswer
            is Question.FillInTheBlank -> userAnswer.trim()
                .equals(question.correctAnswer.trim(), ignoreCase = true)
        }
    }

    val score = (correctAnswers.toFloat() / content.questions.size.toFloat() * 100).toInt()
    val passed = score >= content.passingScore

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            TestResultsHeader(
                score = score,
                passed = passed,
                correctAnswers = correctAnswers,
                totalQuestions = content.questions.size,
                passingScore = content.passingScore
            )
        }

        item {
            Text(
                text = "Детальні результати:",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextWhite
            )
        }

        items(content.questions) { question ->
            val userAnswer = userAnswers[question.id] ?: ""
            val isCorrect = when (question) {
                is Question.MultipleChoice -> userAnswer == question.correctAnswer
                is Question.TrueFalse -> userAnswer == question.correctAnswer
                is Question.FillInTheBlank -> userAnswer.trim()
                    .equals(question.correctAnswer.trim(), ignoreCase = true)
            }

            QuestionResultCard(
                question = question,
                userAnswer = userAnswer,
                isCorrect = isCorrect
            )
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = { onComplete(score) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (passed) GreenAccent else RedAccent,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = if (passed) "Завершити тест" else "Повторити спробу",
                    modifier = Modifier.padding(8.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun TestResultsHeader(
    score: Int,
    passed: Boolean,
    correctAnswers: Int,
    totalQuestions: Int,
    passingScore: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (passed) GreenAccent.copy(alpha = 0.2f) else RedAccent.copy(
                alpha = 0.2f
            )
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = if (passed) "🎉" else "😔",
                fontSize = 48.sp
            )
            Text(
                text = if (passed) "Вітаємо!" else "Спробуйте ще раз",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = TextWhite
            )
            Text(
                text = "Ваш результат",
                fontSize = 16.sp,
                color = TextGray
            )
            Text(
                text = "$score%",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = if (passed) GreenAccent else RedAccent
            )
            Text(
                text = "Правильних відповідей: $correctAnswers з $totalQuestions",
                fontSize = 14.sp,
                color = TextWhite
            )
            if (!passed) {
                Text(
                    text = "Необхідно набрати мінімум $passingScore% для проходження",
                    fontSize = 12.sp,
                    color = TextGray,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun QuestionResultCard(
    question: Question,
    userAnswer: String,
    isCorrect: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isCorrect)
                GreenAccent.copy(alpha = 0.1f)
            else
                RedAccent.copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = if (isCorrect) Icons.Default.Check else Icons.Default.Close,
                    contentDescription = null,
                    tint = if (isCorrect) GreenAccent else RedAccent,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = question.text,
                    fontSize = 14.sp,
                    color = TextWhite,
                    fontWeight = FontWeight.Medium
                )
            }

            if (userAnswer.isNotBlank()) {
                Text(
                    text = "Ваша відповідь: $userAnswer",
                    fontSize = 12.sp,
                    color = if (isCorrect) GreenAccent else RedAccent
                )
            }

            if (!isCorrect) {
                Text(
                    text = "Правильна відповідь: ${question.correctAnswer}",
                    fontSize = 12.sp,
                    color = TextWhite
                )
            }
        }
    }
}
