package com.example.dialektapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.example.dialektapp.presentation.screens.auth.forgotpass.ForgotPasswordScreen
import com.example.dialektapp.presentation.navigation.NavGraph
import com.example.dialektapp.presentation.navigation.Routes
import com.example.dialektapp.presentation.screens.auth.login.LoginScreen
import com.example.dialektapp.presentation.screens.auth.signup.SignUpScreen
import com.example.dialektapp.presentation.screens.profile.ProfileScreen
import com.example.dialektapp.ui.theme.DialektAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            DialektAppTheme {
                val navController = rememberNavController()
                NavGraph(
                    navController = navController,
                    loginScreenContent = {
                        LoginScreen(
                            onSignUpClick = {
                                navController.navigate(Routes.SignUp) {
                                    launchSingleTop = true
                                }
                            },
                            onForgotPasswordClick = {
                                navController.navigate(Routes.ForgotPassword) {
                                    launchSingleTop = true
                                }
                            },
                            onSignInSuccess = {
                                navController.navigate(Routes.Profile) {
                                    popUpTo(Routes.Auth) { inclusive = true }
                                    launchSingleTop = true
                                }
                            }
                        )
                    },
                    signUpScreenContent = {
                        SignUpScreen(
                            onSignInClick = {
                                navController.navigate(Routes.Login) {
                                    popUpTo(navController.graph.findStartDestination().id)
                                }
                            },
                            onSignUpSuccess = { email, password ->
                                navController.navigate(Routes.Login) {
                                    popUpTo(Routes.SignUp) { inclusive = true }
                                }
                            }
                        )
                    },
                    forgotPasswordScreenContent = {
                        ForgotPasswordScreen(
                            onSignInClick = {
                                navController.navigate(Routes.Login) {
                                    popUpTo(navController.graph.findStartDestination().id)
                                }
                            }
                        )
                    },
                    profileScreenContent = {
                        ProfileScreen(
                            onLogoutSuccess = {
                                navController.navigate(Routes.Auth) {
                                    popUpTo(0) { inclusive = true }
                                    launchSingleTop = true
                                }
                            }
                        )
                    }
                )
            }
        }
    }
}
