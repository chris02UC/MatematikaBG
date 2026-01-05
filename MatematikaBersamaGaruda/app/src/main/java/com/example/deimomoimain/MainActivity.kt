package com.example.deimomoimain

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.deimomoimain.navigation.AppScreen
import com.example.deimomoimain.ui.screens.HomeScreen
import com.example.deimomoimain.ui.theme.DEIMOMOIMAINTheme
import com.example.deimomoimain.view.*
import com.example.deimomoimain.viewmodel.DirectBattleViewModel
import com.example.deimomoimain.viewmodel.EnduranceBattleViewModel
import com.example.deimomoimain.views.VersusViewKeypadDB


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DEIMOMOIMAINTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val directVm: DirectBattleViewModel = viewModel()
    val enduranceVm: EnduranceBattleViewModel = viewModel()
    NavHost(navController = navController, startDestination = AppScreen.Login.route) {
        composable (AppScreen.Learning.route) {
            LearningView(navController = navController)
        }
        composable(AppScreen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(AppScreen.Login.route) {
            LoginView(navController = navController)
        }
        composable(AppScreen.Register.route) {
            RegisterView(navController = navController)
        }
        composable(AppScreen.Profile.route) {
            ProfileView(navController = navController) // Add ProfileScreen
        }
        composable(AppScreen.BuyPremium.route) {
            BuyPremiumView(navController = navController) // Pass navController if needed
        }
        composable(AppScreen.Custom.route) {
            CustomView(navController = navController) // Add CustomScreen
        }
        composable(AppScreen.CustomLobby.route) {
            CustomLobbyView(navController = navController) // Add CustomScreen
        }
        composable(AppScreen.CustomQuestions.route) {
            CustomQuestionsView(navController = navController) // Add CustomQuestionsScreen
        }

        // Choose input / battle type
        composable(AppScreen.ChooseType.route) {
            ChooseBattleTypeView(
                navController  = navController,
                directVm       = directVm,
                enduranceVm    = enduranceVm
            )
        }

//        // Main Battle Screens
//        composable(AppScreen.DirectBattleScreen.route) {
//            DirectBattleScreen(
//                navController = navController,
//                viewModel     = directVm
//            )
//        }

        composable(
            route = "direct_battle_screen/{level}",
            arguments = listOf(navArgument("level") { type = NavType.IntType })
        ) { backStackEntry ->
            val level = backStackEntry.arguments!!.getInt("level")
            DirectBattleScreen(
                navController = navController,
                level         = level,
                viewModel     = directVm
            )
        }

         composable(
               route = "endurance_battle_screen/{level}",
               arguments = listOf(navArgument("level") {
                     type = NavType.IntType
                   })
                     ) { backStack ->
               val lvl = backStack.arguments!!.getInt("level")
               EnduranceBattleScreen(
                     navController = navController,
                     level         = lvl,
                     viewModel     = enduranceVm
                           )
             }

//        composable(AppScreen.EnduranceBattleScreen.route) {
//            EnduranceBattleScreen(
//                navController = navController,
//                viewModel     = enduranceVm
//            )
//        }

        // Input Method Specific Battle Views
//        composable(AppScreen.DirectBattleDrawScreen.route) {
//            DirectBattleDrawScreen(
//                navController = navController,
//                directVm      = directVm
//            )
//        }

        composable(
            route = "direct_battle_draw_screen/{level}",
            arguments = listOf(navArgument("level") { type = NavType.IntType })
        ) { backStackEntry ->
            val level = backStackEntry.arguments!!.getInt("level")
            DirectBattleDrawScreen(
                navController = navController,
                level         = level,
                directVm      = directVm
            )
        }

         composable(
               route = "endurance_battle_draw_screen/{level}",
               arguments = listOf(navArgument("level") {
                     type = NavType.IntType
                   })
                     ) { backStack ->
               val lvl = backStack.arguments!!.getInt("level")
               EnduranceBattleDrawScreen(
                     navController = navController,
                     level         = lvl,
                     enduranceVm   = enduranceVm
                           )
             }

//        composable(AppScreen.EnduranceBattleDrawScreen.route) {
//            EnduranceBattleDrawScreen(
//                navController  = navController,
//                enduranceVm    = enduranceVm
//            )
//        }

        composable(AppScreen.VersusViewKeypadDB.route) {
            VersusViewKeypadDB("", 0, "", 0, 0, {  })
        }

        composable(AppScreen.VersusViewKeypadEB.route) {
            VersusViewKeypadEB("", "", 0, "", 0, 0, {  })
        }

        composable(AppScreen.ChooseType.route) {
            ChooseBattleTypeView(navController = navController)
        }
    }
}