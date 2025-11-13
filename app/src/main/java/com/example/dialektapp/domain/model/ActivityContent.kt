package com.example.dialektapp.domain.model

sealed class ActivityContent {
    data class Introduction(
        val title: String,
        val description: String,
        val videoUrl: String? = null,
        val imageUrl: String? = null,
        val keyPoints: List<String> = emptyList()
    ) : ActivityContent()

    data class Reading(
        val title: String,
        val text: String,
        val audioUrl: String? = null,
        val translations: Map<String, String> = emptyMap(),
        val examples: List<Example> = emptyList()
    ) : ActivityContent()

    data class Explaining(
        val title: String,
        val explanation: String,
        val audioUrl: String? = null,
        val examples: List<Example> = emptyList(),
        val tips: List<String> = emptyList()
    ) : ActivityContent()

    data class Test(
        val title: String,
        val questions: List<Question> = emptyList(),
        val passingScore: Int = 70
    ) : ActivityContent()
}

data class Example(
    val dialectText: String,
    val literaryText: String,
    val translation: String? = null
)

sealed class Question {
    abstract val id: String
    abstract val text: String
    abstract val correctAnswer: String

    data class MultipleChoice(
        override val id: String,
        override val text: String,
        val options: List<String>,
        override val correctAnswer: String,
        val explanation: String? = null
    ) : Question()

    data class TrueFalse(
        override val id: String,
        override val text: String,
        override val correctAnswer: String,
        val explanation: String? = null
    ) : Question()

    data class FillInTheBlank(
        override val id: String,
        override val text: String,
        override val correctAnswer: String,
        val hint: String? = null
    ) : Question()
}

data class ActivityDetail(
    val activity: LessonActivity,
    val content: ActivityContent
)
