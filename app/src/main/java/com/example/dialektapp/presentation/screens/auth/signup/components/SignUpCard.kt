package com.example.dialektapp.presentation.screens.auth.signup.components

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
import com.example.dialektapp.ui.theme.TextPrimary
import com.example.dialektapp.ui.theme.TextSecondary

@Composable
fun SignUpCard(
    fullName: String,
    email: String,
    password: String,
    confirmPassword: String,
    passwordVisible: Boolean,
    confirmPasswordVisible: Boolean,
    loadingState: Boolean,
    enabled: Boolean,
    username: String,
    agreeToTerms: Boolean,
    onSignUpClick: () -> Unit,
    onFullNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onUsernameChange: (String) -> Unit,
    onPasswordVisibleChange: (Boolean) -> Unit,
    onConfirmPasswordVisibleChange: (Boolean) -> Unit,
    onAgreeToTermsChange: (Boolean) -> Unit,
    errorHint: () -> List<ValidationError>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
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
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Створити аккаунт",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Будь ласка, введіть свої дані, щоб зареєструватися і почати користуватися додатком.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(28.dp))

            UserNameField(
                username = username,
                onUsernameChange = onUsernameChange,
                errorHint = errorHint
            )

            Spacer(modifier = Modifier.height(16.dp))

            FullNameField(
                fullName = fullName,
                onFullNameChange = onFullNameChange,
                errorHint = errorHint
            )

            Spacer(modifier = Modifier.height(16.dp))

            EmailField(
                email = email,
                onEmailChange = onEmailChange,
                errorHint = errorHint
            )

            Spacer(modifier = Modifier.height(16.dp))

            PasswordField(
                password = password,
                passwordVisible = passwordVisible,
                onPasswordVisibleChange = onPasswordVisibleChange,
                onPasswordChange = onPasswordChange,
                errorHint = errorHint
            )

            Spacer(modifier = Modifier.height(16.dp))

            ConfirmPasswordField(
                confirmPassword = confirmPassword,
                passwordVisible = confirmPasswordVisible,
                onPasswordVisibleChange = onConfirmPasswordVisibleChange,
                onConfirmPasswordChange = onConfirmPasswordChange,
                errorHint = errorHint
            )

            Spacer(modifier = Modifier.height(12.dp))

            TermsAndConditions(
                agreeToTerms = agreeToTerms,
                onAgreeToTermsChange = onAgreeToTermsChange,
                errorHint = errorHint,
            )

            Spacer(modifier = Modifier.height(20.dp))

            AuthButton(
                primaryText = "Зареєструватися",
                secondaryText = "Будь ласка, зачекайте",
                loadingState = loadingState,
                enabled = enabled,
                onAuthClick = onSignUpClick
            )
        }
    }
}