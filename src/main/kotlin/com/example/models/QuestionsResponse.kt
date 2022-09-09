package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class QuestionsResponse(
    val chooseQuestions: List<ChooseQuestion> = emptyList(),
    val multiQuestions: List<MultiQuestion> = emptyList()
)