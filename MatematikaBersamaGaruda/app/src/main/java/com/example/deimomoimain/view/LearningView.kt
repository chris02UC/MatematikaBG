
package com.example.deimomoimain.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.deimomoimain.PremiumState
import com.example.deimomoimain.R
import com.example.deimomoimain.navigation.AppScreen
import com.example.deimomoimain.ui.theme.*
import com.example.deimomoimain.ui.composables.*

// Data holder for each lesson card
private data class SectionItem(val icon: ImageVector, val label: String)

@Composable
fun LearningView(navController: NavController) {
    /*var premiumUnlocked by remember { mutableStateOf(false) }*/
    val premiumUnlocked by PremiumState.unlocked
    val backgroundColor = Color(momoiDark)
    val cardColor = Color(momoiPink)

    val basicItems = listOf(
        SectionItem(Icons.Default.Add, "Addition"),
        SectionItem(Icons.Default.Remove, "Subtraction"),
        SectionItem(Icons.Default.Close, "Multiplication"),
        SectionItem(Icons.Default.Home, "Division")
    )
    val otherSections = listOf("Algebra", "Calculus")

    Scaffold(
        topBar = { AppTopBar("Learning") },
        bottomBar = { AppBottomNavigationBar(navController = navController)},
        containerColor = backgroundColor
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(paddingValues)
        ) {
            Spacer(Modifier.height(16.dp))
            // Basic math always visible/unlocked
            LearningSection(title = "Basic math", items = basicItems, cardColor = cardColor)
            Spacer(Modifier.height(24.dp))
            // Other sections in a Box to overlay unlock UI
            Box(modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(otherSections) { section ->
                        LearningSection(title = section, items = basicItems, cardColor = cardColor)
                    }
                }
                if (!premiumUnlocked) {
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(Color.White.copy(alpha = 0.9f))
                            .clickable { navController.navigate(AppScreen.BuyPremium.route) }
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Locked",
                                tint = AppBackgroundBrown,
                                modifier = Modifier
                                    .size(60.dp)
                            )
                            Text (
                                text = "Unlock with\nPremium",
                                color = AppBackgroundBrown,
                                style = MaterialTheme.typography.headlineSmall.copy(fontSize = 24.sp),
                                modifier = Modifier,
                                textAlign = TextAlign.Center,
                                lineHeight = 20.sp
                            )
                            Spacer(Modifier.height(100.dp))
                            /*TODO:BUTTON FOR DEBUGGING REMOVE ON PROTOTYPE*/
/*                            Button(onClick = { premiumUnlocked = true },
                                colors = ButtonDefaults.buttonColors(Color(momoiYellow))) {
                                Text("Unlock All")

                            }*/
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LearningSection(
    title: String,
    items: List<SectionItem>,
    cardColor: Color
) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineLarge.copy(color = Color.White),
        fontFamily = LilitaOne,
        modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
    )
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items) { item ->
            Card(
                colors = CardDefaults.cardColors(containerColor = cardColor),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .width(160.dp)
                    .aspectRatio(1f)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        modifier = Modifier.size(96.dp),
                        tint = Color.White
                    )
                    Text(
                        text = item.label,
                        style = MaterialTheme.typography.bodyLarge.copy(color = Color.White, fontSize = 20.sp),
                        fontFamily = LilitaOne
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LearningViewPreview() {
    val navController = rememberNavController()
    LearningView(navController)
}
