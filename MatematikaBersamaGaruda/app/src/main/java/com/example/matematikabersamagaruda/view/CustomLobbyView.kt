package com.example.matematikabersamagaruda.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.matematikabersamagaruda.model.GameType
import com.example.matematikabersamagaruda.model.InputMethod
import com.example.matematikabersamagaruda.navigation.AppScreen
import com.example.matematikabersamagaruda.ui.composables.RotatedBackground
import com.example.matematikabersamagaruda.ui.theme.*
import com.example.matematikabersamagaruda.viewmodel.CustomLobbyState
import com.example.matematikabersamagaruda.viewmodel.CustomLobbyViewModel
import com.example.matematikabersamagaruda.viewmodel.PlayerLobbyInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomLobbyView(
    navController: NavController,
    // roomCode: String, // Pass roomCode if navigating here
    // isHost: Boolean,  // Pass isHost if navigating here
    customLobbyViewModel: CustomLobbyViewModel = viewModel()
) {
    // LaunchedEffect(roomCode, isHost) {
    //     customLobbyViewModel.setupLobby(roomCode, isHost)
    // }
    val uiState by customLobbyViewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        RotatedBackground()

        Scaffold(
            containerColor = Color.Transparent,
            topBar = { CustomLobbyTopBar() }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 20.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                RoomCodeDisplay(uiState.roomCode)
                Spacer(modifier = Modifier.height(16.dp))
                PlayerVsDisplay(uiState.player1, uiState.player2)
                Spacer(modifier = Modifier.height(24.dp))
                SettingsCard(uiState, customLobbyViewModel, navController)
                Spacer(modifier = Modifier.height(24.dp))
                GoBattleButton(
                    enabled = customLobbyViewModel.canProceedToBattle(),
                    onClick = { /* TODO: Navigate to battle screen based on selections */ }
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomLobbyTopBar() {
    TopAppBar(
        title = {
            Text(
                text = "Custom Lobby",
                style = MaterialTheme.typography.displaySmall,
                color = AppNavSelected,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = AppNavBackground)
        // Optional: Add navigationIcon for back if needed
    )
}

@Composable
fun RoomCodeDisplay(roomCode: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            "Room Code",
            style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp),
            color = AppTextWhite.copy(alpha = 0.8f)
        )
        Text(
            roomCode,
            style = MaterialTheme.typography.displayMedium.copy(fontSize = 40.sp),
            color = AppTextWhite
        )
    }
}

@Composable
fun PlayerVsDisplay(player1: PlayerLobbyInfo, player2: PlayerLobbyInfo?) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        PlayerAvatar(player1.name, player1.avatarColor)
        Text("VS", style = MaterialTheme.typography.displayMedium, color = AppTextWhite)
        if (player2 != null) {
            PlayerAvatar(player2.name, player2.avatarColor)
        } else {
            PlayerAvatar("Awaiting...", Color.Gray)
        }
    }
}

@Composable
fun PlayerAvatar(name: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(color)
                .shadow(elevation = 4.dp, spotColor = Color(0x40000000), ambientColor = Color(0x40000000))
                .padding(1.dp)
                .width(100.dp)
                .height(100.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(name, style = MaterialTheme.typography.bodyLarge, color = AppTextWhite)
    }
}

