package com.example.dialektapp.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/**
 * Collection of predefined gradients for the DialektApp
 */
object AppGradients {

    // Main brand gradient
    val primaryGradient = Brush.verticalGradient(
        colors = listOf(GradientStart, GradientMiddle, GradientEnd)
    )

    // Diagonal brand gradient
    val primaryDiagonalGradient = Brush.linearGradient(
        colors = listOf(GradientStart, GradientMiddle, GradientEnd)
    )

    // Ocean gradient - cool and calming
    val oceanGradient = Brush.verticalGradient(
        colors = listOf(OceanStart, OceanMiddle, OceanEnd)
    )

    // Sunset gradient - warm and energetic
    val sunsetGradient = Brush.verticalGradient(
        colors = listOf(SunsetStart, SunsetMiddle, SunsetEnd)
    )

    // Success gradient
    val successGradient = Brush.verticalGradient(
        colors = listOf(StateSuccess.copy(alpha = 0.8f), StateSuccess)
    )

    // Error gradient
    val errorGradient = Brush.verticalGradient(
        colors = listOf(StateError.copy(alpha = 0.8f), StateError)
    )

    // Card overlay gradient
    val cardOverlayGradient = Brush.verticalGradient(
        colors = listOf(
            Color.White.copy(alpha = 0.9f),
            Color.White.copy(alpha = 0.95f)
        )
    )

    // Dark card overlay gradient
    val darkCardOverlayGradient = Brush.verticalGradient(
        colors = listOf(
            SurfaceDark.copy(alpha = 0.9f),
            SurfaceDark.copy(alpha = 0.95f)
        )
    )

    // Shimmer gradient for loading states
    val shimmerGradient = Brush.linearGradient(
        colors = listOf(
            Color.Gray.copy(alpha = 0.3f),
            Color.Gray.copy(alpha = 0.1f),
            Color.Gray.copy(alpha = 0.3f)
        )
    )
}

/**
 * Extension functions for creating custom gradients
 */
fun createVerticalGradient(colors: List<Color>): Brush {
    return Brush.verticalGradient(colors = colors)
}

fun createHorizontalGradient(colors: List<Color>): Brush {
    return Brush.horizontalGradient(colors = colors)
}

fun createDiagonalGradient(colors: List<Color>): Brush {
    return Brush.linearGradient(colors = colors)
}