package com.example.dialektapp.presentation.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
        composable<Routes.Login>(
            enterTransition = {
                slideInHorizontally(
                    animationSpec = tween(400),
                    initialOffsetX = { it / 3 }
                ) + fadeIn(animationSpec = tween(400))
            },
            exitTransition = {
                slideOutHorizontally(
                    animationSpec = tween(400),
                    targetOffsetX = { -it / 3 }
                ) + fadeOut(animationSpec = tween(400))
            }
        ) {
            loginScreenContent()
        }

        composable<Routes.SignUp>(
            enterTransition = {
                slideInHorizontally(
                    animationSpec = tween(400),
                    initialOffsetX = { it / 3 }
                ) + fadeIn(animationSpec = tween(400))
            },
            exitTransition = {
                slideOutHorizontally(
                    animationSpec = tween(400),
                    targetOffsetX = { -it / 3 }
                ) + fadeOut(animationSpec = tween(400))
            }
        ) {
            signUpScreenContent()
        }

        composable<Routes.ForgotPassword>(
            enterTransition = {
                slideInHorizontally(
                    animationSpec = tween(400),
                    initialOffsetX = { it / 3 }
                ) + fadeIn(animationSpec = tween(400))
            },
            exitTransition = {
                slideOutHorizontally(
                    animationSpec = tween(400),
                    targetOffsetX = { -it / 3 }
                ) + fadeOut(animationSpec = tween(400))
            }
        ) {
            forgotPasswordScreenContent()
        }
    }
}