@Composable
fun SettingsCard(uiState: CustomLobbyState, viewModel: CustomLobbyViewModel, navController: NavController) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = AppLobbyCardBackground),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp)) {
            // Q. Difficulty
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "Q. Difficulty",
                        style = MaterialTheme.typography.headlineSmall,
                        color = AppDarkGray // Darker text for readability on light card
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    DifficultySlider(
                        value = uiState.difficulty,
                        onValueChange = { viewModel.setDifficulty(it) },
                        modifier = Modifier.fillMaxWidth(0.95f) // Slider takes most of this column
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Button(
                        onClick = { navController.navigate(AppScreen.CustomQuestions.route) },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = AppCustomQButton),
                        modifier = Modifier
                            .shadow(
                                elevation = 4.dp,
                                spotColor = Color(0x40000000),
                                ambientColor = Color(0x40000000)
                            )
                            .width(100.dp)
                            .height(60.dp)
                            .background(
                                color = AppPremiumPink,
                                shape = RoundedCornerShape(size = 12.dp)
                            ),
                        contentPadding = PaddingValues(horizontal = 4.dp)
                    ) {
                        Text(
                            "Custom Q.",
                            style = MaterialTheme.typography.titleSmall.copy(fontSize=16.sp),
                            color = AppTextWhite
                        )
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }


            // Game Type
            Text(
                "Game Type",
                style = MaterialTheme.typography.headlineSmall,
                color = AppDarkGray,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                viewModel.gameTypes.forEach { gameType ->
                    LobbySelectableButton(
                        text = gameType.displayName,
                        iconRes = gameType.iconRes,
                        isSelected = uiState.selectedGameType == gameType,
                        onClick = { viewModel.selectGameType(gameType) },
                        backgroundColor = when (gameType) {
                            GameType.Endurance -> AppOrange
                            GameType.Direct -> AppBlue
                        },
                        Modifier
                            .shadow(elevation = 4.dp, spotColor = Color(0x40000000), ambientColor = Color(0x40000000))
                            .width(160.dp)
                            .height(80.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Input Method
            Text(
                "Input Method",
                style = MaterialTheme.typography.headlineSmall,
                color = AppDarkGray,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                viewModel.inputMethods.forEach { inputMethod ->
                    LobbySelectableButton(
                        text = inputMethod.displayName,
                        iconRes = inputMethod.iconRes,
                        isSelected = uiState.selectedInputMethod == inputMethod,
                        onClick = { viewModel.selectInputMethod(inputMethod) },
                        backgroundColor = when (inputMethod) {
                            InputMethod.Touch -> AppPurple
                            InputMethod.Keypad -> AppGreen
                        },
                        Modifier
                            .shadow(elevation = 4.dp, spotColor = Color(0x40000000), ambientColor = Color(0x40000000))
                            .width(160.dp)
                            .height(80.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun DifficultySlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    val valueRange = 1f..4f
    val steps = 2 // For 4 stops (1, 2, 3, 4), steps = num_stops - 2

    Column(modifier = modifier) {
        Box( // Gradient Track
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        listOf(AppSliderGreen, AppSliderRed)
                    ),
                    shape = RoundedCornerShape(5.dp)
                )
        )
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange,
            steps = steps,
            modifier = Modifier.fillMaxWidth().offset(y = (-29).dp), // Adjust for visual centering
            colors = SliderDefaults.colors(
                thumbColor = AppSliderThumb,
                activeTrackColor = Color.Transparent,
                inactiveTrackColor = Color.Transparent,
                activeTickColor = Color.Transparent,
                inactiveTickColor = Color.Transparent
            )
        )
        Row(
            modifier = Modifier.fillMaxWidth().offset(y = (-35).dp).padding(horizontal = 4.dp), // Adjust y-offset
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            (1..4).forEach { number -> // Labels for 1, 2, 3, 4
                Text(
                    text = number.toString(),
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    color = AppDarkGray
                )
            }
        }
    }
}

@Composable
fun LobbySelectableButton(
    text: String,
    @androidx.annotation.DrawableRes iconRes: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    val borderColor = if (isSelected) AppTextWhite else Color.Transparent // Darker border when selected
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
        border = BorderStroke(if (isSelected) 3.dp else 0.dp, borderColor),
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 10.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) { // Changed to Row for icon and text
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size(40.dp), // Adjust icon size
                colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(AppTextWhite)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.titleSmall.copy(fontSize=16.sp),
                color = AppTextWhite
            )
        }
    }
}

@Composable
fun GoBattleButton(enabled: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = AppBlue,
            disabledContainerColor = AppBlue.copy(alpha = 0.5f)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(103.dp)
            .background(color = Color(0xFF2D8EF7), shape = RoundedCornerShape(size = 18.dp))
    ) {
        Text(
            "Go Battle!",
            style = MaterialTheme.typography.headlineMedium.copy(fontSize=48.sp),
            color = AppTextWhite
        )
    }
}