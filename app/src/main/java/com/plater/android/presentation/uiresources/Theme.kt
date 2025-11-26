package com.plater.android.presentation.uiresources

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = Primary60,
    onPrimary = NeutralBlack,
    secondary = Secondary60,
    onSecondary = NeutralBlack,
    tertiary = Primary40,
    onTertiary = NeutralBlack,
    background = NeutralGray1,
    onBackground = NeutralWhite,
    surface = NeutralGray2,
    onSurface = NeutralWhite,
    error = WarningSecondary,
    onError = NeutralBlack
)

private val LightColorScheme = lightColorScheme(
    primary = Primary100,
    onPrimary = NeutralWhite,
    secondary = Secondary100,
    onSecondary = NeutralWhite,
    tertiary = Primary60,
    onTertiary = NeutralBlack,
    background = NeutralWhite,
    onBackground = NeutralBlack,
    surface = NeutralWhite,
    onSurface = NeutralBlack,
    error = WarningPrimary,
    onError = NeutralWhite
)

@Composable
fun PlateRTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = plateRTypography(),
        content = content
    )
}