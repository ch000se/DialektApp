package com.example.dialektapp.data.remote.dto

import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName
import com.example.dialektapp.data.remote.dto.ActivityType

data class ActivityDetailDto(
    @SerializedName("activity_id")
    val activityId: Int,
    @SerializedName("content")
    val content: ActivityContentDto
)

data class ActivityContentDto(
    @SerializedName("type")
    val type: ActivityType,
    @SerializedName("title")
    val title: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("videoUrl")
    val videoUrl: String?,
    @SerializedName("imageUrl")
    val imageUrl: String?,
    @SerializedName("keyPoints")
    val keyPoints: List<String>?,
    // For READING
    @SerializedName("text")
    val text: String?,
    @SerializedName("audioUrl")
    val audioUrl: String?,
    @SerializedName("translations")
    val translations: Map<String, String>?,
    @SerializedName("examples")
    val examples: List<ExampleDto>?,
    // For EXPLAINING
    @SerializedName("explanation")
    val explanation: String?,
    @SerializedName("tips")
    val tips: List<String>?,
    // For TEST
    @SerializedName("questions")
    val questions: List<QuestionDto>?,
    @SerializedName("passingScore")
    val passingScore: Int?
)

data class ExampleDto(
    @SerializedName("dialectText")
    val dialectText: String,
    @SerializedName("literaryText")
    val literaryText: String,
    @SerializedName("translation")
    val translation: String?
)

data class QuestionDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("kind")
    val kind: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("text")
    val text: String,
    @SerializedName("options")
    val options: List<String>?,
    @SerializedName("correctAnswer")
    val correctAnswer: String,
    @SerializedName("explanation")
    val explanation: String?,
    @SerializedName("hint")
    val hint: String?
)
