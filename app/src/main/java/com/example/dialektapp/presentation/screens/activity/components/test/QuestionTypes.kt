package com.example.dialektapp.presentation.screens.activity.components.test

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dialektapp.domain.model.Question

private val CardBackground = Color(0xFF1F2025)
private val TextWhite = Color.White
private val TextGray = Color(0xFFE0E0E0)
private val GreenAccent = Color(0xFFD1F501)
private val RedAccent = Color(0xFFFF5252)
private val YellowAccent = Color(0xFFFFC107)

@Composable
fun MultipleChoiceQuestion(
    question: Question.MultipleChoice,
    selectedAnswer: String?,
    onAnswerSelected: (String) -> Unit,
    showExplanation: Boolean
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        QuestionText(text = question.text)

        question.options.forEach { option ->
            val isSelected = selectedAnswer == option
            val isCorrect = option == question.correctAnswer
            val showResult = showExplanation

            AnswerOption(
                text = option,
                isSelected = isSelected,
                isCorrect = if (showResult) isCorrect else null,
                onClick = { if (!showExplanation) onAnswerSelected(option) }
            )
        }

        if (showExplanation && question.explanation != null) {
            ExplanationCard(
                explanation = question.explanation,
                isCorrect = selectedAnswer == question.correctAnswer
            )
        }
    }
}

@Composable
fun TrueFalseQuestion(
    question: Question.TrueFalse,
    selectedAnswer: String?,
    onAnswerSelected: (String) -> Unit,
    showExplanation: Boolean
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        QuestionText(text = question.text)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            listOf("true" to "Так", "false" to "Ні").forEach { (value, label) ->
                val isSelected = selectedAnswer == value
                val isCorrect = value == question.correctAnswer
                val showResult = showExplanation

                Box(modifier = Modifier.weight(1f)) {
                    AnswerOption(
                        text = label,
                        isSelected = isSelected,
                        isCorrect = if (showResult) isCorrect else null,
                        onClick = { if (!showExplanation) onAnswerSelected(value) }
                    )
                }
            }
        }

        if (showExplanation && question.explanation != null) {
            ExplanationCard(
                explanation = question.explanation,
                isCorrect = selectedAnswer == question.correctAnswer
            )
        }
    }
}

@Composable
fun FillInTheBlankQuestion(
    question: Question.FillInTheBlank,
    userAnswer: String,
    onAnswerChanged: (String) -> Unit,
    showExplanation: Boolean
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        QuestionText(text = question.text)

        if (question.hint != null && !showExplanation) {
            HintCard(hint = question.hint)
        }

        OutlinedTextField(
            value = userAnswer,
            onValueChange = onAnswerChanged,
            modifier = Modifier.fillMaxWidth(),
            enabled = !showExplanation,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = TextWhite,
                unfocusedTextColor = TextWhite,
                disabledTextColor = TextWhite,
                focusedBorderColor = GreenAccent,
                unfocusedBorderColor = TextGray,
                cursorColor = GreenAccent
            ),
            shape = RoundedCornerShape(12.dp),
            placeholder = {
                Text("Введіть відповідь...", color = TextGray)
            },
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(fontSize = 16.sp)
        )

        if (showExplanation) {
            val isCorrect =
                userAnswer.trim().equals(question.correctAnswer.trim(), ignoreCase = true)

            FillInTheBlankResult(
                isCorrect = isCorrect,
                correctAnswer = question.correctAnswer
            )
        }
    }
}

@Composable
private fun QuestionText(text: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            color = TextWhite,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(20.dp),
            lineHeight = 26.sp
        )
    }
}

@Composable
private fun HintCard(hint: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = YellowAccent.copy(alpha = 0.1f)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "💡", fontSize = 16.sp)
            Text(
                text = hint,
                fontSize = 14.sp,
                color = YellowAccent,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun ExplanationCard(
    explanation: String,
    isCorrect: Boolean
) {
    AnimatedVisibility(
        visible = true,
        enter = fadeIn() + expandVertically()
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
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = if (isCorrect) Icons.Default.Check else Icons.Default.Close,
                    contentDescription = null,
                    tint = if (isCorrect) GreenAccent else RedAccent
                )
                Text(
                    text = explanation,
                    fontSize = 14.sp,
                    color = TextWhite,
                    lineHeight = 20.sp
                )
            }
        }
    }
}

@Composable
private fun FillInTheBlankResult(
    isCorrect: Boolean,
    correctAnswer: String
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
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = if (isCorrect) Icons.Default.Check else Icons.Default.Close,
                    contentDescription = null,
                    tint = if (isCorrect) GreenAccent else RedAccent
                )
                Text(
                    text = if (isCorrect) "Правильно!" else "Неправильно",
                    fontSize = 16.sp,
                    color = TextWhite,
                    fontWeight = FontWeight.Bold
                )
            }
            if (!isCorrect) {
                Text(
                    text = "Правильна відповідь: $correctAnswer",
                    fontSize = 14.sp,
                    color = TextWhite
                )
            }
        }
    }
}
