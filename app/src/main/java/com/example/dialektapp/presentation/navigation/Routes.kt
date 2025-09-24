package com.example.dialektapp.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Routes {
    @Serializable
    data object Auth : Routes()
    @Serializable
    data object Login : Routes()

    @Serializable
    data object SignUp : Routes()

    @Serializable
    data object ForgotPassword : Routes()

    @Serializable
    data object Profile : Routes()
}