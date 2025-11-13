package com.example.dialektapp.data.mappers

import com.example.dialektapp.data.remote.dto.ActivityContentDto
import com.example.dialektapp.data.remote.dto.ActivityDetailDto
import com.example.dialektapp.data.remote.dto.ExampleDto
import com.example.dialektapp.data.remote.dto.QuestionDto
import com.example.dialektapp.domain.model.ActivityContent
import com.example.dialektapp.domain.model.ActivityDetail
import com.example.dialektapp.domain.model.Example
import com.example.dialektapp.domain.model.Question

fun ActivityDetailDto.toDomain(): ActivityDetail {
    return ActivityDetail(
        activity = com.example.dialektapp.domain.model.LessonActivity(
            id = activityId.toString(),
            lessonId = "", // Може не знадобитись тут
            name = content.title ?: "",
            type = content.type.toActivityType(),
            duration = "",
            order = 0,
            isCompleted = false,
            isUnlocked = true
        ),
        content = content.toActivityContent()
    )
}

fun ActivityContentDto.toActivityContent(): ActivityContent {
    return when (type.uppercase()) {
        "INTRODUCTION" -> ActivityContent.Introduction(
            title = title ?: "",
            description = description ?: "",
            videoUrl = videoUrl,
            imageUrl = imageUrl,
            keyPoints = keyPoints ?: emptyList()
        )

        "READING" -> ActivityContent.Reading(
            title = title ?: "",
            text = text ?: "",
            audioUrl = audioUrl,
            translations = translations ?: emptyMap(),
            examples = examples?.map { it.toDomain() } ?: emptyList()
        )

        "EXPLAINING" -> ActivityContent.Explaining(
            title = title ?: "",
            explanation = explanation ?: "",
            audioUrl = audioUrl,
            examples = examples?.map { it.toDomain() } ?: emptyList(),
            tips = tips ?: emptyList()
        )

        "TEST" -> ActivityContent.Test(
            title = title ?: "",
            questions = questions?.map { it.toDomain() } ?: emptyList(),
            passingScore = passingScore ?: 70
        )

        else -> ActivityContent.Introduction(
            title = title ?: "",
            description = description ?: ""
        )
    }
}

fun ExampleDto.toDomain(): Example {
    return Example(
        dialectText = dialectText,
        literaryText = literaryText,
        translation = translation
    )
}

fun QuestionDto.toDomain(): Question {
    return when (type.uppercase()) {
        "MULTIPLE_CHOICE" -> Question.MultipleChoice(
            id = id,
            text = text,
            options = options ?: emptyList(),
            correctAnswer = correctAnswer,
            explanation = explanation
        )

        "TRUE_FALSE" -> Question.TrueFalse(
            id = id,
            text = text,
            correctAnswer = correctAnswer,
            explanation = explanation
        )

        "FILL_IN_THE_BLANK" -> Question.FillInTheBlank(
            id = id,
            text = text,
            correctAnswer = correctAnswer,
            hint = hint
        )

        else -> Question.MultipleChoice(
            id = id,
            text = text,
            options = emptyList(),
            correctAnswer = correctAnswer
        )
    }
}

private fun String.toActivityType(): com.example.dialektapp.domain.model.ActivityType {
    return when (this.uppercase()) {
        "INTRODUCTION" -> com.example.dialektapp.domain.model.ActivityType.INTRODUCTION
        "READING" -> com.example.dialektapp.domain.model.ActivityType.READING
        "EXPLAINING" -> com.example.dialektapp.domain.model.ActivityType.EXPLAINING
        "TEST" -> com.example.dialektapp.domain.model.ActivityType.TEST
        else -> com.example.dialektapp.domain.model.ActivityType.INTRODUCTION
    }
}
