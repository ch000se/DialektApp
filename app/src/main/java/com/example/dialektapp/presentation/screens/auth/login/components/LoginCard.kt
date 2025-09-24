package com.example.dialektapp.presentation.screens.auth.login.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.dialektapp.domain.util.ValidationError
import com.example.dialektapp.presentation.screens.auth.components.AuthButton
import com.example.dialektapp.presentation.screens.auth.components.EmailField
import com.example.dialektapp.presentation.screens.auth.components.PasswordField
import com.example.dialektapp.presentation.screens.auth.login.components.RememberAndForgot
import com.example.dialektapp.presentation.screens.auth.login.components.SignUpLink
import com.example.dialektapp.presentation.screens.auth.signup.components.UserNameField
import com.example.dialektapp.ui.theme.TextPrimary
import com.example.dialektapp.ui.theme.TextSecondary

@Composable
fun LoginCard(
    username: String,
    password: String,
    passwordVisible: Boolean,
    loadingState: Boolean,
    enabled: Boolean,
    rememberMe: Boolean,
    errorHint: () -> List<ValidationError>,
    onSignInClick: () -> Unit,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordVisibleChange: (Boolean) -> Unit,
    onRememberMeChange: (Boolean) -> Unit,
    onForgotPasswordClick: () -> Unit,
    onSignUpClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 32.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 12.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "З поверненням!",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Увійдіть, щоб продовжити використовувати додаток",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            UserNameField(
                username = username,
                onUsernameChange = onUsernameChange,
                errorHint = errorHint
            )

            Spacer(modifier = Modifier.height(8.dp))

            PasswordField(
                password = password,
                passwordVisible = passwordVisible,
                onPasswordVisibleChange = onPasswordVisibleChange,
                onPasswordChange = onPasswordChange,
                errorHint = errorHint
            )

            Spacer(modifier = Modifier.height(8.dp))

            RememberAndForgot(
                rememberMe = rememberMe,
                onRememberMeChange = onRememberMeChange,
                onForgotPasswordClick = onForgotPasswordClick
            )

            Spacer(modifier = Modifier.height(16.dp))

            AuthButton(
                primaryText = "Вхід",
                secondaryText = "Будь ласка, зачекайте",
                loadingState = loadingState,
                enabled = enabled,
                onAuthClick = onSignInClick
            )

            Spacer(modifier = Modifier.height(16.dp))

            SignUpLink(
                onSignUpClick = onSignUpClick
            )

        }
    }
}
