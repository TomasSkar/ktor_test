package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class PostChooseQuestionRequest(
    val askerId: String,
    val question: String,
)

@Serializable
data class PostMultiQuestionRequest(
    val askerId: String,
    val question: String,
)