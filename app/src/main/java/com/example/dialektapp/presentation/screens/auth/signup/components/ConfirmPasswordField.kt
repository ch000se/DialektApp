package com.example.dialektapp.presentation.screens.auth.signup.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.dialektapp.domain.util.ValidationError
import com.example.dialektapp.presentation.util.toUserMessage
import com.example.dialektapp.ui.theme.BorderColor
import com.example.dialektapp.ui.theme.Primary
import com.example.dialektapp.ui.theme.TextPrimary
import com.example.dialektapp.ui.theme.TextSecondary

@Composable
fun ConfirmPasswordField(
    confirmPassword: String,
    passwordVisible: Boolean,
    onPasswordVisibleChange: (Boolean) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    errorHint: () -> List<ValidationError>,
) {
    val context = LocalContext.current
    val firstConfirmPasswordError = errorHint().firstOrNull {
        it is ValidationError.PasswordsDoNotMatch
    }

    Column {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = confirmPassword,
            onValueChange = onConfirmPasswordChange,
            label = { Text("Confirm Password") },
            leadingIcon = {
                Icon(
                    Icons.Filled.Lock,
                    contentDescription = "Confirm Password",
                    tint = if (firstConfirmPasswordError != null) Color.Red else TextSecondary
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = { onPasswordVisibleChange(!passwordVisible) },
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = Color.Gray
                    )
                ) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = if (passwordVisible) "Hide password" else "Show password"
                    )
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            isError = firstConfirmPasswordError != null,
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (firstConfirmPasswordError != null) Color.Red else Primary,
                unfocusedBorderColor = if (firstConfirmPasswordError != null) Color.Red else BorderColor,
                focusedLabelColor = if (firstConfirmPasswordError != null) Color.Red else Primary,
                unfocusedLabelColor = if (firstConfirmPasswordError != null) Color.Red else TextSecondary,
                focusedTextColor = if (firstConfirmPasswordError != null) Color.Red else Primary,
                unfocusedTextColor = if (firstConfirmPasswordError != null) Color.Red else TextSecondary,
                focusedLeadingIconColor = if (firstConfirmPasswordError != null) Color.Red else Primary,
                unfocusedLeadingIconColor = if (firstConfirmPasswordError != null) Color.Red else TextSecondary,
                errorBorderColor = Color.Red,
                errorLabelColor = Color.Red,
                errorLeadingIconColor = Color.Red,
                errorTextColor = Color.Red,
                errorCursorColor = Color.Red,
                errorTrailingIconColor = Color.Red,
            )
        )

        if (firstConfirmPasswordError != null) {
            Text(
                text = firstConfirmPasswordError.toUserMessage(context),
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}