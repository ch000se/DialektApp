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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
            label = { Text("Full Name") },
            leadingIcon = {
                Icon(
                    Icons.Filled.Person,
                    contentDescription = "Full Name",
                    tint = if (firstFullnameError != null) Color.Red else TextSecondary
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),
            isError = firstFullnameError != null,
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (firstFullnameError != null) Color.Red else Primary,
                unfocusedBorderColor = if (firstFullnameError != null) Color.Red else BorderColor,
                focusedLabelColor = if (firstFullnameError != null) Color.Red else Primary,
                unfocusedLabelColor = if (firstFullnameError != null) Color.Red else TextSecondary,
                focusedTextColor = if (firstFullnameError != null) Color.Red else Primary,
                unfocusedTextColor = if (firstFullnameError != null) Color.Red else TextSecondary,
                focusedLeadingIconColor = if (firstFullnameError != null) Color.Red else Primary,
                unfocusedLeadingIconColor = if (firstFullnameError != null) Color.Red else TextSecondary,
                errorBorderColor = Color.Red,
                errorLabelColor = Color.Red,
                errorLeadingIconColor = Color.Red,
                errorTextColor = Color.Red,
                errorCursorColor = Color.Red,
                errorTrailingIconColor = Color.Red,
            )
        )

        if (firstFullnameError != null) {
            Text(
                text = firstFullnameError.toUserMessage(context),
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}