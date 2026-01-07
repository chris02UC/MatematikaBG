package com.example.matematikabersamagaruda.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.matematikabersamagaruda.navigation.AppScreen
import com.example.matematikabersamagaruda.ui.composables.RotatedBackground
import com.example.matematikabersamagaruda.ui.theme.*
import com.example.matematikabersamagaruda.viewmodel.CustomViewModel

@Composable
fun CustomView(
    navController: NavController,
    customViewModel: CustomViewModel = viewModel()
) {
    val roomCode by customViewModel.roomCode.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        RotatedBackground()

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                CustomTopBar()
            }
            // No bottom bar for this screen based on the image
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 32.dp) // Main horizontal padding
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(40.dp))
                Spacer(modifier = Modifier.weight(1f))

                // Create Lobby Button
                Button(
                    onClick = { navController.navigate("${AppScreen.CustomLobby.route}") },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AppRed.copy(alpha = 0.8f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(103.dp)
                ) {
                    Text(
                        text = "Create Lobby",
                        style = MaterialTheme.typography.headlineLarge, // LilitaOne
                        color = AppTextWhite
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // "OR" Divider
                OrDivider()

                Spacer(modifier = Modifier.height(24.dp))

                // Enter Room Code Section
                EnterRoomCodeSection(
                    roomCode = roomCode,
                    onRoomCodeChange = { customViewModel.onRoomCodeChange(it) },
                    onJoinLobbyClick = { navController.navigate("${AppScreen.CustomLobby.route}") }
                )

                Spacer(modifier = Modifier.weight(1f)) // Pushes content up if screen is tall
                Spacer(modifier = Modifier.height(30.dp)) // Bottom padding
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar() {
    TopAppBar(
        title = {
            Text(
                text = "Custom",
                style = MaterialTheme.typography.displaySmall, // LilitaOne
                color = AppNavSelected, // Dark brown text on light background
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center // Center title
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = AppNavBackground // Light beige background
        ),
         navigationIcon = {
             IconButton(onClick = { /* navController.popBackStack() */ }) {
                 Icon(
                     imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                     contentDescription = "Back",
                     tint = AppNavSelected
                 )
             }
         },
         actions = { Spacer(modifier = Modifier.width(48.dp)) } // Balance for centered title if nav icon exists
    )
}

@Composable
fun OrDivider() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Divider(
            color = AppTextWhite.copy(alpha = 0.7f),
            thickness = 4.dp,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = " OR ",
            color = AppTextWhite.copy(alpha = 0.9f),
            style = MaterialTheme.typography.headlineMedium, // LilitaOne
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Divider(
            color = AppTextWhite.copy(alpha = 0.7f),
            thickness = 4.dp,
            modifier = Modifier.weight(1f)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnterRoomCodeSection(
    roomCode: String,
    onRoomCodeChange: (String) -> Unit,
    onJoinLobbyClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = AppNavBackground.copy(alpha = 0.9f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Enter Room Code",
                style = MaterialTheme.typography.headlineLarge,
                color = AppNavSelected,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = roomCode,
                onValueChange = onRoomCodeChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(103.dp)
                    .background(
                        AppBackgroundBrown.copy(alpha = 0.5f),
                        RoundedCornerShape(12.dp)
                    ),
                textStyle = MaterialTheme.typography.displaySmall.copy(
                    fontSize = 48.sp,
                    color = AppTextWhite,
                    textAlign = TextAlign.Center
                ),
                placeholder = {
                    Text(
                        "1234",
                        style = MaterialTheme.typography.displaySmall.copy(
                            fontSize = 48.sp,
                            color = AppTextWhite.copy(alpha = 0.5f),
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = AppOrange, // Highlight color when focused
                    unfocusedBorderColor = AppTextWhite.copy(alpha = 0.6f), // Border color for text field
                    cursorColor = AppTextWhite,
                    disabledPlaceholderColor = AppTextWhite.copy(alpha = 0.3f)
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = onJoinLobbyClick,
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AppPurple.copy(alpha = 0.8f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(103.dp)
            ) {
                Text(
                    text = "Join Lobby",
                    style = MaterialTheme.typography.headlineLarge, // LilitaOne
                    color = AppTextWhite
                )
            }
        }
    }
}