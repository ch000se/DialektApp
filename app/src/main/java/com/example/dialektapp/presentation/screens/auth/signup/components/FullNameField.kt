package com.example.dialektapp.presentation.screens.auth.signup.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.dialektapp.domain.util.ValidationError
import com.example.dialektapp.presentation.util.toUserMessage
import com.example.dialektapp.ui.theme.AccentBlue
import com.example.dialektapp.ui.theme.AuthErrorRed
import com.example.dialektapp.ui.theme.BorderColor
import com.example.dialektapp.ui.theme.TextSecondary

@Composable
fun FullNameField(
    fullName: String,
    onFullNameChange: (String) -> Unit,
    errorHint: () -> List<ValidationError>,
) {
    val context = LocalContext.current
    val firstFullnameError = errorHint().firstOrNull {
        it is ValidationError.FullNameEmpty ||
        it is ValidationError.FullNameTooShort ||
        it is ValidationError.FullNameTooLong
    }

    Column {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = fullName,
            onValueChange = onFullNameChange,
            label = { Text("Повне ім'я") },
            leadingIcon = {
                Icon(
                    Icons.Filled.Person,
                    contentDescription = "Повне ім'я",
                    tint = if (firstFullnameError != null) AuthErrorRed else TextSecondary
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),
            isError = firstFullnameError != null,
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (firstFullnameError != null) AuthErrorRed else AccentBlue,
                unfocusedBorderColor = if (firstFullnameError != null) AuthErrorRed else BorderColor,
                focusedLabelColor = if (firstFullnameError != null) AuthErrorRed else AccentBlue,
                unfocusedLabelColor = if (firstFullnameError != null) AuthErrorRed else TextSecondary,
                focusedTextColor = if (firstFullnameError != null) AuthErrorRed else AccentBlue,
                unfocusedTextColor = if (firstFullnameError != null) AuthErrorRed else TextSecondary,
                focusedLeadingIconColor = if (firstFullnameError != null) AuthErrorRed else AccentBlue,
                unfocusedLeadingIconColor = if (firstFullnameError != null) AuthErrorRed else TextSecondary,
                errorBorderColor = AuthErrorRed,
                errorLabelColor = AuthErrorRed,
                errorLeadingIconColor = AuthErrorRed,
                errorTextColor = AuthErrorRed,
                errorCursorColor = AuthErrorRed,
                errorTrailingIconColor = AuthErrorRed,
            )
        )

        if (firstFullnameError != null) {
            Text(
                text = firstFullnameError.toUserMessage(context),
                color = AuthErrorRed,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp),
                textAlign = TextAlign.Start
            )
        }
    }
}