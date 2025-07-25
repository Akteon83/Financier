package com.atech.financier.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

enum class ColorMode(val primary: Color, val pale: Color, val dark: Color) {
    PURPLE(PurplePrimary, PurpleSecondaryPale, PurpleSecondaryDark),
    GREEN(GreenPrimary, GreenSecondaryPale, GreenSecondaryDark),
    BLUE(BluePrimary, BlueSecondaryPale, BlueSecondaryDark),
}

private fun darkColorScheme(colorMode: ColorMode) = darkColorScheme(
    primary = colorMode.primary,
    secondary = colorMode.dark,
    secondaryContainer = colorMode.dark,
    onSecondaryContainer = colorMode.primary,
)

private fun lightColorScheme(colorMode: ColorMode) = lightColorScheme(
    primary = colorMode.primary,
    secondary = colorMode.pale,
    secondaryContainer = colorMode.pale,
    onSecondaryContainer = colorMode.primary,
)

@Composable
fun FinancierTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    colorMode: ColorMode = ColorMode.GREEN,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) darkColorScheme(colorMode) else lightColorScheme(colorMode)

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
