package com.example.matematikabersamagaruda.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.matematikabersamagaruda.ui.theme.DEIMOMOIMAINTheme
import com.example.matematikabersamagaruda.ui.theme.LilitaOne
import androidx.compose.foundation.Canvas
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width

import androidx.compose.ui.geometry.Size

@Composable
fun VersusViewKeypad(
    modifier: Modifier = Modifier,
    questionText: String = "21 x 3",
    timer: Int = 60
) {
    var answer by remember { mutableStateOf("69") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF2E2A28))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Menu icon
        Box(
            modifier = Modifier
                .align(Alignment.Start)     // ← here
                .size(60.dp)
                .background(Color(0xFFFBD77A), RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                tint = Color(0xFF382E3B),
                modifier = Modifier.size(48.dp)
            )
        }

        Spacer(Modifier.height(32.dp))

        // Health + Timer
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Slot 1: You on the left
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterStart
            ) {
                HealthBar("You", Color(0xFF4CAF50))
            }

            // Slot 2: Timer centered
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "Timer",
                        style = TextStyle(
                            fontFamily = LilitaOne,
                            fontSize = 20.sp,
                            color = Color.White
                        )
                    )
                    Text(
                        "$timer",
                        style = TextStyle(
                            fontFamily = LilitaOne,
                            fontSize = 40.sp,
                            color = Color.White
                        )
                    )
                }
            }

            // Slot 3: Opponent on the right
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterEnd
            ) {
                HealthBar("Opponent", Color(0xFFF44336))
            }
        }

        Spacer(Modifier.height(32.dp))

        // Question above keypad
        Text(
            questionText,
            style = TextStyle(
                fontFamily = LilitaOne,
                fontSize = 60.sp,
                color = Color.White
            )
        )

        Spacer(Modifier.height(16.dp))

        // Card containing answer display + keypad, fills remaining space
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

                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(108.dp)
                        .background(Color(0xFF382E3B), RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Text(
                        answer,
                        style = TextStyle(
                            fontFamily = LilitaOne,
                            fontSize = 60.sp,
                            color = Color.White
                        ),
                        modifier = Modifier.padding(end = 16.dp)
                    )
                }

                Spacer(Modifier.height(12.dp))

                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                ) {
                    val strokeWidth = 2.dp.toPx()
                    val radiusPx = size.height / 2
                    val diameter = 2 * radiusPx
                    val w = size.width

                    drawArc(
                        color      = Color.Gray,
                        startAngle = 180f,
                        sweepAngle = 90f,
                        useCenter  = false,
                        topLeft    = Offset(0f, radiusPx),
                        size       = Size(diameter, diameter),
                        style      = Stroke(strokeWidth, cap = StrokeCap.Butt)
                    )

                    drawLine(
                        color       = Color.Gray,
                        start       = Offset(radiusPx, radiusPx),
                        end         = Offset(w - radiusPx, radiusPx),
                        strokeWidth = strokeWidth,
                        cap         = StrokeCap.Butt
                    )
                    drawArc(
                        color      = Color.Gray,
                        startAngle = 270f,
                        sweepAngle = 90f,
                        useCenter  = false,
                        topLeft    = Offset(w - diameter, radiusPx),
                        size       = Size(diameter, diameter),
                        style      = Stroke(strokeWidth, cap = StrokeCap.Butt)
                    )
                }
                Spacer(Modifier.height(24.dp))

                Column(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(14.dp, Alignment.CenterVertically)
                ) {
                    val keys = listOf(
                        listOf("7","8","9"),
                        listOf("4","5","6"),
                        listOf("1","2","3"),
                        listOf(".","0","←")
                    )
                    keys.forEach { row ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            row.forEach { key ->
                                KeyButton(
                                    key = key,
                                    modifier = Modifier
                                        .weight(1f)     // equal share of width
                                        .height(72.dp)  // fixed height for rectangle shape
                                ) {
                                    when (key) {
                                        "←" -> if (answer.isNotEmpty()) answer = answer.dropLast(1)
                                        "." -> if (!answer.contains(".")) answer += "."
                                        else -> answer += key
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HealthBar(label: String, barColor: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            label,
            style = TextStyle(
                fontFamily = LilitaOne,
                fontSize = 28.sp,
                color = Color.White
            )
        )
        Spacer(Modifier.height(4.dp))
        Box(
            Modifier
                .width(160.dp)
                .height(18.dp)
                .background(barColor, RoundedCornerShape(6.dp))
        )
    }
}

@Composable
private fun KeyButton(
    key: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        // Shadow layer
        Box(
            Modifier
                .matchParentSize()
                .offset(y = 6.dp)
                .background(Color(0xFFD37A96), RoundedCornerShape(12.dp))
        )
        // Foreground button
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .matchParentSize()
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(12.dp),
                    ambientColor = Color.Black.copy(alpha = 0.25f),
                    spotColor = Color.Black.copy(alpha = 0.25f)
                )
                .background(Color(0xFFF48FB1), RoundedCornerShape(12.dp))
                .clickable(onClick = onClick)
        ) {
            if (key == "←") {
                Icon(
                    imageVector = Icons.Default.Backspace,
                    contentDescription = "Backspace",
                    tint = Color.White,
                    modifier = Modifier.size(40.dp)  // <-- bump this up to match your digit size
                )
            } else {
                Text(
                    key,
                    style = TextStyle(
                        fontFamily = LilitaOne,
                        fontSize = 64.sp,
                        color = Color.White
                    )
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF2E2A28,
    widthDp = 360,
    heightDp = 640
)
@Composable
fun VersusViewKeypadPreview() {
    DEIMOMOIMAINTheme {
        VersusViewKeypad(Modifier.fillMaxSize())
    }
}
