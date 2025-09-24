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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.dialektapp.domain.util.ValidationError
import com.example.dialektapp.presentation.util.toUserMessage
import com.example.dialektapp.ui.theme.BorderColor
import com.example.dialektapp.ui.theme.Primary
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
            label = { Text("Username") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Username",
                    tint = if (firstUsernameError != null) Color.Red else TextSecondary
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
                focusedBorderColor = if (firstUsernameError != null) Color.Red else Primary,
                unfocusedBorderColor = if (firstUsernameError != null) Color.Red else BorderColor,
                focusedLabelColor = if (firstUsernameError != null) Color.Red else Primary,
                unfocusedLabelColor = if (firstUsernameError != null) Color.Red else TextSecondary,
                focusedTextColor = if (firstUsernameError != null) Color.Red else Primary,
                unfocusedTextColor = if (firstUsernameError != null) Color.Red else TextSecondary,
                focusedLeadingIconColor = if (firstUsernameError != null) Color.Red else Primary,
                unfocusedLeadingIconColor = if (firstUsernameError != null) Color.Red else TextSecondary,
                errorBorderColor = Color.Red,
                errorLabelColor = Color.Red,
                errorLeadingIconColor = Color.Red,
                errorTextColor = Color.Red,
                errorCursorColor = Color.Red,
                errorTrailingIconColor = Color.Red,
            )
        )

        if (firstUsernameError != null) {
            Text(
                text = firstUsernameError.toUserMessage(context),
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp),
                textAlign = TextAlign.Center
            )
        }

    }
}