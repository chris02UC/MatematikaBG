package com.example.deimomoimain.navigation


sealed class AppScreen(val route: String) {
    // Core Screens
    object Home : AppScreen("home_screen") // HomeScreen.kt
    object Login : AppScreen("login_screen") // LoginView.kt
    object Register : AppScreen("register_screen") // RegisterView.kt
    object ChooseType : AppScreen("choose_battle_type_screen")
    object Learning : AppScreen("learning_screen") // LearningView.kt
    object Profile : AppScreen("profile_screen") // ProfileView.kt
    object BuyPremium : AppScreen("buy_premium_screen") // BuyPremiumView.kt
    object Custom : AppScreen("custom_screen") // CustomView.kt
    object CustomLobby : AppScreen("custom_lobby_screen") // CustomLobbyView.kt
    object CustomQuestions : AppScreen("custom_questions_screen") // CustomQuestionsView.kt

    // Main Battle Screens (if these are distinct from input-specific screens)
    object DirectBattleScreen : AppScreen("direct_battle_screen") // DirectBattleScreen.kt
    object EnduranceBattleScreen : AppScreen("endurance_battle_screen") // EnduranceBattleScreen.kt

    // Input Method Specific Battle Views
    object DirectBattleDrawScreen : AppScreen("direct_battle_draw_screen") // DirectBattleDrawScreen.kt or VersusViewDrawDB.kt
    object EnduranceBattleDrawScreen : AppScreen("endurance_battle_draw_screen") // EnduranceBattleDrawScreen.kt or VersusViewDrawEB.kt
    object VersusViewKeypadDB : AppScreen("versus_view_keypad_db") // VersusViewKeypadDB.kt
    object VersusViewKeypadEB : AppScreen("versus_view_keypad_eb") // VersusViewKeypadEB.kt

    // Utility Screens
    object GameOverScreen : AppScreen("game_over_screen") // GameOverScreen.kt
}