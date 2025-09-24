package com.example.dialektapp.presentation.screens.auth.login.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.dialektapp.ui.theme.Primary
import com.example.dialektapp.ui.theme.TextPrimary
import com.example.dialektapp.ui.theme.TextSecondary

@Composable
fun RememberAndForgot(
    rememberMe: Boolean,
    onRememberMeChange: (Boolean) -> Unit,
    onForgotPasswordClick: () -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = rememberMe,
                onCheckedChange = onRememberMeChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = Primary
                )
            )
            Text(
                text = "Remember me",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary
            )
        }

        TextButton(
            onClick = onForgotPasswordClick
        ) {
            Text(
                text = "Forgot Password?",
                color = Primary,
                fontWeight = FontWeight.Medium
            )
        }
    }
}