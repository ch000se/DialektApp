package com.example.dialektapp.presentation.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.dialektapp.R
import com.example.dialektapp.domain.model.User
import com.example.dialektapp.presentation.screens.profile.components.*
import com.example.dialektapp.ui.theme.DialektAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    onLogoutSuccess: () -> Unit = {},
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isLoggedOut) {
        if (uiState.isLoggedOut) {
            onLogoutSuccess()
        }
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        when {
            uiState.isLoading && uiState.user == null -> {
                LoadingContent()
            }

            uiState.error != null && uiState.user == null -> {
                ErrorContent(
                    error = uiState.error ?: "Помилка завантаження",
                    onRetry = { viewModel.loadProfileData() }
                )
            }

            uiState.user != null -> {
                ProfileContent(
                    uiState = uiState,
                    onRefresh = { viewModel.refreshProfileData() },
                    onEditProfile = { /* TODO: Implement edit profile */ },
                    onLogout = { viewModel.logout() }
                )
            }
        }
    }
}

@Composable
private fun LoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                color = Color.White,
                strokeWidth = 3.dp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Завантаження профілю...",
                color = Color.White.copy(alpha = 0.8f),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun ErrorContent(
    error: String,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(32.dp)
        ) {
            Icon(
                Icons.Default.ErrorOutline,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.8f),
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = error,
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color(0xFF007AFF)
                )
            ) {
                Icon(Icons.Default.Refresh, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Спробувати знову")
            }
        }
    }
}

@Composable
private fun ProfileContent(
    uiState: ProfileUiState,
    onRefresh: () -> Unit,
    onEditProfile: () -> Unit,
    onLogout: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Profile Header with Avatar
        ProfileHeader(
            user = uiState.user!!,
            onEditClick = onEditProfile,
            onRefreshClick = onRefresh
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Statistics Grid (2x2)
        StatisticsGrid(
            totalCoins = uiState.totalCoins,
            weeklyCoins = uiState.weeklyCoins,
            streakDays = uiState.streakDays,
            rankWeekly = uiState.rankWeekly
        )

        Spacer(modifier = Modifier.height(24.dp))

        // User Info Card
        UserInfoCard(user = uiState.user!!)

        Spacer(modifier = Modifier.height(24.dp))

        // Logout Button
        LogoutSection(onLogoutClick = onLogout)

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun StatisticsGrid(
    totalCoins: Int,
    weeklyCoins: Int,
    streakDays: Int,
    rankWeekly: Int?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // First Row: Монети та Тижневі монети
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatsCard(
                title = "Монети",
                value = totalCoins.toString(),
                iconVector = ImageVector.vectorResource(R.drawable.coins),
                color = Color(0xFFFFB800),
                modifier = Modifier.weight(1f)
            )

            StatsCard(
                title = "Тижневі",
                value = weeklyCoins.toString(),
                iconVector = ImageVector.vectorResource(R.drawable.coins),
                color = Color(0xFF4CAF50),
                modifier = Modifier.weight(1f)
            )
        }

        // Second Row: Стрік та Ранг
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatsCard(
                title = "Стрік",
                value = streakDays.toString(),
                icon = "🔥",
                color = Color(0xFFFF5722),
                modifier = Modifier.weight(1f)
            )

            StatsCard(
                title = "Ранг",
                value = rankWeekly?.toString() ?: "–",
                icon = "🏆",
                color = Color(0xFF9C27B0),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun StatsCard(
    title: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier,
    icon: String? = null,
    iconVector: ImageVector? = null,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Icon
            if (iconVector != null) {
                Icon(
                    imageVector = iconVector,
                    contentDescription = title,
                    tint = color,
                    modifier = Modifier.size(32.dp)
                )
            } else if (icon != null) {
                Text(
                    text = icon,
                    style = MaterialTheme.typography.headlineMedium
                )
            }

            // Value
            Text(
                text = value,
                style = MaterialTheme.typography.headlineLarge,
                color = color
            )

            // Title
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF666666)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    DialektAppTheme {
        ProfileContent(
            uiState = ProfileUiState(
                user = User(
                    id = "u1",
                    username = "john_doe",
                    email = "john.doe@example.com",
                    fullName = "John Doe",
                    profileImageUrl = null,
                    role = "User",
                ),
                totalCoins = 1247,
                weeklyCoins = 120,
                streakDays = 5,
                rankWeekly = 37,
                rankAllTime = null
            ),
            onRefresh = {},
            onEditProfile = {},
            onLogout = {}
        )
    }
}
