package com.example.matematikabersamagaruda.viewmodel

import androidx.lifecycle.ViewModel
import com.example.matematikabersamagaruda.ui.theme.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class UserProfile(
    val name: String = "Momoi",
    val email: String = "momoi@gmail.com",
    val avatarPlaceholderColor: androidx.compose.ui.graphics.Color = AppRed
)

class ProfileViewModel : ViewModel() {
    private val _userProfile = MutableStateFlow(UserProfile())
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()
}