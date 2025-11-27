@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)

package com.devgroup.spirioskelar.ui.components

import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.rememberSliderState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import com.devgroup.spirioskelar.mandala.MandalaAction
import com.devgroup.spirioskelar.ui.theme.Primary
import com.devgroup.spirioskelar.ui.theme.PurpleLight
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlin.math.abs

@Composable
fun SpirioSlider(
    value: Float,
    onAction: (MandalaAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()
    val sliderState = rememberSliderState(value)
    var targetValue by rememberSaveable { mutableFloatStateOf(sliderState.value) }

    sliderState.shouldAutoSnap = false

    sliderState.onValueChange = { value ->
        targetValue = value
        if (sliderState.isDragging) sliderState.value = value

        /*onAction(MandalaAction.OnChangeSliderValue(value))*/
    }

    sliderState.onValueChangeFinished = {
        val snapPoints = listOf(0f, .5f, 1f)
        val targetSnapped = snapPoints.minBy { abs(it - targetValue) }

        coroutineScope.launch {
            animate(
                initialValue = sliderState.value,
                targetValue = targetSnapped,
                animationSpec = tween(),
            ) { animatedValue, _ ->
                sliderState.value = animatedValue
            }
        }
    }

    LaunchedEffect(Unit) {
        snapshotFlow { sliderState.value }
            .distinctUntilChanged()
            .collect { onAction(MandalaAction.OnChangeSliderValue(it)) }
    }

    val sliderColors = SliderDefaults.colors(
        thumbColor = Primary,
        activeTrackColor = Primary,
        inactiveTrackColor = PurpleLight.copy(alpha = .2f),
    )

    Slider(
        state = sliderState,
        modifier = modifier,
        colors = sliderColors,
        track = {
            SliderDefaults.Track(
                sliderState = sliderState,
                drawStopIndicator = null,
                colors = sliderColors
            )
        }
    )
}

