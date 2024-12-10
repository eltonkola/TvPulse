package org.eltonkola.tvpulse.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import org.eltonkola.tvpulse.expect.UpdateEdgeToEdge

private val LightColors = lightColorScheme(
    primary = Color(0xFFFFC107), // TV Time's yellow for primary buttons
    secondary = Color(0xFF1A1A1A), // Dark backgrounds for cards/secondary areas
    tertiary = Color(0xFFFAFAFA), // Light background for contrast
    onPrimary = Color.Black, // Text on yellow buttons
    onSecondary = Color.White,
    onBackground = Color.Black,
    background = Color.White,
    surface = Color(0xFFF7F7F7) // Neutral background
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFFFFC107),
    secondary = Color(0xFF242424),
    tertiary = Color(0xFF2E2E2E),
    onPrimary = Color.Black,
    onSecondary = Color.White,
    background = Color(0xFF121212),
    surface = Color(0xFF1F1F1F)
)

@Composable
fun TvPulseTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    UpdateEdgeToEdge(darkTheme)

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}

