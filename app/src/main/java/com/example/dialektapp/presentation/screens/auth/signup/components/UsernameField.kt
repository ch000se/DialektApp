package com.example.dialektapp.presentation.screens.auth.signup.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.dialektapp.domain.util.ValidationError
import com.example.dialektapp.presentation.util.toUserMessage
import com.example.dialektapp.ui.theme.AccentBlue
import com.example.dialektapp.ui.theme.AuthErrorRed
import com.example.dialektapp.ui.theme.BorderColor
import com.example.dialektapp.ui.theme.TextPrimary
import com.example.dialektapp.ui.theme.TextSecondary

@Composable
fun UserNameField(
    username: String,
    onUsernameChange: (String) -> Unit,
    errorHint: () -> List<ValidationError>,
) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val firstUsernameError = errorHint().firstOrNull {
        it is ValidationError.UsernameEmpty ||
                it is ValidationError.UsernameTooShort ||
                it is ValidationError.UsernameTooLong ||
                it is ValidationError.UsernameInvalidChars
    }

    Column {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = username,
            onValueChange = onUsernameChange,
            label = { Text("Ім'я користувача") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Ім'я користувача",
                    tint = if (firstUsernameError != null) AuthErrorRed else TextSecondary
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Next)
                }
            ),
            isError = firstUsernameError != null,
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (firstUsernameError != null) AuthErrorRed else AccentBlue,
                unfocusedBorderColor = if (firstUsernameError != null) AuthErrorRed else BorderColor,
                focusedLabelColor = if (firstUsernameError != null) AuthErrorRed else AccentBlue,
                unfocusedLabelColor = if (firstUsernameError != null) AuthErrorRed else TextSecondary,
                focusedTextColor = if (firstUsernameError != null) AuthErrorRed else AccentBlue,
                unfocusedTextColor = if (firstUsernameError != null) AuthErrorRed else TextSecondary,
                focusedLeadingIconColor = if (firstUsernameError != null) AuthErrorRed else AccentBlue,
                unfocusedLeadingIconColor = if (firstUsernameError != null) AuthErrorRed else TextSecondary,
                errorBorderColor = AuthErrorRed,
                errorLabelColor = AuthErrorRed,
                errorLeadingIconColor = AuthErrorRed,
                errorTextColor = AuthErrorRed,
                errorCursorColor = AuthErrorRed,
                errorTrailingIconColor = AuthErrorRed,
            )
        )

        if (firstUsernameError != null) {
            Text(
                text = firstUsernameError.toUserMessage(context),
                color = AuthErrorRed,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp),
                textAlign = TextAlign.Start
            )
        }

    }
}