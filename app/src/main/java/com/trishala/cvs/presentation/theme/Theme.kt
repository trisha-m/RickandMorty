package com.trishala.cvs.presentation.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext


private val LightScheme = lightColorScheme(
    background = Color.White,
    surface = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black
    // optional: primary/secondary if you want consistent accents
)

@Composable
fun RickAndMortyCVSTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightScheme,
        typography = Typography,
        content = content
    )
}