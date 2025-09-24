package com.example.dialektapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavGraph(
    navController: NavHostController,
    loginScreenContent: @Composable () -> Unit,
    signUpScreenContent: @Composable () -> Unit,
    forgotPasswordScreenContent: @Composable () -> Unit,
    profileScreenContent: @Composable () -> Unit = {},
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Auth
    ) {
        authNavGraph(
            loginScreenContent = loginScreenContent,
            signUpScreenContent = signUpScreenContent,
            forgotPasswordScreenContent = forgotPasswordScreenContent
        )

        composable<Routes.Profile> {
            profileScreenContent()
        }
    }
}