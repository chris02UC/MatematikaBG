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
//import kotlin.math.abs
//import com.example.deimomoimain.viewmodel.GameOver
//
//// UI state for Endurance Battle mode
//data class EnduranceBattleUiState(
//    val currentQuestion: Question = Question.random(),
//    val currentInput: String = "",
//    val playerAnswers: Int = 0,
//    val aiAnswers: Int = 0,
//    val leadType: String = "",
//    val leadCount: Int = 0,
//    val playerHealth: Int = 5,
//    val opponentHealth: Int = 5,
//    val gameOver: GameOver? = null
//)
//
//class EnduranceBattleViewModel : ViewModel() {
//    private val _uiState = MutableStateFlow(EnduranceBattleUiState())
//    val uiState: StateFlow<EnduranceBattleUiState> = _uiState
//
//    private var aiJob: Job? = null
//    private var healthJob: Job? = null
//    private var aiSpeedRange: IntRange = 5000..7000
//    init {
//        startAiLoop()
//        startHealthLoop()
//    }
//
//    /** Call this once before you start navigating into endurance battle */
//    fun setAiDifficulty(level: Int) {
//        aiSpeedRange = when(level.coerceIn(1,5)) {
//            1 -> 10_000..12_000
//            2 -> 8_000..10_000
//            3 -> 5_000..7_000
//            4 -> 3_000..4_000
//            5 -> 1_000..2_000
//            else -> 5_000..7_000
//        }
//    }
//
//    private fun startAiLoop() {
//        aiJob?.cancel()
//        aiJob = viewModelScope.launch {
//            while (_uiState.value.gameOver == null) {
//                           // pick a delay based on the slider’s difficulty range
//                            delay(aiSpeedRange.random().toLong())
//                _uiState.update { state ->
//                    if (state.gameOver != null) return@update state
//
//                    val newAi = state.aiAnswers + 1
//                    // compute leads
//                    val diff = state.playerAnswers - newAi
//                    val (type, count) = when {
//                        diff > 0  -> "Ahead"  to diff
//                        diff < 0  -> "Behind" to abs(diff)
//                        else      -> ""       to 0
//                    }
//                    state.copy(
//                        aiAnswers = newAi,
//                        leadType  = type,
//                        leadCount = count
//                    )
//                }
//            }
//        }
//    }
//
//    private fun startHealthLoop() {
//        healthJob?.cancel()
//        healthJob = viewModelScope.launch {
//            while (_uiState.value.gameOver == null) {
//                val state   = _uiState.value
//                val diff    = state.playerAnswers - state.aiAnswers
//                val absDiff = abs(diff)
//
//                if (absDiff >= 2) {
//                    // determine new healths and potential game-over
//                    val (newPlayerHealth, newOppHealth, over) = if (diff > 0) {
//                        // player ahead → opponent loses a pip
//                        val opp = state.opponentHealth - 1
//                        Triple(state.playerHealth, opp, if (opp <= 0) GameOver.WIN else null)
//                    } else {
//                        // AI ahead → player loses a pip
//                        val pl = state.playerHealth - 1
//                        Triple(pl, state.opponentHealth, if (pl <= 0) GameOver.LOSE else null)
//                    }
//
//                    _uiState.update {
//                        it.copy(
//                            playerHealth   = newPlayerHealth,
//                            opponentHealth = newOppHealth,
//                            gameOver       = over
//                        )
//                    }
//
//                    // wait shorter when lead is larger: base 5s divided by (absDiff - 1)
//                    val interval = (5000L / (absDiff - 1)).coerceAtLeast(500L)
//                    delay(interval)
//                } else {
//                    // no health change until someone leads by >=2
//                    delay(1000)
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
//                val newPl = s.playerAnswers + 1
//                // compute new lead after correct answer
//                val diff = newPl - s.aiAnswers
//                val (type, count) = when {
//                    diff > 0  -> "Ahead"  to diff
//                    diff < 0  -> "Behind" to abs(diff)
//                    else      -> ""       to 0
//                }
//                s.copy(
//                    currentInput    = "",
//                    playerAnswers   = newPl,
//                    currentQuestion = Question.random(),
//                    leadType        = type,
//                    leadCount       = count
//                )
//            } else {
//                s.copy(currentInput = newInput)
//            }
//        }
//    }
//
//    fun restart() {
//        aiJob?.cancel()
//        healthJob?.cancel()
//        _uiState.value = EnduranceBattleUiState()
//        startAiLoop()
//        startHealthLoop()
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
import kotlin.math.abs
// import com.example.deimomoimain.viewmodel.GameOver // Already imported via .*

