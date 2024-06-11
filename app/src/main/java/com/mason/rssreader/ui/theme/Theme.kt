package com.mason.rssreader.ui.theme
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColorScheme(
    primary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFF045D56),
    secondary = Color(0xFF367DF8),
    secondaryContainer = Color(0xFF80CFA9)
)

private val LightColorPalette = lightColorScheme(
    primary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFF045D56),
    secondary = Color(0xFF367DF8),
    secondaryContainer = Color(0xFF80CFA9)
)

@Composable
fun RssReaderTheme(content: @Composable () -> Unit) {
    val colors = if (isSystemInDarkTheme()) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}
