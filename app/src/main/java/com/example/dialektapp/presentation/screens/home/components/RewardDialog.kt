package com.example.dialektapp.presentation.screens.home.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.dialektapp.R
import com.example.dialektapp.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun RewardDialog(
    day: Int,
    amount: Int,
    isClaimingReward: Boolean = false,
    claimErrorMessage: String? = null,
    onDismiss: () -> Unit,
    onClaim: (Int) -> Unit,
) {
    Dialog(onDismissRequest = { if (!isClaimingReward) onDismiss() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = LightPrimary
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val infiniteTransition = rememberInfiniteTransition(label = "rewardSpin")
                val rotation by infiniteTransition.animateFloat(
                    initialValue = 0f,
                    targetValue = 360f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(3000, easing = LinearEasing),
                        repeatMode = RepeatMode.Restart
                    ),
                    label = "rotation"
                )

                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(
                            brush = RewardGradient,
                            shape = CircleShape
                        )
                        .graphicsLayer {
                            rotationZ = rotation
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.coins),
                        contentDescription = "Reward",
                        tint = StreakRewardGold,
                        modifier = Modifier.size(40.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Щоденна нагорода!",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimaryLight,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "День $day в додатку",
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextSecondaryLight,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                var animatedCoins by remember { mutableStateOf(0) }

                LaunchedEffect(amount) {
                    for (i in 0..amount) {
                        animatedCoins = i
                        delay(30)
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.coins),
                        contentDescription = "Coins",
                        tint = StreakRewardGold,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "+$animatedCoins",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = StreakRewardGold,
                        fontSize = 32.sp
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Показуємо помилку якщо є
                if (claimErrorMessage != null) {
                    Text(
                        text = claimErrorMessage,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                Button(
                    onClick = { onClaim(amount) },
                    enabled = !isClaimingReward,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent
                    ),
                    contentPadding = PaddingValues(0.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = if (isClaimingReward) {
                                    Brush.horizontalGradient(
                                        colors = listOf(
                                            AccentGold.copy(alpha = 0.5f),
                                            AccentGold.copy(alpha = 0.7f)
                                        )
                                    )
                                } else {
                                    GoldGradient
                                },
                                shape = RoundedCornerShape(16.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (isClaimingReward) {
                            CircularProgressIndicator(
                                color = TextPrimaryDark,
                                modifier = Modifier.size(24.dp),
                                strokeWidth = 3.dp
                            )
                        } else {
                            Text(
                                text = "Забрати нагороду!",
                                color = TextPrimaryDark,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                TextButton(
                    onClick = onDismiss,
                    enabled = !isClaimingReward
                ) {
                    Text(
                        text = "Пізніше",
                        color = if (isClaimingReward) {
                            TextSecondaryLight.copy(alpha = 0.5f)
                        } else {
                            TextSecondaryLight
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun RewardDialogPreview() {
    RewardDialog(
        day = 5,
        amount = 50,
        onDismiss = { },
        onClaim = { }
    )
}