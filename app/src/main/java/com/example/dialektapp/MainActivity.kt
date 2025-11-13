package com.example.dialektapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.example.dialektapp.presentation.screens.auth.forgotpass.ForgotPasswordScreen
import com.example.dialektapp.presentation.navigation.NavGraph
import com.example.dialektapp.presentation.navigation.Routes
import com.example.dialektapp.presentation.screens.auth.login.LoginScreen
import com.example.dialektapp.presentation.screens.auth.login.LoginViewModel
import com.example.dialektapp.presentation.screens.auth.signup.SignUpScreen
import com.example.dialektapp.presentation.screens.profile.ProfileScreen
import com.example.dialektapp.ui.theme.DialektAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            DialektAppTheme {
                val navController = rememberNavController()
                val snackbarHostState = remember { SnackbarHostState() }

                var prefilledUsername by remember { mutableStateOf("") }
                var prefilledPassword by remember { mutableStateOf("") }

                Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { paddingValues ->
                    NavGraph(
                        navController = navController,
                        loginScreenContent = {
                            LoginScreen(
                                prefilledUsername = prefilledUsername,
                                prefilledPassword = prefilledPassword,
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
                                contentPadding = paddingValues,
                                snackbarHostState = snackbarHostState,
                                onSignInSuccess = {
                                    navController.navigate(Routes.Home) {
                                        popUpTo(0) { inclusive = true }
                                        launchSingleTop = true
                                    }
                                },
                            )
                        },
                        signUpScreenContent = {
                            SignUpScreen(
                                onSignInClick = {
                                    navController.navigate(Routes.Login) {
                                        popUpTo(navController.graph.findStartDestination().id)
                                        launchSingleTop = true
                                    }
                                },
                                onSignUpSuccess = { username, password ->
                                    prefilledUsername = username
                                    prefilledPassword = password
                                    navController.navigate(Routes.Login) {
                                        popUpTo(Routes.SignUp) { inclusive = true }
                                    }
                                },
                                snackbarHostState = snackbarHostState
                            )
                        },
                        forgotPasswordScreenContent = {
                            ForgotPasswordScreen(
                                onSignInClick = {
                                    navController.navigate(Routes.Login) {
                                        popUpTo(navController.graph.findStartDestination().id)
                                        launchSingleTop = true
                                    }
                                },
                                onResetSuccess = {
                                    navController.navigate(Routes.Login) {
                                        popUpTo(navController.graph.findStartDestination().id)
                                        launchSingleTop = true
                                    }
                                },
                                snackbarHostState = snackbarHostState
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
}
