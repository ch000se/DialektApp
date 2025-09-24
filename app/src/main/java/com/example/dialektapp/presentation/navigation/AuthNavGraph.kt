package com.example.dialektapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.authNavGraph(
    loginScreenContent: @Composable () -> Unit,
    signUpScreenContent: @Composable () -> Unit,
    forgotPasswordScreenContent: @Composable () -> Unit
) {
    navigation<Routes.Auth>(startDestination = Routes.Login){
        composable<Routes.Login>{
            loginScreenContent()
        }

        composable<Routes.SignUp> {
            signUpScreenContent()
        }

        composable<Routes.ForgotPassword> {
            forgotPasswordScreenContent()
        }
    }
}