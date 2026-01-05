package com.example.deimomoimain.model

data class DrawUiState(
    val recognizedDigits: String = "",
    val currentStroke: List<Pair<Float, Float>> = emptyList(),
    val allStrokes: List<List<Pair<Float, Float>>> = emptyList()
)
