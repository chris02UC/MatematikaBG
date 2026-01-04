// File: com/example/deimomoimain/view/DirectBattleDrawScreen.kt
package com.example.deimomoimain.view

import android.media.MediaPlayer
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.deimomoimain.R
import com.example.deimomoimain.viewmodel.DirectBattleViewModel
import com.example.deimomoimain.viewmodel.DrawViewModel
import com.example.deimomoimain.viewmodel.GameOver
import com.example.deimomoimain.views.GameOverScreen
import com.example.deimomoimain.views.VersusViewDrawDB

@Composable
fun DirectBattleDrawScreen(
    navController: NavController,
    level: Int,
    directVm: DirectBattleViewModel = viewModel(),
    drawVm: DrawViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
//    val context = LocalContext.current

//    val mediaPlayer = remember {
//        MediaPlayer.create(context, R.raw.unwelcomeschool_bluearchive).apply {
//            isLooping = true
//            setVolume(0.5f, 0.5f)
//            start()
//        }
//    }

//    DisposableEffect(Unit) {
//        onDispose {
//            mediaPlayer.stop()
//            mediaPlayer.release()
//        }
//    }
        LaunchedEffect(level) {
              directVm.setDifficulty(level)
              directVm.restart()
        }
    val state      by directVm.uiState.collectAsState()
    val gameOver   = state.gameOver
    val recognized by remember { derivedStateOf { drawVm.uiState.recognizedDigits } }

    // Whenever recognizedDigits *as a whole* matches the answer, submit it
    LaunchedEffect(recognized, state.currentQuestion) {
        // only try if recognized is nonempty
        val input = recognized
        val correct = state.currentQuestion.answer
        if (input.toIntOrNull() == correct) {
            // feed each digit into onKeyPress, so the VM handles its usual logic
            input.forEach { directVm.onKeyPress(it.toString()) }
            // now wipe the drawing pad + display
            drawVm.clearCanvas()
        }
    }

//    LaunchedEffect(gameOver) {
//        if (gameOver != null && mediaPlayer.isPlaying) {
//            mediaPlayer.stop()
//        }
//    }

    if (gameOver != null) {
        GameOverScreen(
            navController = navController,
            result        = gameOver
        )
    } else {
        VersusViewDrawDB(
            modifier        = modifier,
            questionText    = state.currentQuestion.displayText(),
            timer           = state.timerSeconds,
            playerHealth    = state.playerHealth,
            opponentHealth  = state.opponentHealth,
            onClear         = { drawVm.clearCanvas() }
        )
    }
}
