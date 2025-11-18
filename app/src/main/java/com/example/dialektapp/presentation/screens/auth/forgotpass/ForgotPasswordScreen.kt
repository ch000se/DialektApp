package com.example.dialektapp.presentation.screens.auth.forgotpass

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.dialektapp.presentation.screens.auth.forgotpass.components.ForgotPasswordCard
import com.example.dialektapp.presentation.screens.auth.components.TopAuthBar
import com.example.dialektapp.presentation.util.UiEvent
import com.example.dialektapp.presentation.util.toUserMessage
import com.example.dialektapp.ui.theme.BackgroundDeepBlue
import com.example.dialektapp.ui.theme.DialektAppTheme
import com.example.dialektapp.ui.theme.GradientStart

@Composable
fun ForgotPasswordScreen(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    viewModel: ForgotPasswordViewModel = hiltViewModel(),
    onSignInClick: () -> Unit = {},
    onResetSuccess: () -> Unit = {},
) {
    val context = LocalContext.current
    val state by viewModel.forgotPasswordState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.actionLabel
                    )
                }

                is UiEvent.ShowErrorSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.error.toUserMessage(context),
                        actionLabel = event.actionLabel
                    )
                }

                UiEvent.Navigate -> {
                    onResetSuccess()
                }
            }
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAuthBar(
                text = "Відновлення паролю",
                onBackClick = onSignInClick
            )
        },
        containerColor = GradientStart,
        contentWindowInsets = WindowInsets(bottom = 0),
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(
                    color = BackgroundDeepBlue
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ForgotPasswordCard(
                email = state.email,
                loadingState = state.isLoading,
                enabled = state.isInputValid,
                onEmailChange = viewModel::updateEmail,
                onResetPasswordClick = { viewModel.resetPassword() },
                errorHint = { state.errorMessageInput }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ForgotPasswordScreenPreview() {
    DialektAppTheme {
        ForgotPasswordScreen(
            modifier = Modifier,
            snackbarHostState = remember { SnackbarHostState() },
            onSignInClick = {},
            onResetSuccess = {}
        )
    }
}