// UI state for Endurance Battle mode
data class EnduranceBattleUiState(
    val currentQuestion: Question = Question.random(), // Default will be replaced by generateValidQuestion initially
    val currentInput: String = "",
    val playerAnswers: Int = 0,
    val aiAnswers: Int = 0,
    val leadType: String = "",
    val leadCount: Int = 0,
    val playerHealth: Int = 5,
    val opponentHealth: Int = 5,
    val gameOver: GameOver? = null
)

class EnduranceBattleViewModel : ViewModel() {
    // Initialize with a valid question
    private val _uiState = MutableStateFlow(EnduranceBattleUiState(currentQuestion = generateValidQuestion()))
    val uiState: StateFlow<EnduranceBattleUiState> = _uiState

    private var aiJob: Job? = null
    private var healthJob: Job? = null
    private var aiSpeedRange: IntRange = 5000..7000 // Default, will be set by setAiDifficulty

    init {
        // _uiState is already initialized with a valid question.
        // startAiLoop and startHealthLoop will run with this initial valid state.
        startAiLoop()
        startHealthLoop()
    }

    /** Call this once before you start navigating into endurance battle */
    fun setAiDifficulty(level: Int) {
        aiSpeedRange = when(level.coerceIn(1,5)) {
            1 -> 10_000..12_000
            2 -> 8_000..10_000
            3 -> 5_000..7_000
            4 -> 3_000..4_000
            5 -> 1_000..2_000
            else -> 5_000..7_000
        }
    }

    private fun startAiLoop() {
        aiJob?.cancel()
        aiJob = viewModelScope.launch {
            while (_uiState.value.gameOver == null) {
                // pick a delay based on the slider’s difficulty range
                delay(aiSpeedRange.random().toLong())
                _uiState.update { state ->
                    if (state.gameOver != null) return@update state

                    val newAi = state.aiAnswers + 1
                    // compute leads
                    val diff = state.playerAnswers - newAi
                    val (type, count) = when {
                        diff > 0  -> "Ahead"  to diff
                        diff < 0  -> "Behind" to abs(diff)
                        else      -> ""       to 0
                    }
                    state.copy(
                        aiAnswers = newAi,
                        leadType  = type,
                        leadCount = count
                    )
                }
            }
        }
    }

    private fun startHealthLoop() {
        healthJob?.cancel()
        healthJob = viewModelScope.launch {
            while (_uiState.value.gameOver == null) {
                val state   = _uiState.value
                val diff    = state.playerAnswers - state.aiAnswers
                val absDiff = abs(diff)

                if (absDiff >= 2) {
                    // determine new healths and potential game-over
                    val (newPlayerHealth, newOppHealth, over) = if (diff > 0) {
                        // player ahead → opponent loses a pip
                        val opp = state.opponentHealth - 1
                        Triple(state.playerHealth, opp, if (opp <= 0) GameOver.WIN else null)
                    } else {
                        // AI ahead → player loses a pip
                        val pl = state.playerHealth - 1
                        Triple(pl, state.opponentHealth, if (pl <= 0) GameOver.LOSE else null)
                    }

                    _uiState.update {
                        it.copy(
                            playerHealth   = newPlayerHealth,
                            opponentHealth = newOppHealth,
                            gameOver       = over
                        )
                    }

                    // wait shorter when lead is larger: base 5s divided by (absDiff - 1)
                    val interval = (5000L / (absDiff - 1)).coerceAtLeast(500L)
                    delay(interval)
                } else {
                    // no health change until someone leads by >=2
                    delay(1000)
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
                val newPl = s.playerAnswers + 1
                // compute new lead after correct answer
                val diff = newPl - s.aiAnswers
                val (type, count) = when {
                    diff > 0  -> "Ahead"  to diff
                    diff < 0  -> "Behind" to abs(diff)
                    else      -> ""       to 0
                }
                s.copy(
                    currentInput    = "",
                    playerAnswers   = newPl,
                    currentQuestion = generateValidQuestion(), // Use the new function here
                    leadType        = type,
                    leadCount       = count
                )
            } else {
                s.copy(currentInput = newInput)
            }
        }
    }

    fun restart() {
        aiJob?.cancel()
        healthJob?.cancel()
        // reset with a valid question
        _uiState.value = EnduranceBattleUiState(currentQuestion = generateValidQuestion())
        startAiLoop()
        startHealthLoop()
    }

    private fun generateValidQuestion(): Question {
        var question: Question
        do {
            question = Question.random()
        } while (question.answer.toString().contains('9')) // Keep generating if answer contains '9'
        return question
    }
}