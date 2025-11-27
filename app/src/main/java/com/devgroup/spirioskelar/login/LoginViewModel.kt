package com.devgroup.spirioskelar.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    private val _events = MutableSharedFlow<LoginEvent>(0, 1, BufferOverflow.DROP_OLDEST)
    val events: SharedFlow<LoginEvent> = _events.asSharedFlow()

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.OnEmailChange -> onEmailChange(action.value)
            is LoginAction.OnPasswordChange -> onPasswordChange(action.value)
            LoginAction.OnMandalaClick -> onMandalaClick()
        }
    }

    private fun onEmailChange(value: String) {
        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
        val isValidEmail = emailRegex.matches(value)
        val trimEmail = value.trim()

        _state.update { state ->
            state.copy(
                emailValue = value,
                isEmailError = trimEmail.isNotEmpty() && !isValidEmail
            )
        }
    }

    private fun onPasswordChange(value: String) {
        val trimPassword = value.trim()

        _state.update { state ->
            state.copy(
                passwordValue = value,
                isPasswordError = trimPassword.isEmpty()
            )
        }
    }

    private fun onMandalaClick() {
        val email = _state.value.emailValue
        val password = _state.value.passwordValue

        if (email.isNotEmpty() && password.isNotEmpty()) {
            viewModelScope.launch {
                _events.tryEmit(LoginEvent.NavEvent.Mandala)
            }
        }
    }
}
