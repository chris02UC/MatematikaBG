package com.example.matematikabersamagaruda.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.matematikabersamagaruda.R
import com.example.matematikabersamagaruda.navigation.AppScreen
import com.example.matematikabersamagaruda.ui.theme.AppDarkGray
import com.example.matematikabersamagaruda.ui.theme.AppNavBackground
import com.example.matematikabersamagaruda.ui.theme.AppNavSelected

@Composable
fun RotatedBackground(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF3A241D)) // Dark brown fallback
            .clipToBounds() // Important for rotated image performance & clipping
    ) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "Background Pattern",
            modifier = Modifier
                .scale(3f)
                .rotate(15f)
                .offset(y = 100.dp),
            contentScale = ContentScale.Crop
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(title: String) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.displayMedium,
                color = AppNavSelected
            )
        },
        actions = {
            Image(
                painter = painterResource(id = R.drawable.strongestracista), // Replace with your logo
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(96.dp)
                    .padding(end = 16.dp)
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = AppNavBackground
        )
    )
}

data class NavItem(
    val label: String,
    val iconRes: Int,
    val screenRoute: String
)

@Composable
fun AppBottomNavigationBar(navController: NavController) {
    val navItems = listOf(
        NavItem("Book", R.drawable.book, AppScreen.Learning.route),
        NavItem("Home", R.drawable.home, AppScreen.Home.route),
        NavItem("Profile", R.drawable.profile, AppScreen.Profile.route)
    )

    NavigationBar(
        containerColor = AppNavBackground,
        modifier = Modifier.height(120.dp)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        navItems.forEach { item ->
            val isSelected = currentDestination?.hierarchy?.any { it.route == item.screenRoute } == true
            this.NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(item.screenRoute) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconRes),
                        contentDescription = item.label,
                        modifier = Modifier.size(if (isSelected) 64.dp else 48.dp),
                        tint = if (isSelected) AppNavSelected else AppDarkGray
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}