package com.example.matematikabersamagaruda.view

import android.media.MediaPlayer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.matematikabersamagaruda.R
import com.example.matematikabersamagaruda.viewmodel.EnduranceBattleViewModel

@Composable
fun EnduranceBattleScreen(
    navController: NavController,
    level: Int,
    viewModel: EnduranceBattleViewModel = viewModel()
) {
    LaunchedEffect(level) { // Keep this as it was for difficulty setting
        viewModel.setAiDifficulty(level)
        viewModel.restart()
    }
    val state by viewModel.uiState.collectAsState()
    val gameOver = state.gameOver

    if (gameOver != null) {
        GameOverScreen(
            navController = navController,
            result        = gameOver
        )
    } else {
        VersusViewKeypadEB(
            questionText   = state.currentQuestion.displayText(),
            leadType       = state.leadType,
            leadCount      = state.leadCount,
            answer         = state.currentInput,
            playerHealth   = state.playerHealth,
            opponentHealth = state.opponentHealth,
            onKeyPress     = viewModel::onKeyPress
        )
    }
}