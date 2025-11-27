package com.devgroup.spirioskelar.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.util.lerp

fun lerpColor(start: Color, end: Color, fraction: Float): Color {
    return Color(
        red = lerp(start.red, end.red, fraction),
        green = lerp(start.green, end.green, fraction),
        blue = lerp(start.blue, end.blue, fraction),
        alpha = lerp(start.alpha, end.alpha, fraction)
    )
}

fun lerpGradient(start: List<Color>, end: List<Color>, fraction: Float): List<Color> {
    return listOf(lerpColor(start[0], end[0], fraction), lerpColor(start[1], end[1], fraction))
}

fun gradientColorForValue(value: Float): Color {
    val v = value.coerceIn(0f, 1f)

    val colorA = Color(0xFF4510A0)
    val colorB = Color(0xFFFF39E8)
    val colorC = Color(0xFFFF6F22)

    return when {
        v <= 0.5f -> {
            val t = v / 0.5f
            lerpColor(colorA, colorB, t)
        }
        else -> {
            val t = (v - 0.5f) / 0.5f
            lerpColor(colorB, colorC, t)
        }
    }
}

fun mandalaGradient(value: Float): List<Color> {
    val v = value.coerceIn(0f, 1f)

    val step1 = listOf(Color(0xFF7512FF), Color(0xFFBF00FF))
    val step2 = listOf(Color(0xFFBF00FF), Color(0xFFFFCD4F))
    val step3 = listOf(Color(0xFFF0A000), Color(0xFFFFCD4F))

    return when {
        v < 0.5f -> {
            val t = v / 0.5f
            lerpGradient(step1, step2, t)
        }
        else -> {
            val t = (v - 0.5f) / 0.5f
            lerpGradient(step2, step3, t)
        }
    }
}