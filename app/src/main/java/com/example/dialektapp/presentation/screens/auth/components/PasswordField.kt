package com.example.dialektapp.presentation.screens.auth.components

import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.dialektapp.ui.theme.TextSecondary

@Composable
fun PasswordField(
    password: String,
    passwordVisible: Boolean,
    onPasswordVisibleChange: (Boolean) -> Unit,
    onPasswordChange: (String) -> Unit,
    errorHint: () -> List<ValidationError>,
) {
    val context = LocalContext.current
    val firstPasswordError = errorHint().firstOrNull {
        it is ValidationError.PasswordEmpty ||
                it is ValidationError.PasswordTooShort ||
                it is ValidationError.PasswordTooLong ||
                it is ValidationError.PasswordMissingUpperCase ||
                it is ValidationError.PasswordMissingLowerCase ||
                it is ValidationError.PasswordMissingDigit ||
                it is ValidationError.PasswordMissingSpecialChar
    }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = password,
        onValueChange = onPasswordChange,
        label = { Text("Password") },
        leadingIcon = {
            Icon(
                Icons.Filled.Lock,
                contentDescription = "Password",
                tint = if (firstPasswordError != null) Color.Red else TextSecondary
            )
        },
        trailingIcon = {
            IconButton(
                onClick = { onPasswordVisibleChange(!passwordVisible) }
            ) {
                Icon(
                    imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                    contentDescription = if (passwordVisible) "Hide password" else "Show password",
                    tint = if (firstPasswordError != null) Color.Red else TextSecondary
                )
            }
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        isError = firstPasswordError != null,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = if (firstPasswordError != null) Color.Red else Primary,
            unfocusedBorderColor = if (firstPasswordError != null) Color.Red else BorderColor,
            focusedLabelColor = if (firstPasswordError != null) Color.Red else Primary,
            unfocusedLabelColor = if (firstPasswordError != null) Color.Red else TextSecondary,
            focusedTextColor = if (firstPasswordError != null) Color.Red else Primary,
            unfocusedTextColor = if (firstPasswordError != null) Color.Red else TextSecondary,
            focusedLeadingIconColor = if (firstPasswordError != null) Color.Red else Primary,
            unfocusedLeadingIconColor = if (firstPasswordError != null) Color.Red else TextSecondary,
            errorBorderColor = Color.Red,
            errorLabelColor = Color.Red,
            errorLeadingIconColor = Color.Red,
            errorTextColor = Color.Red,
            errorCursorColor = Color.Red,
            errorTrailingIconColor = Color.Red,
        )
    )

    if (firstPasswordError != null) {
        Text(
            text = firstPasswordError.toUserMessage(context),
            color = Color.Red,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center
        )
    }
}
