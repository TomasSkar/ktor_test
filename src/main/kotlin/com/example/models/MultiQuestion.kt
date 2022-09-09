package com.example.models

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

@Serializable
data class MultiQuestion(
    @BsonId val id: Id<MultiQuestion>? = null,
    val askerId: String,
    val question: String,
    val answers: List<String>
)