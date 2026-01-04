package com.example.deimomoimain.viewmodel

import androidx.lifecycle.ViewModel
import com.example.deimomoimain.model.LeaderboardItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {
    private val _leaderboardItems = MutableStateFlow<List<LeaderboardItem>>(emptyList())
    val leaderboardItems: StateFlow<List<LeaderboardItem>> = _leaderboardItems.asStateFlow()

    init {
        loadLeaderboard()
    }

    private fun loadLeaderboard() {
        _leaderboardItems.value = listOf(
            LeaderboardItem("1", 1, "Momoi", 1984),
            LeaderboardItem("2", 2, "Dooby", 1756),
            LeaderboardItem("3", 3, "Nimi", 1679),
            LeaderboardItem("4", 4, "Dokibird", 1500),
            LeaderboardItem("5", 5, "Senzawa", 1450)
        )
    }
}