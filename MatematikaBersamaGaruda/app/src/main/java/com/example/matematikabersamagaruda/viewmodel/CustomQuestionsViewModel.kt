package com.example.matematikabersamagaruda.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class QuestionTemplate(
    val id: String,
    val template: String
)

class CustomQuestionsViewModel : ViewModel() {
    private val _newQuestionText = MutableStateFlow("")
    val newQuestionText: StateFlow<String> = _newQuestionText.asStateFlow()

    private val _questionTemplates = MutableStateFlow<List<QuestionTemplate>>(emptyList())
    val questionTemplates: StateFlow<List<QuestionTemplate>> = _questionTemplates.asStateFlow()

    init {
        loadInitialTemplates()
    }

    fun onNewQuestionTextChange(text: String) {
        _newQuestionText.value = text
    }

    fun addQuestionTemplate() {
        if (_newQuestionText.value.isNotBlank()) {
            val newTemplate = QuestionTemplate(
                id = System.currentTimeMillis().toString(), // Simple unique ID
                template = _newQuestionText.value
            )
            _questionTemplates.update { currentList -> currentList + newTemplate }
            _newQuestionText.value = "" // Clear input field
        }
        // In a real app, you'd likely save this to persistent storage or a backend.
    }

    private fun loadInitialTemplates() {
        // These are the examples from the image
        _questionTemplates.value = listOf(
            QuestionTemplate("1", "{n} x {nn}"),
            QuestionTemplate("2", "{nnn} / {n}"),
            QuestionTemplate("3", "{n} + {n} x {n}"),
            QuestionTemplate("4", "{nn} - {n}"),
            QuestionTemplate("5", "{n} x 9"),
            QuestionTemplate("6", "{nn} + 99"),
            QuestionTemplate("7", "{nnn} x {nnn}"),
            QuestionTemplate("8", "1 x {n} + 2"),
            QuestionTemplate("9", "75 + {n} x 2"),
            QuestionTemplate("10", "{nn} + {nnnn}"),
            QuestionTemplate("11", "39 x 2"),
            QuestionTemplate("12", "27 / 3"),
            QuestionTemplate("13", "0 x {n}"),
            QuestionTemplate("14", "1234 + {nnnn}"),
            QuestionTemplate("15", "{n} x 50"),
            QuestionTemplate("16", "{nn} ^ 2"),
            QuestionTemplate("17", "{n} ^ {n}"),
            // Add more if there are more in the scrollable list from the image
        )
    }
}