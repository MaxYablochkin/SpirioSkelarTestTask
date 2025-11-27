@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package com.devgroup.spirioskelar.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.MotionScheme
import androidx.compose.material3.RippleConfiguration
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun SpirioSKELARTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
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

    CompositionLocalProvider(LocalRippleConfiguration provides rippleConfiguration) {
        MaterialExpressiveTheme(
            colorScheme = colorScheme,
            typography = Typography,
            motionScheme = MotionScheme.standard(),
            content = content
        )
    }
}

const val DraggedStateLayerOpacity = 0.16f
const val FocusStateLayerOpacity = 0.1f
const val HoverStateLayerOpacity = 0.08f
const val PressedStateLayerOpacity = 0.25f

val rippleConfiguration = RippleConfiguration(
    rippleAlpha = RippleAlpha(
        draggedAlpha = DraggedStateLayerOpacity,
        focusedAlpha = FocusStateLayerOpacity,
        hoveredAlpha = HoverStateLayerOpacity,
        pressedAlpha = PressedStateLayerOpacity
    )
)