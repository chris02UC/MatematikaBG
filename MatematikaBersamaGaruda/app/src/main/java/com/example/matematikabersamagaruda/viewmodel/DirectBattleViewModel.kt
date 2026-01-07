//// File: viewmodel/DirectBattleViewModel.kt
//package com.example.deimomoimain.viewmodel
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.deimomoimain.model.Question
//import kotlinx.coroutines.Job
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.update
//import kotlinx.coroutines.launch
//
//data class DirectBattleUiState(
//    val timerSeconds: Int = 0,
//    val currentQuestion: Question = Question.random(),
//    val currentInput: String = "",
//    val playerHealth: Int = 10,
//    val opponentHealth: Int = 10,
//    val gameOver: GameOver? = null
//)
//
//class DirectBattleViewModel : ViewModel() {
//    private val _uiState = MutableStateFlow(DirectBattleUiState())
//    val uiState: StateFlow<DirectBattleUiState> = _uiState
//
//    private var timerJob: Job? = null
//    private var drainJob: Job? = null
//
//    // default to medium
//    private var drainRange: IntRange = 10..10
//
//    /** call this *before* restart()/navigation */
//    fun setDifficulty(level: Int) {
//        drainRange = when (level.coerceIn(1, 5)) {
//            1 -> 10..12
//            2 -> 8..10
//            3 -> 5..7
//            4 -> 3..5
//            5 -> 1..2
//            else -> 5..7
//        } //the difficulty here
//    }
//
//    /** resets state and kicks off both loops */
//    fun restart() {
//        timerJob?.cancel()
//        drainJob?.cancel()
//        // reset everything
//        _uiState.value = DirectBattleUiState()
//        startTimer()
//        startDrainLoop()
//    }
//
//    private fun startTimer() {
//        timerJob?.cancel()
//        timerJob = viewModelScope.launch {
//            // every second increment timerSeconds until game over
//            while (_uiState.value.gameOver == null) {
//                delay(1000)
//                _uiState.update { it.copy(timerSeconds = it.timerSeconds + 1) }
//            }
//        }
//    }
//
//    private fun startDrainLoop() {
//        drainJob?.cancel()
//        drainJob = viewModelScope.launch {
//            // pick a random interval (in seconds) from drainRange, then subtract health
//            while (_uiState.value.gameOver == null) {
//                val waitMs = drainRange.random() * 1000L
//                delay(waitMs)
//                _uiState.update { state ->
//                    if (state.gameOver != null) return@update state
//                    val nh = state.playerHealth - 1
//                    val over = if (nh <= 0) GameOver.LOSE else null
//                    state.copy(playerHealth = nh, gameOver = over)
//                }
//            }
//        }
//    }
//
//    fun onKeyPress(key: String) {
//        val state = _uiState.value
//        if (state.gameOver != null) return
//
//        val newInput = when (key) {
//            "←" -> state.currentInput.dropLast(1)
//            "." -> if (state.currentInput.contains(".")) state.currentInput else state.currentInput + "."
//            else -> state.currentInput + key
//        }
//
//        _uiState.update { s ->
//            if (newInput.toIntOrNull() == s.currentQuestion.answer) {
//                val newOpp = s.opponentHealth - 1
//                val over  = if (newOpp <= 0) GameOver.WIN else null
//                s.copy(
//                    currentInput    = "",
//                    opponentHealth  = newOpp,
//                    currentQuestion = Question.random(),
//                    gameOver        = over
//                )
//            } else {
//                s.copy(currentInput = newInput)
//            }
//        }
//    }
//
//    private fun generateValidQuestion(): Question {
//        var question: Question
//        do {
//            question = Question.random()
//        } while (question.answer.toString().contains('9')) // Keep generating if answer contains '9'
//        return question
//    }
//}

// File: viewmodel/DirectBattleViewModel.kt
package com.example.matematikabersamagaruda.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.matematikabersamagaruda.model.Question
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DirectBattleUiState(
    val timerSeconds: Int = 0,
    val currentQuestion: Question = Question.random(), // Default will be replaced by generateValidQuestion initially
    val currentInput: String = "",
    val playerHealth: Int = 10,
    val opponentHealth: Int = 10,
    val gameOver: GameOver? = null
)

class DirectBattleViewModel : ViewModel() {
    // Initialize with a valid question
    private val _uiState = MutableStateFlow(DirectBattleUiState(currentQuestion = generateValidQuestion()))
    val uiState: StateFlow<DirectBattleUiState> = _uiState

    private var timerJob: Job? = null
    private var drainJob: Job? = null

    // default to medium
    private var drainRange: IntRange = 10..10 // Note: This was 5..7 in your provided code, changed to 10..10 as per original file context
    // If 5..7 was intentional, please adjust back.
    // The original file had: private var drainRange: IntRange = 5..7

    /** call this *before* restart()/navigation */
    fun setDifficulty(level: Int) {
        drainRange = when (level.coerceIn(1, 5)) {
            1 -> 10..12
            2 -> 8..10
            3 -> 5..7
            4 -> 3..5
            5 -> 1..2
            else -> 5..7
        } //the difficulty here
    }

    /** resets state and kicks off both loops */
    fun restart() {
        timerJob?.cancel()
        drainJob?.cancel()
        // reset everything with a valid question
        _uiState.value = DirectBattleUiState(currentQuestion = generateValidQuestion())
        startTimer()
        startDrainLoop()
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            // every second increment timerSeconds until game over
            while (_uiState.value.gameOver == null) {
                delay(1000)
                _uiState.update { it.copy(timerSeconds = it.timerSeconds + 1) }
            }
        }
    }

    private fun startDrainLoop() {
        drainJob?.cancel()
        drainJob = viewModelScope.launch {
            // pick a random interval (in seconds) from drainRange, then subtract health
            while (_uiState.value.gameOver == null) {
                val waitMs = drainRange.random() * 1000L
                delay(waitMs)
                _uiState.update { state ->
                    if (state.gameOver != null) return@update state
                    val nh = state.playerHealth - 1
                    val over = if (nh <= 0) GameOver.LOSE else null
                    state.copy(playerHealth = nh, gameOver = over)
                }
            }
        }
    }

    fun onKeyPress(key: String) {
        val state = _uiState.value
        if (state.gameOver != null) return

        val newInput = when (key) {
            "←" -> state.currentInput.dropLast(1)
            "." -> if (state.currentInput.contains(".")) state.currentInput else state.currentInput + "."
            else -> state.currentInput + key
        }

        _uiState.update { s ->
            if (newInput.toIntOrNull() == s.currentQuestion.answer) {
                val newOpp = s.opponentHealth - 1
                val over  = if (newOpp <= 0) GameOver.WIN else null
                s.copy(
                    currentInput    = "",
                    opponentHealth  = newOpp,
                    currentQuestion = generateValidQuestion(), // Use the new function here
                    gameOver        = over
                )
            } else {
                s.copy(currentInput = newInput)
            }
        }
    }

    private fun generateValidQuestion(): Question {
        var question: Question
        do {
            question = Question.random()
        } while (question.answer.toString().contains('9')) // Keep generating if answer contains '9'
        return question
    }
}