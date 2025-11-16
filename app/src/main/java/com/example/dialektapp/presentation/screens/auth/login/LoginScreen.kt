package com.example.dialektapp.presentation.screens.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.dialektapp.R
import com.example.dialektapp.presentation.screens.auth.login.components.LoginCard
import com.example.dialektapp.presentation.screens.auth.login.components.TopLoginScreen
import com.example.dialektapp.presentation.util.UiEvent
import com.example.dialektapp.presentation.util.toUserMessage
import com.example.dialektapp.ui.theme.AppGradients
import com.example.dialektapp.ui.theme.BackgroundDeepBlue
import com.example.dialektapp.ui.theme.GradientEnd
import com.example.dialektapp.ui.theme.GradientMiddle
import com.example.dialektapp.ui.theme.GradientStart
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    snackbarHostState: SnackbarHostState,
    prefilledUsername: String = "",
    prefilledPassword: String = "",
    viewModel: LoginViewModel = hiltViewModel(),
    onSignUpClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onSignInSuccess: () -> Unit,
) {
    val context = LocalContext.current
    val state = viewModel.loginState.collectAsStateWithLifecycle()

    LaunchedEffect(prefilledUsername, prefilledPassword) {
        if (prefilledUsername.isNotEmpty()) {
            viewModel.updateUsername(prefilledUsername)
        }
        if (prefilledPassword.isNotEmpty()) {
            viewModel.updatePassword(prefilledPassword)
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.actionLabel,
                    )
                }

                is UiEvent.ShowErrorSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.error.toUserMessage(context),
                        actionLabel = event.actionLabel
                    )
                }

                UiEvent.Navigate -> {
                    onSignInSuccess()
                }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = BackgroundDeepBlue
            )
            .padding(contentPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TopLoginScreen()

        Spacer(modifier = Modifier.height(20.dp))

        LoginCard(
            username = state.value.username,
            password = state.value.password,
            passwordVisible = state.value.isPasswordVisible,
            loadingState = state.value.isLoading,
            enabled = state.value.isInputValid,
            rememberMe = state.value.rememberMe,
            onUsernameChange = viewModel::updateUsername,
            onPasswordChange = viewModel::updatePassword,
            onPasswordVisibleChange = { viewModel.togglePasswordVisibility() },
            onSignInClick = { viewModel.login() },
            onRememberMeChange = { viewModel.toggleRememberMe() },
            onForgotPasswordClick = onForgotPasswordClick,
            onSignUpClick = onSignUpClick,
            errorHint = { state.value.errorMessageInput }
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    LoginScreen(
        modifier = Modifier,
        viewModel = hiltViewModel(),
        onSignUpClick = {},
        onForgotPasswordClick = {},
        onSignInSuccess = {},
        prefilledUsername = "",
        prefilledPassword = "",
        snackbarHostState = SnackbarHostState(),
        contentPadding = PaddingValues()
    )
}
