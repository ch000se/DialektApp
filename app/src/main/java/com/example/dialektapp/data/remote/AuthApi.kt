package com.example.dialektapp.data.remote

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
    @POST("/auth/register")
    suspend fun register(@Body body: RegisterRequest): Response<Unit>

    @FormUrlEncoded
    @POST("/auth/token")
    suspend fun login(
        @Field ("username") username: String,
        @Field ("password") password: String
    ): Response<LoginResponse>

    @GET("/users/me")
    suspend fun getCurrentUser(): Response<UserDto>
}