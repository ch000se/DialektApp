package com.example.dialektapp.di

import android.content.Context
import com.example.dialektapp.data.local.TokenManager
import com.example.dialektapp.data.remote.api.*
import com.example.dialektapp.data.remote.network.TokenInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "http://31.222.235.7:9090/"

    @Provides
    @Singleton
    fun provideTokenManager(@ApplicationContext context: Context): TokenManager {
        return TokenManager(context)
    }

    @Provides
    @Singleton
    fun provideTokenInterceptor(tokenManager: TokenManager): TokenInterceptor {
        return TokenInterceptor(tokenManager)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(tokenInterceptor: TokenInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(tokenInterceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)

    @Provides
    @Singleton
    fun provideCoursesApi(retrofit: Retrofit): CoursesApi = retrofit.create(CoursesApi::class.java)

    @Provides
    @Singleton
    fun provideActivitiesApi(retrofit: Retrofit): ActivitiesApi =
        retrofit.create(ActivitiesApi::class.java)

    @Provides
    @Singleton
    fun provideStreakApi(retrofit: Retrofit): StreakApi =
        retrofit.create(StreakApi::class.java)

    @Provides
    @Singleton
    fun provideLeaderboardApi(retrofit: Retrofit): LeaderboardApi =
        retrofit.create(LeaderboardApi::class.java)

    @Provides
    @Singleton
    fun provideAchievementsApi(retrofit: Retrofit): AchievementsApi =
        retrofit.create(AchievementsApi::class.java)

    @Provides
    @Singleton
    fun provideStatsApi(retrofit: Retrofit): StatsApi =
        retrofit.create(StatsApi::class.java)
}
