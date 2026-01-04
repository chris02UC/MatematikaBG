// File: com/example/deimomoimain/view/ChooseBattleTypeView.kt

package com.example.deimomoimain.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.deimomoimain.navigation.AppScreen
import com.example.deimomoimain.ui.composables.RotatedBackground
import com.example.deimomoimain.ui.theme.LilitaOne
import com.example.deimomoimain.ui.theme.momoiDark
import com.example.deimomoimain.ui.theme.momoiYellow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.deimomoimain.PremiumState
import com.example.deimomoimain.R
import com.example.deimomoimain.ui.theme.AppBackgroundBrown
import com.example.deimomoimain.ui.theme.AppTextWhite
import com.example.deimomoimain.viewmodel.DirectBattleViewModel
import com.example.deimomoimain.viewmodel.EnduranceBattleViewModel
import kotlin.math.roundToInt

private enum class InfoDialogType {
    GAME_TYPE, INPUT_METHOD
}

@Composable
fun ChooseBattleTypeView(
    navController: NavController,
    directVm: DirectBattleViewModel = viewModel(),
    enduranceVm: EnduranceBattleViewModel = viewModel()
) {
    var selectedGameType by remember { mutableStateOf("Endurance") }
    var selectedInputMethod by remember { mutableStateOf("Keypad") }
    var aiDifficulty by remember { mutableStateOf(3f) }
    /*Make true or false to change :3*/
    /*var premiumUnlocked by remember { mutableStateOf(false) }*/
    val premiumUnlocked by PremiumState.unlocked
    var showInfoDialogFor by remember { mutableStateOf<InfoDialogType?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        // 1) same background as HomeScreen, scaled 1.2x
        RotatedBackground(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { scaleX = 1.2f; scaleY = 1.2f }
        )

        Column(modifier = Modifier.fillMaxSize()) {
            // header
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(Color(momoiYellow))
                    .padding(vertical = 40.dp)
            ) {
                Text(
                    "Battle vs AI",
                    fontFamily = LilitaOne,
                    fontSize = 48.sp,
                    color = Color(momoiDark),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            // content
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Game Type
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                    ) {
                        Text(
                            "Game Type",
                            fontFamily = LilitaOne,
                            fontSize = 48.sp,
                            color = Color.White,
                            textAlign = TextAlign.Start
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(onClick = { showInfoDialogFor = InfoDialogType.GAME_TYPE }) {
                            Icon(
                                imageVector = Icons.Filled.Info,
                                contentDescription = "Game Type Information",
                                tint = Color.White.copy(alpha = 0.8f)
                            )
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    ) {
                        GameModeButton(
                            label = "Endurance",
                            icon = R.drawable.flame,
                            isSelected = selectedGameType == "Endurance",
                            color = Color(0xFFFF9500),
                            onClick = { selectedGameType = "Endurance" },
                            modifier = Modifier
                                .weight(1f)
                                .height(172.dp),
                            secondaryColor = Color(0xFFFCECB4)
                        )
                        GameModeButton(
                            label = "Direct",
                            icon = R.drawable.sword,
                            isSelected = selectedGameType == "Direct",
                            color = Color(0xFF32ADE6),
                            onClick = { selectedGameType = "Direct" },
                            modifier = Modifier
                                .weight(1f)
                                .height(172.dp),
                            secondaryColor = Color(0xFF94FFFB)
                        )
                    }


                    // Input Method
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                    ) {
                        Text(
                            "Input Method",
                            fontFamily = LilitaOne,
                            fontSize = 48.sp,
                            color = Color.White,
                            textAlign = TextAlign.Start
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(onClick = { showInfoDialogFor = InfoDialogType.INPUT_METHOD }) {
                            Icon(
                                imageVector = Icons.Filled.Info,
                                contentDescription = "Input Method Information",
                                tint = Color.White.copy(alpha = 0.8f)
                            )
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(modifier = Modifier
                            .weight(1f)
                            .height(172.dp)
                        ) {
                            GameModeButton(
                                label = "Touch",
                                icon = R.drawable.pen,
                                isSelected = selectedInputMethod == "Touch",
                                color = Color(0xFF5856D6),
                                onClick = { if (premiumUnlocked) selectedInputMethod = "Touch" },
                                modifier = Modifier.fillMaxSize(),
                                secondaryColor = Color(0xFFE6C4FF),
                            )
                            if (!premiumUnlocked) {
                                Box(
                                    Modifier
                                        .matchParentSize()
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(Color.White.copy(alpha = 0.7f))
                                        .clickable { navController.navigate(AppScreen.BuyPremium.route) }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Lock,
                                        contentDescription = "Locked",
                                        tint = AppBackgroundBrown,
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                            .size(48.dp)
                                            .offset(y = -20.dp)
                                    )
                                    Text (
                                        text = "Unlock with\nPremium",
                                        color = AppBackgroundBrown,
                                        style = MaterialTheme.typography.headlineSmall.copy(fontSize = 20.sp),
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                            .padding(top = 50.dp),
                                        textAlign = TextAlign.Center,
                                        lineHeight = 20.sp
                                    )
                                }
                            }
                        }
                        GameModeButton(
                            label = "Keypad",
                            icon = R.drawable.keypad,
                            isSelected = selectedInputMethod == "Keypad",
                            color = Color(0xFF34C759),
                            onClick = { selectedInputMethod = "Keypad" },
                            modifier = Modifier
                                .weight(1f)
                                .height(172.dp),
                            secondaryColor = Color(0xFFDEFFB6)
                        )
                    }

                    Spacer(Modifier.height(20.dp))

                    Text(
                        "AI Difficulty",
                        fontFamily = LilitaOne,
                        fontSize = 24.sp,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        textAlign = TextAlign.Start
                    )

                    GradientSlider(
                        value = aiDifficulty,
                        onValueChange = { aiDifficulty = it },
                        valueRange = 1f..5f,
                        steps = 3,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                    )

                    Spacer(Modifier.height(0.dp))

                    Button(
                        onClick = {
//                            val level = aiDifficulty.toInt().coerceIn(1,5)
//                            when (selectedGameType to selectedInputMethod) {
//                                "Direct" to "Touch",
//                                "Direct" to "Keypad" -> {
//                                    directVm.setDifficulty(level)
//                                }
//                                "Endurance" to "Touch",
//                                "Endurance" to "Keypad" -> {
//                                    enduranceVm.setAiDifficulty(level)
//                                }
//                            }
                            val level = aiDifficulty.roundToInt().coerceIn(1,5)
                            if (selectedGameType == "Direct") {
                                directVm.setDifficulty(level)
                            }else {
                                enduranceVm.setAiDifficulty(level)
                            }
                            // now navigate
                            val route = when (selectedGameType to selectedInputMethod) {
                                "Endurance" to "Touch"  -> "${AppScreen.EnduranceBattleDrawScreen.route}/$level"
                                "Endurance" to "Keypad" -> "${AppScreen.EnduranceBattleScreen.route}/$level"
                                "Direct"    to "Touch"  -> "${AppScreen.DirectBattleDrawScreen.route}/$level"
                                "Direct"    to "Keypad" -> "${AppScreen.DirectBattleScreen.route}/$level"
                                else                    -> AppScreen.ChooseType.route
                            }
                            navController.navigate(route)
                        },
                        colors    = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
                        modifier  = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        shape     = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            "Go Battle!",
                            fontFamily = LilitaOne,
                            fontSize   = 48.sp,
                            color      = Color.White
                        )
                    }
                }
            }
        }
    }

    if (showInfoDialogFor != null) {
        val title: String
        val content: AnnotatedString

        when (showInfoDialogFor) {
            InfoDialogType.GAME_TYPE -> {
                title = "Game Type Information"
                content = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp, color = AppTextWhite)) {
                        append("Endurance:\n")
                    }
                    withStyle(style = SpanStyle(color = AppTextWhite)) {
                        append("he Endurance game mode is designed to test a player's speed and sustained accuracy. Correctly answered questions incrementally increase the rate at which the opponent's health points diminish.\n\n")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp, color = AppTextWhite)) {
                        append("Direct:\n")
                    }
                    withStyle(style = SpanStyle(color = AppTextWhite)) {
                        append("In Direct Damage mode, each correctly answered question results in a fixed amount of damage dealt to the opponent. This mode emphasizes consistent performance and strategic accuracy to deplete the opponent's health points methodically.")
                    }
                }
            }
            InfoDialogType.INPUT_METHOD -> {
                title = "Input Method Information"
                content = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp, color = AppTextWhite)) {
                        append("Touch:\n")
                    }
                    withStyle(style = SpanStyle(color = AppTextWhite)) {
                        append("The Touch input method allows players to inscribe their answers directly onto a designated area of the screen using touch gestures, accommodating a freehand style of response.\n\n")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp, color = AppTextWhite)) {
                        append("Keypad:\n")
                    }
                    withStyle(style = SpanStyle(color = AppTextWhite)) {
                        append("The Keypad input method provides a numerical interface, similar to a standard calculator, enabling players to enter their answers through discrete button presses for precise data entry.\n\n")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp, color = AppTextWhite)) {
                        append("General Gameplay:\n")
                    }
                    withStyle(style = SpanStyle(color = AppTextWhite)) {
                        append("Gameplay starts immediately upon presentation of a question, requiring swift cognitive processing and response. Correct answers are validated instantaneously. Incorrect submissions must be cleared or deleted before a new answer can be entered.")
                    }
                }
            }
            null -> {
                title = ""
                content = buildAnnotatedString { append("") }
            }
        }

        AlertDialog(
            onDismissRequest = { showInfoDialogFor = null },
            title = { Text(title, fontFamily = LilitaOne, fontSize = 22.sp) },
            text = { Text(content, style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp)) },
            confirmButton = {
                TextButton(onClick = { showInfoDialogFor = null }) {
                    Text("OK", fontFamily = LilitaOne, fontSize = 18.sp)
                }
            },
            shape = RoundedCornerShape(16.dp)
        )
    }
}


@Composable
fun GradientSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float> = 1f..5f,
    steps: Int = 3,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        // 1) Gradient track background
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        listOf(Color(0xFF4CAF50), Color(0xFFF44336))
                    ),
                    shape = RoundedCornerShape(4.dp)
                )
        )

        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange,
            steps = steps,
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-28).dp), // pull it up so thumb centers on the 8dp bar
            colors = SliderDefaults.colors(
                thumbColor         = Color(0xFF4E342E),
                activeTrackColor   = Color.Transparent,
                inactiveTrackColor = Color.Transparent,
                activeTickColor    = Color.Transparent,
                inactiveTickColor  = Color.Transparent
            )
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-32).dp)        // lift them up closer to the bar
                .padding(top = 0.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            for (i in 1..5) {
                Text(
                    text = "$i",
                    fontFamily = LilitaOne,
                    fontSize   = 14.sp,
                    color      = Color.White
                )
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewChooseBattleType() {
    ChooseBattleTypeView(navController = rememberNavController())
}
