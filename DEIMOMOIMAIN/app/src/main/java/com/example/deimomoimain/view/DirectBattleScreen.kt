package com.example.deimomoimain.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.deimomoimain.viewmodel.DirectBattleViewModel
// import com.example.deimomoimain.viewmodel.GameOver // Already imported by .*
import com.example.deimomoimain.views.GameOverScreen
// import com.example.deimomoimain.views.VersusViewKeypad // Not used directly here
import com.example.deimomoimain.views.VersusViewKeypadDB
import android.media.MediaPlayer
import androidx.compose.runtime.DisposableEffect
// import androidx.compose.runtime.remember // No longer needed for mediaPlayer
import androidx.compose.ui.platform.LocalContext
import com.example.deimomoimain.R


@Composable
fun DirectBattleScreen(
    navController: NavController,
    level: Int,
    viewModel: DirectBattleViewModel = viewModel()
) {
    val context = LocalContext.current

    // Changed MediaPlayer initialization to match touchscreen views
    DisposableEffect(Unit) {
        val mediaPlayer = MediaPlayer.create(context, R.raw.unwelcomeschool_bluearchive)?.apply {
            isLooping = true
            setVolume(1.0f, 1.0f) // Set your desired volume (e.g., 1.0f for full)
            try {
                start()
            } catch (e: IllegalStateException) {
                // Optional: Log e.message or handle error
            }
        }

        onDispose {
            mediaPlayer?.let {
                try {
                    if (it.isPlaying) {
                        it.stop()
                    }
                } catch (e: IllegalStateException) {
                    // Optional: Log e.message or handle error
                }
                it.release()
            }
        }
    }

    LaunchedEffect(Unit) { // Keep this as it was for difficulty setting
        viewModel.setDifficulty(level)
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
        VersusViewKeypadDB(
            questionText   = state.currentQuestion.displayText(),
            timer          = state.timerSeconds,
            answer         = state.currentInput,
            playerHealth   = state.playerHealth,
            opponentHealth = state.opponentHealth,
            onKeyPress     = viewModel::onKeyPress
        )
    }
}