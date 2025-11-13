package com.example.dialektapp.presentation.screens.auth.forgotpass.components

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
import com.example.dialektapp.ui.theme.AuthBackgroundWhite
import com.example.dialektapp.ui.theme.TextPrimary
import com.example.dialektapp.ui.theme.TextSecondary

@Composable
fun ForgotPasswordCard(
    email: String,
    loadingState: Boolean,
    enabled: Boolean,
    errorHint: () -> List<ValidationError>,
    onEmailChange: (String) -> Unit,
    onResetPasswordClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 32.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = AuthBackgroundWhite
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Забули пароль?",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Введіть свою електронну пошту, щоб отримати посилання для скидання пароля.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(32.dp))
            EmailField(
                email = email,
                onEmailChange = onEmailChange,
                errorHint = errorHint
            )
            Spacer(modifier = Modifier.height(24.dp))
            AuthButton(
                primaryText = "Надіслати посилання",
                secondaryText = "Надсилання...",
                loadingState = loadingState,
                enabled = enabled,
                onAuthClick = onResetPasswordClick
            )
        }
    }
}