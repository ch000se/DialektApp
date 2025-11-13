package com.example.dialektapp.presentation.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dialektapp.R
import com.example.dialektapp.domain.model.User
import com.example.dialektapp.presentation.screens.profile.components.*
import com.example.dialektapp.ui.theme.DialektAppTheme

// Тимчасовий UI State до підключення ViewModel
data class ProfileUiState(
    val user: User? = null,
    val totalCoins: Int = 0,
    val weeklyCoins: Int = 0,
    val streakDays: Int = 0,
    val rankWeekly: Int? = null,
    val rankAllTime: Int? = null,
    val isLoggedOut: Boolean = false,
    val error: String? = null,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    onLogoutSuccess: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
    onNavigateToHelp: () -> Unit = {},
) {
    var isLoggedOut by remember { mutableStateOf(false) }

    // Тимчасові mock дані
    val mockUser = remember {
        User(
            id = "u1",
            username = "john_doe",
            email = "john.doe@example.com",
            fullName = "John Doe",
            profileImageUrl = null,
            role = "User",
        )
    }

    val uiState = ProfileUiState(
        user = mockUser,
        totalCoins = 1247,
        weeklyCoins = 120,
        streakDays = 5,
        rankWeekly = 37,
        rankAllTime = null,
        isLoggedOut = isLoggedOut,
        error = null
    )

    LaunchedEffect(isLoggedOut) {
        if (isLoggedOut) {
            onLogoutSuccess()
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        when {
            uiState.user != null -> {
                SuccessContent(
                    uiState = uiState,
                    onRefresh = { /* TODO: Implement refresh when ViewModel is ready */ },
                    onEditProfile = { /* TODO: Implement edit when ViewModel is ready */ },
                    onLogout = { isLoggedOut = true }
                )
            }
        }
    }
}

@Composable
private fun SuccessContent(
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
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        ProfileHeader(
            user = uiState.user!!,
            onEditClick = onEditProfile,
            onRefreshClick = onRefresh
        )

        Spacer(modifier = Modifier.height(24.dp))

        StatisticsSection(
            statistics = listOf(
                StatData(
                    title = "Монети",
                    value = uiState.totalCoins.toString(),
                    iconVector = ImageVector.vectorResource(R.drawable.coins),
                    color = Color(0xFFFFB800)
                ),
                StatData(
                    title = "Стрік",
                    value = uiState.streakDays.toString(),
                    icon = "🔥",
                    color = Color(0xFFFF5722)
                ),
                StatData(
                    title = "Ранг",
                    value = uiState.rankWeekly?.toString() ?: "–",
                    icon = "🏆",
                    color = Color(0xFF9C27B0)
                )
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        UserInfoCard(user = uiState.user!!)

        Spacer(modifier = Modifier.height(24.dp))

        LogoutSection(onLogoutClick = onLogout)

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    DialektAppTheme {
        SuccessContent(
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
