package com.devgroup.spirioskelar.login

sealed interface LoginEvent {
    sealed interface NavEvent : LoginEvent {
        data object Mandala : NavEvent
    }
}