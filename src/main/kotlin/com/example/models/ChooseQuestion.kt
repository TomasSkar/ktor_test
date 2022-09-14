package com.example.models

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

@Serializable
data class ChooseQuestion(
    @BsonId
    val id: Id<ChooseQuestion>? = null,
    val askerId: String,
    val question: String,
    val yesCount: Int = 0,
    val noCount: Int = 0
)

@Serializable
data class ChooseQuestionDto(
    val id: String? = null,
    val askerId: String,
    val question: String,
    val yesCount: Int = 0,
    val noCount: Int = 0
)

fun ChooseQuestion.toDto() = ChooseQuestionDto(
    id = this.id.toString(),
    askerId = this.askerId,
    question = this.question,
    yesCount = this.yesCount,
    noCount = this.noCount
)

fun ChooseQuestionDto.toServerModel() = ChooseQuestion(
    askerId = this.askerId,
    question = this.question,
    yesCount = this.yesCount,
    noCount = this.noCount
)