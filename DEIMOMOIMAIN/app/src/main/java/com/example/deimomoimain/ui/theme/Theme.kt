package com.example.deimomoimain.ui.theme

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

private val LightColorScheme = lightColorScheme(
    primary = AppOrange, // A general primary
    secondary = AppBlue,
    tertiary = AppGreen,
    background = AppBackgroundBrown, // Main screen background
    surface = AppLightBrown, // Card backgrounds
    onPrimary = AppTextWhite,
    onSecondary = AppTextWhite,
    onTertiary = AppTextWhite,
    onBackground = AppTextWhite, // Text on main background
    onSurface = AppTextWhite,     // Text on cards
    error = AppRed,
    onError = AppTextWhite
)

// For dark theme, you might want different shades or to invert some
private val DarkColorScheme = darkColorScheme(
    primary = AppOrange,
    secondary = AppBlue,
    tertiary = AppGreen,
    background = Color(0xFF1C1B1F),
    surface = Color(0xFF3A2E2C),
    onPrimary = AppTextBlack,
    onSecondary = AppTextBlack,
    onTertiary = AppTextBlack,
    onBackground = AppTextWhite,
    onSurface = AppTextWhite,
    error = AppRed,
    onError = AppTextBlack
)

@Composable
fun DEIMOMOIMAINTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
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
            window.statusBarColor = colorScheme.primary.toArgb() // Or a specific color
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography, // Use our custom typography
        content = content
    )
}