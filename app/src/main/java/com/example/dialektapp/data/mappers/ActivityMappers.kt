package com.example.dialektapp.data.mappers

import com.example.dialektapp.data.remote.dto.ActivityContentDto
import com.example.dialektapp.data.remote.dto.ActivityDetailDto
import com.example.dialektapp.data.remote.dto.ActivityType
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
            lessonId = "", // Може не знадобитись тут(?)
            name = content.title ?: "",
            type = content.type.toDomainActivityType(),
            duration = "",
            order = 0,
            isCompleted = false,
            isUnlocked = true
        ),
        content = content.toActivityContent()
    )
}

fun ActivityContentDto.toActivityContent(): ActivityContent {
    return when (type) {
        ActivityType.INTRODUCTION -> ActivityContent.Introduction(
            title = title ?: "",
            description = description ?: "",
            videoUrl = videoUrl,
            imageUrl = imageUrl,
            keyPoints = keyPoints ?: emptyList()
        )

        ActivityType.READING -> ActivityContent.Reading(
            title = title ?: "",
            text = text ?: "",
            audioUrl = audioUrl,
            translations = translations ?: emptyMap(),
            examples = examples?.map { it.toDomain() } ?: emptyList()
        )

        ActivityType.EXPLAINING -> ActivityContent.Explaining(
            title = title ?: "",
            explanation = explanation ?: "",
            audioUrl = audioUrl,
            examples = examples?.map { it.toDomain() } ?: emptyList(),
            tips = tips ?: emptyList()
        )

        ActivityType.TEST -> ActivityContent.Test(
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
    // Backend використовує "kind" з форматом "TrueFalse", "MultipleChoice", etc.
    // Беремо kind, якщо є, інакше type
    val questionType = (kind ?: type ?: "MultipleChoice").uppercase()

    // Маппінг з backend формату в app формат
    val normalizedType = when {
        questionType.contains("TRUE") || questionType.contains("FALSE") -> "TRUE_FALSE"
        questionType.contains("MULTIPLE") || questionType.contains("CHOICE") -> "MULTIPLE_CHOICE"
        questionType.contains("FILL") || questionType.contains("BLANK") -> "FILL_IN_THE_BLANK"
        else -> "MULTIPLE_CHOICE"
    }

    return when (normalizedType) {
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

private fun ActivityType.toDomainActivityType(): com.example.dialektapp.domain.model.ActivityType {
    return when (this) {
        ActivityType.INTRODUCTION -> com.example.dialektapp.domain.model.ActivityType.INTRODUCTION
        ActivityType.READING -> com.example.dialektapp.domain.model.ActivityType.READING
        ActivityType.EXPLAINING -> com.example.dialektapp.domain.model.ActivityType.EXPLAINING
        ActivityType.TEST -> com.example.dialektapp.domain.model.ActivityType.TEST
    }
}
