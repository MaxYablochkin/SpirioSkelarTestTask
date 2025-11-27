package com.devgroup.spirioskelar.mandala

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

class MandalaViewModel : ViewModel() {
    private val _state = MutableStateFlow(MandalaState())
    val state: StateFlow<MandalaState> = _state.asStateFlow()

    private val _events = MutableSharedFlow<MandalaEvent>(0, 1, BufferOverflow.DROP_OLDEST)
    val events: SharedFlow<MandalaEvent> = _events.asSharedFlow()

    fun onAction(action: MandalaAction) {
        when (action) {
            is MandalaAction.OnChangeSliderValue -> onChangeSliderValue(action.value)
            MandalaAction.OnNavigateBack -> onNavigateBack()
        }
    }

    private fun onChangeSliderValue(value: Float) {
        _state.update { state ->
            state.copy(sliderValue = value)
        }
    }

    private fun onNavigateBack() {
        viewModelScope.launch {
            _events.tryEmit(MandalaEvent.NavEvent.Back)
        }
    }
}