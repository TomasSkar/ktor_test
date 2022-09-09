package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Asker(
    val id: String,
    val answeredQuestionsIds: List<String>,
    val askedQuestionsIds: List<String>,
)