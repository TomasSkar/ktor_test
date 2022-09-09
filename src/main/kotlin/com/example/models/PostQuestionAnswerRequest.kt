package com.example.models

import kotlinx.serialization.Serializable

enum class PostChooseQuestionAnswerType{
    YES, NO
}
@Serializable
data class PostChooseQuestionAnswerRequest(
    val askerId: String,
    val questionId: String,
    val answer: PostChooseQuestionAnswerType
)

@Serializable
data class PostMultiQuestionAnswerRequest(
    val askerId: String,
    val questionId: String,
    val answer: String
)