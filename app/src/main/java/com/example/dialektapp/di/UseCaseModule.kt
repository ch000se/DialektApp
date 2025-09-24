package com.example.dialektapp.di

import com.example.dialektapp.domain.repository.AuthRepository
import com.example.dialektapp.domain.usecases.GetCurrentUserUseCase
import com.example.dialektapp.domain.usecases.LoginUseCase
import com.example.dialektapp.domain.usecases.LogoutUseCase
import com.example.dialektapp.domain.usecases.RegisterUseCase
import com.example.dialektapp.domain.usecases.ValidateLoginUseCase
import com.example.dialektapp.domain.usecases.ValidateSignUpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideRegisterUseCase(repository: AuthRepository): RegisterUseCase {
        return RegisterUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideLoginUseCase(repository: AuthRepository): LoginUseCase {
        return LoginUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetCurrentUserUseCase(repository: AuthRepository): GetCurrentUserUseCase {
        return GetCurrentUserUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideLogoutUseCase(repository: AuthRepository): LogoutUseCase {
        return LogoutUseCase(repository)
    }

    @Provides
    fun provideValidateSignUpUseCase(): ValidateSignUpUseCase {
        return ValidateSignUpUseCase()
    }

    @Provides
    fun provideValidateValidateLoginUseCase(): ValidateLoginUseCase {
        return ValidateLoginUseCase()
    }
}