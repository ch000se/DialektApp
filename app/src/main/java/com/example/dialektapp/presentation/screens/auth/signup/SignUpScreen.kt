package com.example.dialektapp.presentation.screens.auth.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.dialektapp.R
import com.example.dialektapp.presentation.screens.auth.components.TopAuthBar
import com.example.dialektapp.presentation.screens.auth.signup.components.SignUpCard
import com.example.dialektapp.presentation.util.UiEvent
import com.example.dialektapp.presentation.util.toUserMessage
import com.example.dialektapp.ui.theme.AppGradients
import com.example.dialektapp.ui.theme.BackColor
import com.example.dialektapp.ui.theme.DialektAppTheme
import com.example.dialektapp.ui.theme.GradientStart

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    onSignInClick: () -> Unit = {},
    onSignUpSuccess: (String, String) -> Unit,
) {
    val context = LocalContext.current
    val state by viewModel.signUpState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message.toUserMessage(context),
                        actionLabel = event.actionLabel
                    )
                }

                UiEvent.Navigate -> {
                    onSignUpSuccess(
                        state.email,
                        state.password
                    )
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAuthBar(text = "Створити акаунт", onBackClick = onSignInClick)
        },
        containerColor = GradientStart,
        contentWindowInsets = WindowInsets(bottom = 0)
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = BackColor
                )
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            SignUpCard(
                fullName = state.fullname,
                email = state.email,
                password = state.password,
                confirmPassword = state.confirmPassword,
                passwordVisible = state.isPasswordVisible,
                confirmPasswordVisible = state.isConfirmPasswordVisible,
                loadingState = state.isLoading,
                enabled = state.isInputValid,
                username = state.username,
                agreeToTerms = state.agreeToTerms,
                onSignUpClick = { viewModel.register() },
                onFullNameChange = viewModel::updateFullName,
                onEmailChange = viewModel::updateEmail,
                onPasswordChange = viewModel::updatePassword,
                onConfirmPasswordChange = viewModel::updateConfirmPassword,
                onUsernameChange = viewModel::updateUsername,
                onPasswordVisibleChange = { viewModel.togglePasswordVisibility() },
                onConfirmPasswordVisibleChange = { viewModel.toggleConfirmPasswordVisibility() },
                onAgreeToTermsChange = viewModel::updateAgreeToTerms,
                errorHint = { state.errorMessageInput }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    DialektAppTheme {
        SignUpScreen(
            viewModel = TODO(),
            onSignInClick = {},
            onSignUpSuccess = { _, _ -> }
        )
    }
}