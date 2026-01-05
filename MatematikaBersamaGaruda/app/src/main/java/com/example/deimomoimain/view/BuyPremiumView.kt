package com.example.deimomoimain.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.deimomoimain.PremiumState
import com.example.deimomoimain.R // Your R file
import com.example.deimomoimain.ui.theme.* // Import all your theme colors
import com.example.deimomoimain.viewmodel.BuyPremiumViewModel
import com.example.deimomoimain.viewmodel.PremiumPlan

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuyPremiumView(
    navController: NavController,
    buyPremiumViewModel: BuyPremiumViewModel = viewModel()

) {
    val premiumPlans by buyPremiumViewModel.premiumPlans.collectAsState()
    val premiumUnlocked by PremiumState.unlocked
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            AppPremiumGradientTop,
                            AppPremiumGradientBottom
                        )
                    )
                )
        )

        Scaffold ( containerColor = Color.Transparent
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 20.dp) // Horizontal padding for the whole content
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(30.dp))

                Image(
                    painter = painterResource(id = R.drawable.strongestracista),
                    contentDescription = "App Logo",
                    modifier = Modifier.size(150.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Become a\nPremium Player!",
                    style = MaterialTheme.typography.displaySmall.copy(fontSize = 48.sp), // LilitaOne
                    color = AppNavSelected, // Dark brown text color
                    textAlign = TextAlign.Center,
                    lineHeight = 36.sp
                )

                Spacer(modifier = Modifier.height(30.dp))

                premiumPlans.forEach { plan ->
                    PremiumPlanCard(plan = plan)
                    Spacer(modifier = Modifier.height(24.dp))
                }

                Spacer(modifier = Modifier.height(20.dp)) // Bottom padding
            }
        }
    }
}

@Composable
fun PremiumPlanCard(plan: PremiumPlan) {
    Card(
        shape = RoundedCornerShape(20.dp), // More rounded corners
        colors = CardDefaults.cardColors(containerColor = AppPremiumCardBackground.copy(alpha = 0.85f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = plan.title,
                style = MaterialTheme.typography.displayMedium.copy(fontSize = 40.sp), // LilitaOne
                color = plan.titleColor
            )
            Text(
                text = plan.price,
                style = MaterialTheme.typography.headlineSmall.copy(fontSize = 20.sp), // LilitaOne
                color = plan.titleColor,
                modifier = Modifier.padding(top = 0.dp, bottom = 16.dp)
            )

            // Inner card for features
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White), // White background for feature list
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = plan.description,
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 15.sp), // LilitaOne, smaller
                        color = AppDarkGray,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    plan.features.forEach { feature ->
                        FeatureListItem(text = feature)
                    }
                }
            }


            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { PremiumState.unlocked.value = true },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AppSubscribeButtonBackground),
                modifier = Modifier
                    .fillMaxWidth(0.8f) // Button width relative to card
                    .height(55.dp),
                contentPadding = PaddingValues(vertical = 10.dp)
            ) {
                Text(
                    text = "Subscribe",
                    style = MaterialTheme.typography.headlineMedium.copy(fontSize = 26.sp), // LilitaOne
                    color = AppTextWhite
                )
            }
        }
    }
}

@Composable
fun FeatureListItem(text: String) {
    Row(
        verticalAlignment = Alignment.Top, // Align bullet with first line of text
        modifier = Modifier.padding(vertical = 3.dp)
    ) {
        Text(
            text = "• ", // Bullet point
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 15.sp), // LilitaOne, smaller
            color = AppDarkGray,
            modifier = Modifier.offset(y = (-1).dp) // Slight offset for better alignment with LilitaOne
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 15.sp), // LilitaOne, smaller
            color = AppDarkGray,
            lineHeight = 18.sp
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BuyPremiumViewPreview() {
    DEIMOMOIMAINTheme {
        BuyPremiumView(navController = NavController(context = LocalContext.current))
    }
}