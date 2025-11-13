package com.example.dialektapp.presentation.screens.profile.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LogoutSection(
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onLogoutClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFEF4444)
        ),
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(vertical = 14.dp)
    ) {
        Icon(
            Icons.AutoMirrored.Filled.ExitToApp,
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Вийти з акаунту",
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}