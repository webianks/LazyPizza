package com.webianks.lazypizza.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = TextOnPrimary,
    primaryContainer = Primary8,
    onPrimaryContainer = TextPrimary,

    secondary = TextSecondary,
    onSecondary = TextOnPrimary,
    secondaryContainer = TextSecondary8,
    onSecondaryContainer = TextPrimary,

    tertiary = PrimaryGradientStart,
    onTertiary = TextOnPrimary,

    background = Background,
    onBackground = TextPrimary,

    surface = Surface,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceHigher,
    onSurfaceVariant = TextSecondary,

    outline = Outline,
    outlineVariant = Outline50,

    surfaceTint = Primary,
    scrim = ShadowColor,
    inverseSurface = SurfaceHighest,
    inverseOnSurface = TextPrimary,
    inversePrimary = PrimaryGradientEnd,
)

@Composable
fun LazyPizzaTheme(
    typography: AppTypography = AppTypographyDefaults,
    colorScheme: ColorScheme = LightColorScheme,
    content: @Composable () -> Unit
) {

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = true
        )
    }

    CompositionLocalProvider(LocalAppTypography provides typography) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = typography.m3,
            content = content
        )
    }
}