package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class QuestionsResponse(
    val chooseQuestions: List<ChooseQuestionDto> = emptyList(),
    val multiQuestions: List<MultiQuestionDto> = emptyList()
)