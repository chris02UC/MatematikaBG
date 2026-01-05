// File: com/example/deimomoimain/views/VersusViewDrawDB.kt
package com.example.deimomoimain.views

import android.media.MediaPlayer
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.deimomoimain.R
import com.example.deimomoimain.ui.theme.LilitaOne
import com.example.deimomoimain.viewmodel.DrawViewModel
import com.example.deimomoimain.viewmodel.DirectBattleViewModel
import kotlin.math.min

/**
 * VersusViewDrawDB: Direct‐Battle with drawing input UI
 *
 * @param questionText  current math question
 * @param timer         elapsed seconds
 * @param playerHealth  your remaining health pips (0..5)
 * @param opponentHealth opponent's remaining health pips (0..5)
 * @param onClear       callback to clear the drawing canvas
 */
@Composable
fun VersusViewDrawDB(
    questionText: String,
    timer: Int,
    playerHealth: Int,
    opponentHealth: Int,
    onClear: () -> Unit,
    modifier: Modifier = Modifier,
    drawVm: DrawViewModel = viewModel()
) {
    val context = LocalContext.current
    DisposableEffect(Unit) {
        val mediaPlayer = MediaPlayer.create(context, R.raw.unwelcomeschool_bluearchive).apply {
            isLooping = true
            setVolume(1f, 1f)
            start()
        }
        onDispose {
            mediaPlayer.stop()
            mediaPlayer.release()
        }
    }
    val state      = drawVm.uiState
    var canvasSize by remember { mutableStateOf(IntSize.Zero) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF2E2A28))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // ─── top menu button ─────────────────────────────────────────────
        Box(
            Modifier
                .align(Alignment.Start)
                .size(60.dp)
                .background(Color(0xFFFBD77A), RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = androidx.compose.material.icons.Icons.Default.Menu,
                contentDescription = "Menu",
                tint = Color(0xFF382E3B),
                modifier = Modifier.size(48.dp)
            )
        }

        Spacer(Modifier.height(32.dp))

        // ─── health + timer row ──────────────────────────────────────────
        Row(Modifier.fillMaxWidth()) {
            Box(Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                HealthBar("You", Color(0xFF4CAF50), fraction = playerHealth / 5f)
            }
            Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Timer",
                        fontFamily = LilitaOne, fontSize = 20.sp, color = Color.White)
                    Text("$timer",
                        fontFamily = LilitaOne, fontSize = 40.sp, color = Color.White)
                }
            }
            Box(Modifier.weight(1f), contentAlignment = Alignment.CenterEnd) {
                HealthBar("Opponent", Color(0xFFF44336), fraction = opponentHealth / 5f)
            }
        }

        Spacer(Modifier.height(32.dp))

        // ─── question ────────────────────────────────────────────────────
        Text(
            questionText,
            fontFamily = LilitaOne, fontSize = 60.sp, color = Color.White
        )

        Spacer(Modifier.height(16.dp))

        // ─── main card ───────────────────────────────────────────────────
        Card(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.95f),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBD2)),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // – recognized‐digits display
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(108.dp)
                        .background(Color(0xFF382E3B), RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Text(
                        state.recognizedDigits,
                        fontFamily = LilitaOne, fontSize = 60.sp, color = Color.White,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                }

                Spacer(Modifier.height(12.dp))

                // – decorative separator
                Canvas(Modifier.fillMaxWidth().height(16.dp)) {
                    val stroke = 2.dp.toPx()
                    val r      = size.height / 2
                    val w      = size.width
                    drawArc(
                        Color.Gray, 180f, 90f, false,
                        topLeft = Offset(0f, r),
                        size    = Size(2 * r, 2 * r),
                        style   = androidx.compose.ui.graphics.drawscope.Stroke(stroke)
                    )
                    drawLine(Color.Gray,
                        Offset(r, r),
                        Offset(w - r, r),
                        strokeWidth = stroke)
                    drawArc(
                        Color.Gray, 270f, 90f, false,
                        topLeft = Offset(w - 2 * r, r),
                        size    = Size(2 * r, 2 * r),
                        style   = androidx.compose.ui.graphics.drawscope.Stroke(stroke)
                    )
                }

                Spacer(Modifier.height(8.dp))

                // – drawing canvas
                Box(
                    Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .onSizeChanged { canvasSize = it }
                        .background(Color.Black, RoundedCornerShape(12.dp))
                        .pointerInput(canvasSize) {
                            detectDragGestures(
                                onDragStart = { p ->
                                    if (canvasSize.width > 0) {
                                        val x = p.x / canvasSize.width * 500f
                                        val y = p.y / canvasSize.height * 500f
                                        drawVm.startStroke(x, y)
                                    }
                                },
                                onDrag = { ch, _ ->
                                    val x = ch.position.x / canvasSize.width * 500f
                                    val y = ch.position.y / canvasSize.height * 500f
                                    drawVm.addPointToStroke(x, y)
                                },
                                onDragEnd = { drawVm.endStroke() }
                            )
                        }
                ) {
                    Canvas(Modifier.fillMaxSize()) {
                        if (canvasSize.width > 0) {
                            val sx = size.width  / 500f
                            val sy = size.height / 500f
                            val sw = 16f * min(sx, sy)
                            state.allStrokes.forEach { s ->
                                s.windowed(2).forEach { (a, b) ->
                                    drawLine(
                                        Color.White,
                                        Offset(a.first * sx, a.second * sy),
                                        Offset(b.first * sx, b.second * sy),
                                        strokeWidth = sw
                                    )
                                }
                            }
                            state.currentStroke.windowed(2).forEach { (a, b) ->
                                drawLine(
                                    Color.White,
                                    Offset(a.first * sx, a.second * sy),
                                    Offset(b.first * sx, b.second * sy),
                                    strokeWidth = sw
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                // – clear button
                Button(onClick = {
                    drawVm.clearCanvas()
                    onClear()
                }) {
                    Text("Clear")
                }
            }
        }
    }
}

@Composable
private fun HealthBar(
    label: String,
    barColor: Color,
    fraction: Float = 1f
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, fontFamily = LilitaOne, fontSize = 28.sp, color = Color.White)
        Spacer(Modifier.height(4.dp))
        Box(
            Modifier
                .width(160.dp * fraction)
                .height(18.dp)
                .background(barColor, RoundedCornerShape(6.dp))
        )
    }
}
