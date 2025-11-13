package com.example.dialektapp.presentation.screens.auth.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.dialektapp.ui.theme.AuthBackgroundWhite
import com.example.dialektapp.ui.theme.AuthIconBlack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAuthBar(
    text: String,
    onBackClick: () -> Unit = {},
) {
    TopAppBar(
        modifier = Modifier
            .shadow(8.dp),
        title = {
            Text(
                text = text,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onBackClick
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back to Login",
                    tint = AuthIconBlack
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = AuthBackgroundWhite.copy(alpha = 0.95f),
            titleContentColor = AuthIconBlack,
        )
    )
}