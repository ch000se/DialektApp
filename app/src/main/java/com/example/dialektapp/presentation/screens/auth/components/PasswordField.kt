package com.example.dialektapp.presentation.screens.auth.components

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.dialektapp.domain.util.ValidationError
import com.example.dialektapp.presentation.util.toUserMessage
import com.example.dialektapp.ui.theme.AccentBlue
import com.example.dialektapp.ui.theme.AuthErrorRed
import com.example.dialektapp.ui.theme.BorderColor
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

    Column {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = onPasswordChange,
            label = { Text("Пароль") },
            leadingIcon = {
                Icon(
                    Icons.Filled.Lock,
                    contentDescription = "Пароль",
                    tint = if (firstPasswordError != null) AuthErrorRed else TextSecondary
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = { onPasswordVisibleChange(!passwordVisible) }
                ) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = if (passwordVisible) "Сховати пароль" else "Показати пароль",
                        tint = if (firstPasswordError != null) AuthErrorRed else TextSecondary
                    )
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            isError = firstPasswordError != null,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (firstPasswordError != null) AuthErrorRed else AccentBlue,
                unfocusedBorderColor = if (firstPasswordError != null) AuthErrorRed else BorderColor,
                focusedLabelColor = if (firstPasswordError != null) AuthErrorRed else AccentBlue,
                unfocusedLabelColor = if (firstPasswordError != null) AuthErrorRed else TextSecondary,
                focusedTextColor = if (firstPasswordError != null) AuthErrorRed else AccentBlue,
                unfocusedTextColor = if (firstPasswordError != null) AuthErrorRed else TextSecondary,
                focusedLeadingIconColor = if (firstPasswordError != null) AuthErrorRed else AccentBlue,
                unfocusedLeadingIconColor = if (firstPasswordError != null) AuthErrorRed else TextSecondary,
                errorBorderColor = AuthErrorRed,
                errorLabelColor = AuthErrorRed,
                errorLeadingIconColor = AuthErrorRed,
                errorTextColor = AuthErrorRed,
                errorCursorColor = AuthErrorRed,
                errorTrailingIconColor = AuthErrorRed,
            )
        )

        if (firstPasswordError != null) {
            Text(
                text = firstPasswordError.toUserMessage(context),
                color = AuthErrorRed,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp),
                textAlign = TextAlign.Start
            )
        }
    }
}
