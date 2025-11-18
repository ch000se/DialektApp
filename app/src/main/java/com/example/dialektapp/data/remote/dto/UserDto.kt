package com.example.dialektapp.data.remote.dto

import com.google.gson.annotations.SerializedName

enum class UserRole {
    @SerializedName("user")
    USER,
    @SerializedName("admin")
    ADMIN,
    @SerializedName("manager")
    MANAGER
}

data class UserDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("username")
    val username: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("profile_image_url")
    val profileImageUrl: String?,
    @SerializedName("disabled")
    val disabled: Boolean,
    @SerializedName("role")
    val role: UserRole,
)