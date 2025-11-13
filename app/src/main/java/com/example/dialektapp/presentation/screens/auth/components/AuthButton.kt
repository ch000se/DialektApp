package com.example.dialektapp.presentation.screens.auth.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dialektapp.ui.theme.AuthContentWhite
import com.example.dialektapp.ui.theme.Primary

@Composable
fun AuthButton(
    primaryText: String,
    secondaryText: String,
    loadingState: Boolean = false,
    enabled: Boolean = true,
    onAuthClick: () -> Unit
) {
    var buttonText by remember {
        mutableStateOf(primaryText)
    }

    LaunchedEffect(key1 = loadingState) {
        buttonText = if (loadingState) {
            secondaryText
        } else {
            primaryText
        }
    }

    Button(
        onClick = { onAuthClick() },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(16.dp),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = Primary,
            contentColor = AuthContentWhite,
            disabledContainerColor = Primary.copy(alpha = 0.6f),
            disabledContentColor = AuthContentWhite.copy(alpha = 0.6f)
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 6.dp,
            pressedElevation = 2.dp,
            disabledElevation = 0.dp
        )
    ) {
        Text(
            text = buttonText,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        if(loadingState){
            Spacer(modifier = Modifier.width(8.dp))
            CircularProgressIndicator(
                modifier = Modifier.size(16.dp),
                strokeWidth = 2.dp,
                color = AuthContentWhite
            )
        }
    }
}