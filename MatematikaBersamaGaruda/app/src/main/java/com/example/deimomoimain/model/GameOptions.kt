package com.example.deimomoimain.model

import androidx.annotation.DrawableRes
import com.example.deimomoimain.R

sealed class GameType(val displayName: String, @DrawableRes val iconRes: Int) {
    object Endurance : GameType(displayName = "Endurance", iconRes = R.drawable.flame) // Or R.drawable.flame
    object Direct : GameType(displayName = "Direct", iconRes = R.drawable.sword)
}

sealed class InputMethod(val displayName: String, @DrawableRes val iconRes: Int) {
    object Touch : InputMethod(displayName = "Touch", iconRes = R.drawable.pen) // Or R.drawable.pen
    object Keypad : InputMethod(displayName = "Keypad", iconRes = R.drawable.keypad)
}