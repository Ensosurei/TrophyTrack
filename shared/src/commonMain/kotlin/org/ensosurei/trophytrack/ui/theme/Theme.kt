package org.ensosurei.trophytrack.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val ColorScheme = darkColorScheme(
    background = background,
    surface = surface,
    primary = surfaceVariant,
    secondary = pink,
    onBackground = white,
    onSurface = white,
    onPrimary = white
)

@Composable
fun TrophyTrackTheme(content: @Composable () -> Unit){
    MaterialTheme(colorScheme = ColorScheme){
        content()
    }
}