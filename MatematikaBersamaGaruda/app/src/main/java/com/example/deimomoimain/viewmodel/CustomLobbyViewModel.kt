package com.example.deimomoimain.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.deimomoimain.R // Your R file
import com.example.deimomoimain.model.GameType // Your existing GameType model
import com.example.deimomoimain.model.InputMethod // Your existing InputMethod model
import com.example.deimomoimain.ui.theme.AppRed
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class PlayerLobbyInfo(
    val name: String,
    val avatarColor: Color,
    val isHost: Boolean
)

data class CustomLobbyState(
    val roomCode: String = "1234", // Example, should come from previous screen or server
    val player1: PlayerLobbyInfo = PlayerLobbyInfo("Momoi", AppRed, true),
    val player2: PlayerLobbyInfo? = null, // Null if awaiting
    val difficulty: Float = 2f, // Default difficulty 1-4
    val selectedGameType: GameType? = GameType.Endurance, // Default selection
    val selectedInputMethod: InputMethod? = InputMethod.Touch // Default selection
)

class CustomLobbyViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CustomLobbyState())
    val uiState: StateFlow<CustomLobbyState> = _uiState.asStateFlow()

    // Example: Call this when user joins or creates a lobby
    fun setupLobby(roomCode: String, isHost: Boolean, hostName: String = "Momoi", opponentName: String? = null) {
        _uiState.update {
            it.copy(
                roomCode = roomCode,
                player1 = if (isHost) PlayerLobbyInfo(hostName, AppRed, true)
                else PlayerLobbyInfo(hostName, AppRed, true), // Host is always P1 display wise
                player2 = if (isHost) (if (opponentName != null) PlayerLobbyInfo(opponentName, Color.Gray, false) else null)
                else PlayerLobbyInfo(hostName, Color.Gray, false) // If joining, user is P2
            )
        }
        // If current user is P2, they might not have control over settings
    }


    fun setDifficulty(newDifficulty: Float) {
        _uiState.update { it.copy(difficulty = newDifficulty.coerceIn(1f, 4f)) }
    }

    fun selectGameType(gameType: GameType) {
        _uiState.update {
            it.copy(selectedGameType = if (it.selectedGameType == gameType) null else gameType)
        }
    }

    fun selectInputMethod(inputMethod: InputMethod) {
        _uiState.update {
            it.copy(selectedInputMethod = if (it.selectedInputMethod == inputMethod) null else inputMethod)
        }
    }

    fun canProceedToBattle(): Boolean {
        return _uiState.value.selectedGameType != null &&
                _uiState.value.selectedInputMethod != null &&
                _uiState.value.player2 != null // Both players must be present
    }

    val gameTypes = listOf(GameType.Endurance, GameType.Direct)
    val inputMethods = listOf(InputMethod.Touch, InputMethod.Keypad)

}