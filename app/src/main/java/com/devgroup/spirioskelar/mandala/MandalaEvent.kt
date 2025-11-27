package com.devgroup.spirioskelar.mandala

sealed interface MandalaEvent {
    sealed interface NavEvent : MandalaEvent {
        data object Back : NavEvent
    }
}