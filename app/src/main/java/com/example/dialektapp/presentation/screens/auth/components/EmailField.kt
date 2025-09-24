package com.example.dialektapp.presentation.screens.auth.components

import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dialektapp.domain.util.ValidationError
import com.example.dialektapp.presentation.util.toUserMessage
import com.example.dialektapp.ui.theme.BorderColor
import com.example.dialektapp.ui.theme.Primary
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


    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = email,
        onValueChange = onEmailChange,
        label = { Text("Email Address") },
        leadingIcon = {
            Icon(
                Icons.Filled.Email,
                contentDescription = "Email",
                tint = if (firstEmailError != null) Color.Red else TextSecondary
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email
        ),
        singleLine = true,
        isError = firstEmailError != null,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = if (firstEmailError != null) Color.Red else Primary,
            unfocusedBorderColor = if (firstEmailError != null) Color.Red else BorderColor,
            focusedLabelColor = if (firstEmailError != null) Color.Red else Primary,
            unfocusedLabelColor = if (firstEmailError != null) Color.Red else TextSecondary,
            focusedTextColor = if (firstEmailError != null) Color.Red else Primary,
            unfocusedTextColor = if (firstEmailError != null) Color.Red else TextSecondary,
            focusedLeadingIconColor = if (firstEmailError != null) Color.Red else Primary,
            unfocusedLeadingIconColor = if (firstEmailError != null) Color.Red else TextSecondary,
            errorBorderColor = Color.Red,
            errorLabelColor = Color.Red,
            errorLeadingIconColor = Color.Red,
            errorTextColor = Color.Red,
            errorCursorColor = Color.Red,
            errorTrailingIconColor = Color.Red,
        )
    )

    if (firstEmailError != null) {
        Text(
            text = firstEmailError.toUserMessage(context),
            color = Color.Red,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center
        )
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