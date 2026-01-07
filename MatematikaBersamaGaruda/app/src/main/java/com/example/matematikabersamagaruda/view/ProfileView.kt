package com.example.matematikabersamagaruda.view

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.matematikabersamagaruda.R // Your R file
import com.example.matematikabersamagaruda.navigation.AppScreen
import com.example.matematikabersamagaruda.ui.composables.*
import com.example.matematikabersamagaruda.ui.theme.*
import com.example.matematikabersamagaruda.viewmodel.ProfileViewModel
import com.example.matematikabersamagaruda.viewmodel.UserProfile

@Composable
fun ProfileView(
    navController: NavController,
    profileViewModel: ProfileViewModel = viewModel()
) {
    val userProfile by profileViewModel.userProfile.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        RotatedBackground()

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                AppTopBar("Profile")
            },
            bottomBar = {
                AppBottomNavigationBar(navController = navController)
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(30.dp))

                UserInfoSection(userProfile)

                Spacer(modifier = Modifier.height(30.dp))

                SettingsCard(navController)

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun UserInfoSection(userProfile: UserProfile) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp) // Give a bit of space from edge
    ) {
        Box(
            modifier = Modifier
                .size(96.dp)
                .clip(CircleShape)
                .background(userProfile.avatarPlaceholderColor) // Use color from ViewModel
        )
        // If you have an actual avatar image, use Image composable here

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = userProfile.name,
                style = MaterialTheme.typography.headlineLarge.copy(fontSize = 36.sp),
                color = AppTextWhite,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = userProfile.email,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 20.sp),
                color = AppTextWhite.copy(alpha = 0.8f)
            )
        }
    }
}

@Composable
fun SettingsCard(navController: NavController) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = AppLightBrown.copy(alpha = 0.85f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier
            .padding(vertical = 8.dp)
            .border(
                width = 4.dp,
                color = AppYellow,
                shape = RoundedCornerShape(16.dp)
            )
        ) {
            ProfileOptionItem(
                iconRes = R.drawable.settings,
                text1 = "Account",
                text2 = "Settings",
                onClick = { /* TODO: Navigate to Account Settings Screen */ }
            )
            Divider(
                color = AppYellow,
                thickness = 4.dp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            ProfileOptionItem(
                iconRes = R.drawable.game_controls,
                text1 = "Game",
                text2 = "Settings",
                onClick = { /* TODO: Navigate to Game Settings Screen */ }
            )
            Divider(
                color = AppYellow,
                thickness = 4.dp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            ProfileOptionItem(
                iconRes = R.drawable.diamond,
                text1 = "Purchase",
                text2 = "Premium",
                onClick = { navController.navigate(AppScreen.BuyPremium.route)}
            )
        }
    }
}

@Composable
fun ProfileOptionItem(
    @DrawableRes iconRes: Int,
    text1: String,
    text2: String,
    onClick: () -> Unit
) {
    Spacer(modifier = Modifier.height(20.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = "$text1 $text2",
            modifier = Modifier.size(80.dp),
            tint = AppTextWhite
        )
        Spacer(modifier = Modifier.width(20.dp))
        Column {
            Text(
                text = text1,
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 36.sp),
                color = AppTextWhite,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = text2,
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 36.sp),
                color = AppTextWhite,
                fontWeight = FontWeight.Bold
            )
        }
    }
    Spacer(modifier = Modifier.height(20.dp))
}