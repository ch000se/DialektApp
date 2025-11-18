package com.example.dialektapp.presentation.screens.auth.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dialektapp.domain.util.ValidationError
import com.example.dialektapp.presentation.util.toUserMessage
import com.example.dialektapp.ui.theme.AccentBlue
import com.example.dialektapp.ui.theme.AuthErrorRed
import com.example.dialektapp.ui.theme.BorderColor
import com.example.dialektapp.ui.theme.TextSecondary

@Composable
fun EmailField(
    email: String,
    onEmailChange: (String) -> Unit,
    errorHint: () -> List<ValidationError>
) {
    val context = LocalContext.current
    val firstEmailError = errorHint().firstOrNull {
        it is ValidationError.EmailEmpty || it is ValidationError.InvalidEmail || it is ValidationError.EmailAlreadyUsed
    }

    Column {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = onEmailChange,
            label = { Text("Електронна пошта") },
            leadingIcon = {
                Icon(
                    Icons.Filled.Email,
                    contentDescription = "Електронна пошта",
                    tint = if (firstEmailError != null) AuthErrorRed else TextSecondary
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            singleLine = true,
            isError = firstEmailError != null,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (firstEmailError != null) AuthErrorRed else AccentBlue,
                unfocusedBorderColor = if (firstEmailError != null) AuthErrorRed else BorderColor,
                focusedLabelColor = if (firstEmailError != null) AuthErrorRed else AccentBlue,
                unfocusedLabelColor = if (firstEmailError != null) AuthErrorRed else TextSecondary,
                focusedTextColor = if (firstEmailError != null) AuthErrorRed else AccentBlue,
                unfocusedTextColor = if (firstEmailError != null) AuthErrorRed else TextSecondary,
                focusedLeadingIconColor = if (firstEmailError != null) AuthErrorRed else AccentBlue,
                unfocusedLeadingIconColor = if (firstEmailError != null) AuthErrorRed else TextSecondary,
                errorBorderColor = AuthErrorRed,
                errorLabelColor = AuthErrorRed,
                errorLeadingIconColor = AuthErrorRed,
                errorTextColor = AuthErrorRed,
                errorCursorColor = AuthErrorRed,
                errorTrailingIconColor = AuthErrorRed,
            )
        )

        if (firstEmailError != null) {
            Text(
                text = firstEmailError.toUserMessage(context),
                color = AuthErrorRed,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp),
                textAlign = TextAlign.Start
            )
        }
    }
}

@Preview()
@Composable
private fun EmailFieldPreview() {
    EmailField(
        email = "",
        onEmailChange = {},
        errorHint = { listOf(ValidationError.EmailEmpty) }
    )
}