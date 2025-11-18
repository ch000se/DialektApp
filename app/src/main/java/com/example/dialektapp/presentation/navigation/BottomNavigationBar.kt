package com.example.dialektapp.presentation.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.dialektapp.R
import com.example.dialektapp.ui.theme.*

@Composable
fun BottomNavigationBar(
    currentRoute: String? = null,
    onHomeClick: () -> Unit = {},
    onLeaderboardClick: () -> Unit = {},
    onAchievementsClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
) {
    NavigationBar(
        containerColor = Color.White,
        modifier = Modifier.fillMaxWidth()
    ) {
        val items = listOf(
            BottomNavItem(
                route = "Home",
                title = "Головна",
                selectedIcon = Icons.Default.Home,
                unselectedIcon = Icons.Default.Home,
                onClick = onHomeClick
            ),
            BottomNavItem(
                route = "Leaderboard",
                title = "Рейтинг",
                selectedIcon = ImageVector.vectorResource(R.drawable.ranking),
                unselectedIcon = ImageVector.vectorResource(R.drawable.ranking),
                onClick = onLeaderboardClick
            ),
            BottomNavItem(
                route = "Achievements",
                title = "Досягнення",
                selectedIcon = ImageVector.vectorResource(R.drawable.achievments),
                unselectedIcon = ImageVector.vectorResource(R.drawable.achievments),
                onClick = onAchievementsClick
            ),
            BottomNavItem(
                route = "Profile",
                title = "Профіль",
                selectedIcon = Icons.Default.Person,
                unselectedIcon = Icons.Default.Person,
                onClick = onProfileClick
            )
        )

        items.forEach { item ->
            val isSelected = currentRoute?.contains(item.route, ignoreCase = true) == true

            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.title,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = { Text(item.title) },
                selected = isSelected,
                onClick = item.onClick,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = AccentBlue,
                    selectedTextColor = AccentBlue,
                    unselectedIconColor = TextSecondaryDark,
                    unselectedTextColor = TextSecondaryDark,
                    indicatorColor = AccentBlue.copy(alpha = 0.12f)
                )
            )
        }
    }
}

data class BottomNavItem(
    val route: String,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val onClick: () -> Unit = {},
)