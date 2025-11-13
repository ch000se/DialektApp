package com.example.dialektapp.di

import com.example.dialektapp.data.repository.AuthRepositoryImpl
import com.example.dialektapp.data.repository.CoursesRepositoryImpl
import com.example.dialektapp.domain.repository.AuthRepository
import com.example.dialektapp.domain.repository.CoursesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindCoursesRepository(coursesRepositoryImpl: CoursesRepositoryImpl): CoursesRepository
}
