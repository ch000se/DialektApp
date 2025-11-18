package com.example.dialektapp.data.remote.api

import com.example.dialektapp.data.remote.dto.LoginRequest
import com.example.dialektapp.data.remote.dto.LoginResponse
import com.example.dialektapp.data.remote.dto.RegisterRequest
import com.example.dialektapp.data.remote.dto.UserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {
    @FormUrlEncoded
    @POST("/auth/token")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Response<LoginResponse>

    @POST("/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<Unit>

    @GET("/users/me")
    suspend fun getCurrentUser(): Response<UserDto>
}
