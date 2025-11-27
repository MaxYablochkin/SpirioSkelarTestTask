package com.devgroup.spirioskelar.login

sealed interface LoginAction {
    data class OnEmailChange(val value: String) : LoginAction
    data class OnPasswordChange(val value: String) : LoginAction
    data object OnMandalaClick : LoginAction
}