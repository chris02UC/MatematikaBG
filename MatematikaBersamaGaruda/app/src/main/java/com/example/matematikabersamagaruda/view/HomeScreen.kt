package com.example.matematikabersamagaruda.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.matematikabersamagaruda.PremiumState
import com.example.matematikabersamagaruda.R
import com.example.matematikabersamagaruda.model.LeaderboardItem
import com.example.matematikabersamagaruda.navigation.AppScreen
import com.example.matematikabersamagaruda.ui.composables.*
import com.example.matematikabersamagaruda.ui.theme.*
import com.example.matematikabersamagaruda.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = viewModel()
) {
    val leaderboardItems by homeViewModel.leaderboardItems.collectAsState()
    val premiumUnlocked by PremiumState.unlocked

    Box(modifier = Modifier.fillMaxSize()) {
        RotatedBackground()

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                AppTopBar("Home")
            },
            bottomBar = {
                AppBottomNavigationBar(navController = navController)
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues) // Apply padding from Scaffold
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState()) // Scrollable home screen
            ) {

                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "PvP Leaderboard",
                    style = MaterialTheme.typography.headlineSmall,
                    color = AppTextWhite,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = AppLightBrown.copy(alpha = 0.8f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 300.dp)
                            .padding(vertical = 8.dp)
                    ) {
                        items(leaderboardItems) { item ->
                            LeaderboardRow(item = item)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clickable { /* TODO: PvP navigation */ },
                    colors = CardDefaults.cardColors(containerColor = AppRed),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 12.dp)
                    ) {
                        Text(
                            "PvP",
                            style = MaterialTheme.typography.headlineSmall.copy(fontSize = 40.sp),
                            color = Color.White,
                            modifier = Modifier
                                .offset(x = 16.dp,  y = (-24).dp)   // slide text 16dp to the right
                        )
                        Image(
                            painter = painterResource(id = R.drawable.pvpicon),
                            contentDescription = "PvP Icon",
                            modifier = Modifier.size(256.dp)
                                .offset(x = 24.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clickable { navController.navigate(AppScreen.ChooseType.route) },
                    colors = CardDefaults.cardColors(containerColor = AppBlue),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 12.dp)
                    ) {
                        Text(
                            "vs AI",
                            style = MaterialTheme.typography.headlineSmall.copy(fontSize = 40.sp),
                            color = Color.White,
                            modifier = Modifier
                                .offset(x = 16.dp,  y = (-24).dp)
                        )
                        Image(
                            painter = painterResource(id = R.drawable.vsaiicon),
                            contentDescription = "vs AI Icon",
                            modifier = Modifier.size(256.dp)
                                .offset(x = 52.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Box { // Box to allow overlaying the lock
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp) // As per your provided code
                            .clickable(enabled = premiumUnlocked) { // Only clickable if unlocked
                                if (premiumUnlocked) {
                                    navController.navigate(AppScreen.Custom.route) // Navigate to your custom screen
                                } else {
                                }
                            },
                        colors = CardDefaults.cardColors(containerColor = AppGreen), // Using AppGreen as per your example
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 12.dp)
                        ) {
                            Text(
                                "Custom",
                                style = MaterialTheme.typography.headlineSmall.copy(fontSize = 40.sp), // Using LilitaOne via theme
                                color = Color.White,
                                modifier = Modifier
                                    .weight(1f)
                                    .offset(x = 16.dp, y = (-24).dp)
                            )
                            Image(
                                painter = painterResource(id = R.drawable.customangyicon), // Ensure this drawable exists
                                contentDescription = "Custom Icon",
                                modifier = Modifier
                                    .size(120.dp) // Adjusted size to better fit the card height
                                // .offset(x = 48.dp) // Original offset might push it too far
                            )
                        }
                    }

                    // Lock Overlay
                    if (!premiumUnlocked) {
                        Box(
                            Modifier
                                .matchParentSize() // Cover the entire Card
                                .clip(RoundedCornerShape(16.dp)) // Match card shape
                                .background(Color.White.copy(alpha = 0.6f)) // Darker overlay for better visibility
                                .clickable(onClick = {
                                    // Optionally navigate to BuyPremium screen when locked overlay is clicked
                                    navController.navigate(AppScreen.BuyPremium.route)
                                })
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Locked",
                                tint = AppBackgroundBrown,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .size(60.dp)
                                    .offset(y = -20.dp)
                            )
                            Text (
                                text = "Unlock with\nPremium",
                                color = AppBackgroundBrown,
                                style = MaterialTheme.typography.headlineSmall.copy(fontSize = 24.sp),
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(top = 70.dp),
                                textAlign = TextAlign.Center,
                                lineHeight = 20.sp
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun LeaderboardRow(item: LeaderboardItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(AppOrangeLight)
        ) {
            Text(
                text = item.rank.toString(),
                color = AppTextBlack, // Or a darker orange
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 32.sp),
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Avatar Placeholder
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(Color.LightGray) // Placeholder color
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = item.name,
            style = MaterialTheme.typography.titleLarge,
            color = AppTextWhite,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = item.score.toString(),
            style = MaterialTheme.typography.titleLarge,
            color = AppTextBlack,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .background(AppOrange, RoundedCornerShape(8.dp))
                .padding(horizontal = 16.dp, vertical = 6.dp)
        )
    }
}

@Composable
fun ActionButton(text: String, onClick: () -> Unit, color: Color, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color),
        modifier = modifier.height(120.dp),
        contentPadding = PaddingValues(vertical = 12.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineSmall.copy(fontSize = 40.sp),
            color = AppTextWhite
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = NavController(context = LocalContext.current))
}