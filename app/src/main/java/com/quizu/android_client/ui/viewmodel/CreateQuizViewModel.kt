package com.quizu.android_client.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.quizu.kotlin.model.Quiz
import com.quizu.kotlin.model.UserAccount
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class QuizUiState(
    val ownerEmail: String = "",
    val title: String = "",
    val description: String = "",
)

class CreateQuizViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()


    fun createQuiz() {
        val quiz = Quiz(
            quizOwnerEmail = uiState.value.ownerEmail,
            title = uiState.value.title,
            description = uiState.value.description,
        )
    }
}