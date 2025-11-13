package com.example.dialektapp.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.BottomSheetDefaults.ScrimColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Extended colors for custom theme
data class ExtendedColors(
    val accentPrimary: Color,
    val cardBackground: Color,
    val backgroundGradientStart: Color,
    val backgroundGradientMiddle: Color,
    val backgroundGradientEnd: Color,
    val textGray: Color,
    val borderDark: Color,

    // Rarity colors
    val rarityCommon: Color,
    val rarityRare: Color,
    val rarityEpic: Color,
    val rarityLegendary: Color,

    // State colors
    val stateSuccess: Color,
    val stateWarning: Color,
    val stateError: Color,
    val stateInfo: Color,
)

private val DarkExtendedColors = ExtendedColors(
    accentPrimary = AccentPrimary,
    cardBackground = CardBackground,
    backgroundGradientStart = BackgroundGradientStart,
    backgroundGradientMiddle = BackgroundGradientMiddle,
    backgroundGradientEnd = BackgroundGradientEnd,
    textGray = TextGray,
    borderDark = BorderDark,

    rarityCommon = RarityCommonBorder,
    rarityRare = RarityRareBorder,
    rarityEpic = RarityEpicBorder,
    rarityLegendary = RarityLegendaryBorder,

    stateSuccess = StateSuccess,
    stateWarning = StateWarning,
    stateError = StateError,
    stateInfo = StateInfo,
)

private val LightExtendedColors = ExtendedColors(
    accentPrimary = AccentPrimary,
    cardBackground = SurfaceLight,
    backgroundGradientStart = AccentBlue,
    backgroundGradientMiddle = AccentBlueDark,
    backgroundGradientEnd = AccentBlueSky,
    textGray = TextDarkSecondary,
    borderDark = BorderLight,

    rarityCommon = RarityCommonBorder,
    rarityRare = RarityRareBorder,
    rarityEpic = RarityEpicBorder,
    rarityLegendary = RarityLegendaryBorder,

    stateSuccess = StateSuccess,
    stateWarning = StateWarning,
    stateError = StateError,
    stateInfo = StateInfo,
)

val LocalExtendedColors = compositionLocalOf { DarkExtendedColors }

private val DarkColorScheme = darkColorScheme(
    primary = AccentBlue,
    onPrimary = TextWhite,
    primaryContainer = AccentBlueDark,
    onPrimaryContainer = TextWhite,

    secondary = AccentPrimary,
    onSecondary = Color.Black,
    secondaryContainer = AccentPrimaryDark,
    onSecondaryContainer = Color.Black,

    tertiary = AccentPurple,
    onTertiary = TextWhite,
    tertiaryContainer = AccentPurpleDark,
    onTertiaryContainer = TextWhite,

    background = BackgroundDark,
    onBackground = TextWhite,

    surface = CardBackground,
    onSurface = TextWhite,
    surfaceVariant = SurfaceDark,
    onSurfaceVariant = TextGray,

    outline = BorderDark,
    outlineVariant = DividerDark,

    error = AccentRed,
    onError = TextWhite,
    errorContainer = AccentRedDark,
    onErrorContainer = TextWhite,

    inverseSurface = SurfaceLight,
    inverseOnSurface = TextDark,
    inversePrimary = AccentBlue,

    scrim = ScrimDark,
    surfaceTint = AccentBlue
)

private val LightColorScheme = lightColorScheme(
    primary = AccentBlue,
    onPrimary = TextWhite,
    primaryContainer = AccentBlueLight,
    onPrimaryContainer = TextDark,

    secondary = AccentPrimary,
    onSecondary = Color.Black,
    secondaryContainer = AccentPrimaryLight,
    onSecondaryContainer = Color.Black,

    tertiary = AccentPurple,
    onTertiary = TextWhite,
    tertiaryContainer = AccentPurpleLight,
    onTertiaryContainer = TextDark,

    background = SurfaceLight,
    onBackground = TextDark,

    surface = SurfaceLight,
    onSurface = TextDark,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = TextDarkSecondary,

    outline = BorderLight,
    outlineVariant = DividerLight,

    error = AccentRed,
    onError = TextWhite,
    errorContainer = AccentRedLight,
    onErrorContainer = TextDark,

    inverseSurface = CardBackground,
    inverseOnSurface = TextWhite,
    inversePrimary = AccentBlueLight,

    scrim = ScrimDark,
    surfaceTint = AccentBlue
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

    val extendedColors = if (darkTheme) DarkExtendedColors else LightExtendedColors

    androidx.compose.runtime.CompositionLocalProvider(
        LocalExtendedColors provides extendedColors
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}

// Extension properties to access theme colors easily
object AppTheme {
    val colors: ExtendedColors
        @Composable
        @ReadOnlyComposable
        get() = LocalExtendedColors.current

    val materialColors
        @Composable
        @ReadOnlyComposable
        get() = MaterialTheme.colorScheme
}

// Helper extension for MaterialTheme
val MaterialTheme.extendedColors: ExtendedColors
    @Composable
    @ReadOnlyComposable
    get() = LocalExtendedColors.current