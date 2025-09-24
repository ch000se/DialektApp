package com.example.dialektapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Primary,
    onPrimary = Color.White,
    primaryContainer = PrimaryDark,
    onPrimaryContainer = Color.White,

    secondary = Secondary,
    onSecondary = Color(0xFF2D2400),
    secondaryContainer = SecondaryDark,
    onSecondaryContainer = Color.White,

    tertiary = Accent,
    onTertiary = Color.White,
    tertiaryContainer = AccentDark,
    onTertiaryContainer = Color.White,

    background = BackgroundDark,
    onBackground = TextPrimaryDark,

    surface = SurfaceDark,
    onSurface = TextPrimaryDark,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = TextSecondaryDark,

    outline = BorderColorDark,
    outlineVariant = DividerColorDark,

    error = Error,
    onError = Color.White,
    errorContainer = Color(0xFF640F0A),
    onErrorContainer = Color(0xFFFFEDEA),

    inverseSurface = Surface,
    inverseOnSurface = TextPrimary,
    inversePrimary = Primary,

    scrim = ScrimColor,
    surfaceTint = Primary
)

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = Color.White,
    primaryContainer = PrimaryLight,
    onPrimaryContainer = Color(0xFF002F5E),

    secondary = Secondary,
    onSecondary = Color(0xFF2D2400),
    secondaryContainer = SecondaryLight,
    onSecondaryContainer = Color(0xFF2D2400),

    tertiary = Accent,
    onTertiary = Color.White,
    tertiaryContainer = AccentLight,
    onTertiaryContainer = Color(0xFF450600),

    background = Background,
    onBackground = TextPrimary,

    surface = Surface,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = TextSecondary,

    outline = BorderColor,
    outlineVariant = DividerColor,

    error = Error,
    onError = Color.White,
    errorContainer = Color(0xFFFFE1DE),
    onErrorContainer = Color(0xFF5F140E),

    inverseSurface = Color(0xFF1D2732),
    inverseOnSurface = Color(0xFFF1F4F7),
    inversePrimary = PrimaryLight,

    scrim = ScrimColor,
    surfaceTint = Primary
)

@Composable
fun DialektAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = if (darkTheme) {
                BackgroundDark.toArgb()
            } else {
                Primary.toArgb()
            }
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}