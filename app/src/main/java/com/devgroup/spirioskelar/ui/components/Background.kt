package com.devgroup.spirioskelar.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.dp
import com.devgroup.spirioskelar.mandala.LocalSliderValue
import com.devgroup.spirioskelar.ui.theme.Purple
import com.devgroup.spirioskelar.ui.theme.PurpleDark
import com.devgroup.spirioskelar.ui.theme.PurpleDim
import com.devgroup.spirioskelar.ui.theme.PurpleLight
import com.devgroup.spirioskelar.ui.theme.White
import com.devgroup.spirioskelar.util.gradientColorForValue

@Composable
fun SpirioBackgroundBox(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    val shaderBrush = remember {
        object : ShaderBrush() {
            override fun createShader(size: Size): Shader {
                return RadialGradientShader(
                    center = Offset(x = size.width, y = 0f),
                    radius = size.width,
                    colors = listOf(White, PurpleLight, Purple, PurpleDark, PurpleDim)
                )
            }
        }
    }

    Box {
        Canvas(
            modifier = modifier
                .fillMaxSize()
                .blur(100.dp),
            onDraw = {
                drawCircle(
                    brush = shaderBrush,
                    radius = size.width + size.height,
                    center = Offset(x = size.width, y = 0f)
                )
            }
        )
        content()
    }
}

@Composable
fun SpirioAnimatedBackgroundBox(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val value = LocalSliderValue.current

    val shaderBrush = remember(value) {
        object : ShaderBrush() {
            override fun createShader(size: Size): Shader {
                return RadialGradientShader(
                    center = size.center,
                    radius = size.width / 2f,
                    colors = listOf(gradientColorForValue(value), Color.Unspecified)
                )
            }
        }
    }

    Box {
        Canvas(
            modifier = modifier
                .fillMaxSize()
                .blur(250.dp),
            onDraw = {
                drawRect(color = PurpleDim)
                withTransform({
                    rotate(-7f)
                    translate(left = size.center.x / 2f, top = -100f)
                }) {
                    drawOval(
                        color = gradientColorForValue(value).copy(alpha = .5f),
                        size = Size(size.width / 2f, size.height),
                    )
                }
                translate(top = -250f) {
                    drawCircle(
                        brush = shaderBrush,
                        radius = size.minDimension / 2f,
                        center = size.center
                    )
                }
            }
        )
        content()
    }
}
