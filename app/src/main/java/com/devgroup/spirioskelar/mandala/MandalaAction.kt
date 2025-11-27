package com.devgroup.spirioskelar.mandala

sealed interface MandalaAction {
    data class OnChangeSliderValue(val value: Float) : MandalaAction
    data object OnNavigateBack : MandalaAction
}