package com.example.dialektapp.presentation.screens.auth.forgotpass

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import com.example.dialektapp.domain.util.ValidationError
import com.example.dialektapp.presentation.screens.auth.forgotpass.components.ForgotPasswordCard
import com.example.dialektapp.presentation.screens.auth.components.TopAuthBar
import com.example.dialektapp.ui.theme.BackColor
import com.example.dialektapp.ui.theme.DialektAppTheme
import com.example.dialektapp.ui.theme.GradientStart
import com.example.dialektapp.ui.theme.GradientMiddle
import com.example.dialektapp.ui.theme.GradientEnd

@Composable
fun ForgotPasswordScreen(
    modifier: Modifier = Modifier,
    onSignInClick: () -> Unit = {},
    onSendResetLink: () -> Unit = {},
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAuthBar(
                text = "Відновлення паролю",
                onBackClick = onSignInClick
            )
        },
        containerColor = GradientStart,
        contentWindowInsets = WindowInsets(bottom = 0)
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(
                    color = BackColor
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ForgotPasswordCard(
                email = "",
                loadingState = false,
                enabled = false,
                onEmailChange = {},
                onResetPasswordClick = onSendResetLink,
                errorHint = { emptyList()}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ForgotPasswordScreenPreview() {
    DialektAppTheme {
        ForgotPasswordScreen()
    }
}
