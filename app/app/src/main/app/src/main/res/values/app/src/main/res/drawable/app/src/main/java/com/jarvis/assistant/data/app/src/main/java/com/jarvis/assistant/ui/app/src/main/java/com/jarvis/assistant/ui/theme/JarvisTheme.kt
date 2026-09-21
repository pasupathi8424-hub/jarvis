package com.jarvis.assistant.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object JarvisColors {
    val Background = Color(0xFF050B14)
    val Panel = Color(0xFF0B1826)
    val Cyan = Color(0xFF00E5FF)
    val CyanDim = Color(0xFF00A8BA)
    val UserBubble = Color(0xFF12314A)
    val TextPrimary = Color(0xFFE6FBFF)
}

private val JarvisColorScheme = darkColorScheme(
    primary = JarvisColors.Cyan,
    onPrimary = Color.Black,
    background = JarvisColors.Background,
    onBackground = JarvisColors.TextPrimary,
    surface = JarvisColors.Panel,
    onSurface = JarvisColors.TextPrimary
)

@Composable
fun JarvisTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = JarvisColorScheme,
        content = content
    )
}
