package com.example.models

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

@Serializable
data class MultiQuestion(
    @BsonId val id: Id<MultiQuestion>? = null,
    val askerId: String,
    val question: String,
    val answers: List<String> = emptyList()
)

@Serializable
data class MultiQuestionDto(
    val id: String? = null,
    val askerId: String,
    val question: String,
    val answers: List<String> = emptyList()
)

fun MultiQuestion.toDto() = MultiQuestionDto(
    id = this.id.toString(),
    askerId = this.askerId,
    question = this.question,
    answers = this.answers,
)

fun MultiQuestionDto.toServerModel() = MultiQuestion(
    askerId = this.askerId,
    question = this.question,
    answers = this.answers,
)