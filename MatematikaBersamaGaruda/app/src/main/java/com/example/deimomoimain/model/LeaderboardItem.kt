package com.example.deimomoimain.model

import androidx.annotation.DrawableRes

data class LeaderboardItem(
    val id: String,
    val rank: Int,
    val name: String,
    val score: Int,
    @DrawableRes val avatarPlaceholder: Int? = null // For future avatar, using placeholder color for now
)