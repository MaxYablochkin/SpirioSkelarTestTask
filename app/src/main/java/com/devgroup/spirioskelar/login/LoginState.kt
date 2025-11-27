package com.devgroup.spirioskelar.login

data class LoginState(
    val emailValue: String = "",
    val passwordValue: String = "",
    val isEmailError: Boolean = false,
    val isPasswordError: Boolean = false
)