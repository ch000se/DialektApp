package com.example.dialektapp.presentation.screens.auth.login.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import com.example.dialektapp.ui.theme.Primary
import com.example.dialektapp.ui.theme.TextPrimary
import com.example.dialektapp.ui.theme.TextSecondary

@Composable
fun SignUpLink(
    onSignUpClick: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Don't have an account? ",
            color = TextSecondary,
            style = MaterialTheme.typography.bodyMedium
        )
        TextButton(
            onClick = onSignUpClick
        ) {
            Text(
                text = "Sign Up",
                color = Primary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}