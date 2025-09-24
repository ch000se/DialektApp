package com.example.dialektapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class TokenDto(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("token_type")
    val tokenType: String
)