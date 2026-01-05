package com.example.deimomoimain.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CustomViewModel : ViewModel() {
    private val _roomCode = MutableStateFlow("")
    val roomCode: StateFlow<String> = _roomCode.asStateFlow()

    fun onRoomCodeChange(newCode: String) {
        // Limit room code length if needed, e.g., to 4-6 digits
        if (newCode.length <= 6 && newCode.all { it.isDigit() }) { // Example: max 6 digits
            _roomCode.value = newCode
        }
    }
}