package com.example.deimomoimain.view


import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.deimomoimain.viewmodel.DirectBattleViewModel
import com.example.deimomoimain.viewmodel.EnduranceBattleViewModel
import com.example.deimomoimain.viewmodel.DrawViewModel
import com.example.deimomoimain.viewmodel.GameOver
import com.example.deimomoimain.view.VersusViewDrawEB
import com.example.deimomoimain.views.GameOverScreen
/**
 * Hooks up EnduranceBattleViewModel + DrawViewModel.
 * Whenever the *entire* recognizedDigits matches the current answer,
 * we submit it as if each key was pressed, then clear the canvas.
 */
@Composable
fun EnduranceBattleDrawScreen(
    navController: NavController,
    level: Int,
    enduranceVm: EnduranceBattleViewModel = viewModel(),
    drawVm: DrawViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
      LaunchedEffect(level) {
            enduranceVm.setAiDifficulty(level)
            enduranceVm.restart()
      }

    val state     by enduranceVm.uiState.collectAsState()
    val gameOver  = state.gameOver
    val recognized by remember { derivedStateOf { drawVm.uiState.recognizedDigits } }

    // when recognizedDigits parses exactly to the answer, submit it
    LaunchedEffect(recognized, state.currentQuestion) {
        if (recognized.toIntOrNull() == state.currentQuestion.answer) {
            // feed each digit to onKeyPress
            recognized.forEach { enduranceVm.onKeyPress(it.toString()) }
            // clear the strokes & display
            drawVm.clearCanvas()
        }
    }

    if (gameOver != null) {
        GameOverScreen(
            navController = navController,
            result        = gameOver
        )
    } else {
        VersusViewDrawEB(
            modifier        = modifier,
            questionText    = state.currentQuestion.displayText(),
            leadType        = state.leadType,
            leadCount       = state.leadCount,
            playerHealth    = state.playerHealth,
            opponentHealth  = state.opponentHealth,
            onClear         = { drawVm.clearCanvas() }
        )
    }
}
