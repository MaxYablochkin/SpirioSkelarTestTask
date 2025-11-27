package com.devgroup.spirioskelar.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.devgroup.spirioskelar.R
import com.devgroup.spirioskelar.mandala.LocalSliderValue
import com.devgroup.spirioskelar.util.mandalaGradient

@Composable
fun Mandala(modifier: Modifier = Modifier) {
    val mandalaImage = rememberVectorPainter(ImageVector.vectorResource(R.drawable.mandala_vector))

    AnimatedTransformBox {
        Image(
            modifier = modifier,
            painter = mandalaImage,
            contentDescription = "mandala",
            colorFilter = ColorFilter.tint(Color.White),
        )
    }
}

@Composable
fun AnimatedTransformBox(content: @Composable () -> Unit) {
    val value = LocalSliderValue.current
    val colors: List<Color> = mandalaGradient(value)

    Box(
        modifier = Modifier
            .graphicsLayer {
                val scale = (value + 1f) * 1f

                rotationZ = value * 90f
                scaleX = scale
                scaleY = scale

                compositingStrategy = CompositingStrategy.Offscreen
            }
            .drawWithCache {
                val brush = Brush.linearGradient(
                    colors, Offset.Zero, Offset(size.width, size.height)
                )

                onDrawWithContent {
                    drawContent()
                    drawRect(brush, blendMode = BlendMode.SrcAtop)
                }
            }
    ) {
        content()
    }
}

@Preview
@Composable
private fun MandalaPreview() = Mandala()
