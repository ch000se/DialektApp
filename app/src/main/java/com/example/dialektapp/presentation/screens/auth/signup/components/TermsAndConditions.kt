package com.example.dialektapp.presentation.screens.auth.signup.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dialektapp.domain.util.ValidationError
import com.example.dialektapp.presentation.util.toUserMessage
import com.example.dialektapp.ui.theme.AuthErrorRed
import com.example.dialektapp.ui.theme.Primary
import com.example.dialektapp.ui.theme.TextPrimary
import com.example.dialektapp.ui.theme.TextSecondary

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TermsAndConditions(
    agreeToTerms: Boolean,
    onAgreeToTermsChange: (Boolean) -> Unit,
    onTermsClick: () -> Unit = {},
    onPrivacyClick: () -> Unit = {},
    errorHint: () -> List<ValidationError>,
) {
    val context = LocalContext.current
    val firstTermsAndConditionsError = errorHint().firstOrNull {
        it is ValidationError.TermsNotAccepted
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = agreeToTerms,
            onCheckedChange = onAgreeToTermsChange,
            colors = CheckboxDefaults.colors(
                checkedColor = TextPrimary,
                uncheckedColor = TextSecondary
            )
        )

        Spacer(modifier = Modifier.width(8.dp))

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "Я погоджуюся з ",
                style = MaterialTheme.typography.bodySmall.copy(color = TextSecondary)
            )

            Text(
                text = "Умовами використання",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Primary,
                    fontWeight = FontWeight.Medium,
                    textDecoration = TextDecoration.Underline
                ),
                modifier = Modifier.clickable { onTermsClick() }
            )

            Text(
                text = " та ",
                style = MaterialTheme.typography.bodySmall.copy(color = TextSecondary)
            )

            Text(
                text = "Політикою конфіденційності",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Primary,
                    fontWeight = FontWeight.Medium,
                    textDecoration = TextDecoration.Underline
                ),
                modifier = Modifier.clickable { onPrivacyClick() }
            )
        }
    }

    if (firstTermsAndConditionsError != null) {
        Text(
            text = firstTermsAndConditionsError.toUserMessage(context),
            color = AuthErrorRed,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TermsAndConditionsPreview() {
    TermsAndConditions(
        agreeToTerms = true,
        onAgreeToTermsChange = {},
        onTermsClick = {},
        onPrivacyClick = {},
        errorHint = { emptyList() }
    )
}
