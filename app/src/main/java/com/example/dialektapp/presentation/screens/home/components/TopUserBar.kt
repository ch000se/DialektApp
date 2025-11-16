package com.example.dialektapp.presentation.screens.home.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.request.ImageRequest
import com.example.dialektapp.R
import com.example.dialektapp.domain.model.User
import com.example.dialektapp.presentation.components.UserAvatar
import com.example.dialektapp.ui.theme.DialektAppTheme
import com.example.dialektapp.ui.theme.HomeAvatarBackgroundGray
import com.example.dialektapp.ui.theme.TextWhite

@Composable
fun TopUserBar(
    user: User?,
    onProfileClick: () -> Unit = {},
    coinsCount: Int = 0,
) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isVisible = true
    }

    val infiniteTransition = rememberInfiniteTransition(label = "coinsPulse")
    val coinsPulse by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "coinsPulse"
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        AnimatedVisibility(
            visible = isVisible,
            enter = scaleIn(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ) + fadeIn(
                animationSpec = tween(500)
            )
        ) {
            UserAvatar(
                imageUrl = user?.profileImageUrl,
                contentDescription = "Profile",
                size = 64.dp,
                backgroundColor = HomeAvatarBackgroundGray
            )
        }

        Column(
            modifier = Modifier.padding(start = 12.dp)
        ) {
            AnimatedVisibility(
                visible = isVisible,
                enter = slideInHorizontally(
                    animationSpec = tween(600, delayMillis = 200),
                    initialOffsetX = { it / 2 }
                ) + fadeIn(
                    animationSpec = tween(600, delayMillis = 200)
                )
            ) {
                Text(
                    text = user?.fullName ?: "Loading...",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = TextWhite
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            AnimatedVisibility(
                visible = isVisible,
                enter = slideInHorizontally(
                    animationSpec = tween(600, delayMillis = 400),
                    initialOffsetX = { it / 2 }
                ) + fadeIn(
                    animationSpec = tween(600, delayMillis = 400)
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.coins),
                        contentDescription = null,
                        tint = TextWhite,
                        modifier = Modifier.size(18.dp)
                    )

                    val animatedCoins by animateIntAsState(
                        targetValue = coinsCount,
                        animationSpec = tween(
                            durationMillis = 1000,
                            delayMillis = 800,
                            easing = EaseOutQuart
                        ),
                        label = "coinsCount"
                    )

                    Text(
                        text = "$animatedCoins",
                        fontSize = 18.sp,
                        color = TextWhite,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TopUserBarPreview() {
    DialektAppTheme {
        TopUserBar(
            user = User(
                id = "1",
                username = "john",
                email = "john@example.com",
                fullName = "John Doe",
                profileImageUrl = null
            ),
            coinsCount = 1448
        )
    }
}