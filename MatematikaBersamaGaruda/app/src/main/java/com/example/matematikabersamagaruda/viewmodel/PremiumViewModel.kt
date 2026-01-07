package com.example.matematikabersamagaruda.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.matematikabersamagaruda.ui.theme.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class PremiumPlan(
    val id: String,
    val title: String,
    val price: String,
    val titleColor: Color,
    val description: String,
    val features: List<String>
)

class BuyPremiumViewModel : ViewModel() {
    private val _premiumPlans = MutableStateFlow<List<PremiumPlan>>(emptyList())
    val premiumPlans: StateFlow<List<PremiumPlan>> = _premiumPlans.asStateFlow()

    init {
        loadPremiumPlans()
    }

    private fun loadPremiumPlans() {
        _premiumPlans.value = listOf(
            PremiumPlan(
                id = "personal",
                title = "Personal",
                price = "35000 IDR",
                titleColor = AppPremiumPink,
                description = "Monthly access to premium features",
                features = listOf(
                    "1 device",
                    "Touch gameplay",
                    "Access to advanced learning lessons",
                    "Custom games"
                )
            ),
            PremiumPlan(
                id = "institution",
                title = "Institution",
                price = "200000 IDR",
                titleColor = AppBlue,
                description = "Monthly access to premium features",
                features = listOf(
                    "10 devices",
                    "Touch gameplay",
                    "Access to advanced learning lessons",
                    "Custom games",
                    "Add more devices for only 25000 IDR per device"
                )
            )
        )
    }
}