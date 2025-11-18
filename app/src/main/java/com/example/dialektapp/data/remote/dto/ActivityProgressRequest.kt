package com.example.dialektapp.data.remote.dto

import com.google.gson.annotations.SerializedName

enum class ActivityStatus {
    @SerializedName("locked")
    LOCKED,
    @SerializedName("in_progress")
    IN_PROGRESS,
    @SerializedName("completed")
    COMPLETED
}

data class ActivityProgressRequest(
    @SerializedName("status")
    val status: ActivityStatus?,
    @SerializedName("is_unlocked")
    val isUnlocked: Boolean?,
    @SerializedName("score")
    val score: Int?,
    @SerializedName("add_attempt")
    val addAttempt: Boolean?
)
