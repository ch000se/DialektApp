package com.example.dialektapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ActivityProgressRequest(
    @SerializedName("status")
    val status: String?, // "locked", "in_progress", "completed"
    @SerializedName("is_unlocked")
    val isUnlocked: Boolean?,
    @SerializedName("score")
    val score: Int?,
    @SerializedName("add_attempt")
    val addAttempt: Boolean?
)
