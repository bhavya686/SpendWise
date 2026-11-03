package com.bhavya.spendwise.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val SpendWiseLightColorScheme = lightColorScheme(
    primary = ForestGreen,
    onPrimary = WarmCream,

    primaryContainer = SageGreen,
    onPrimaryContainer = ForestGreen,

    secondary = SageGreen,
    onSecondary = ForestGreen,

    secondaryContainer = WarmCream,
    onSecondaryContainer = ForestGreen,

    tertiary = MutedTerracotta,
    onTertiary = SurfaceWhite,

    background = AppBackground,
    onBackground = PrimaryText,

    surface = SurfaceWhite,
    onSurface = PrimaryText,

    surfaceVariant = WarmCream,
    onSurfaceVariant = SecondaryText,

    error = ErrorRed,
    onError = SurfaceWhite,

    outline = DividerColor
)

@Composable
fun SpendWiseTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = SpendWiseLightColorScheme,
        typography = SpendWiseTypography,
        content = content
    )
}
