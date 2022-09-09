package com.example.models

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

@Serializable
data class ChooseQuestion(
    @BsonId val id: Id<ChooseQuestion>? = null,
    val askerId: String,
    val question: String,
    val yesCount: Int,
    val noCount: Int
)