package com.example.models

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

data class Person(
    @BsonId
    val id: Id<Person>? = null,
    val name: String,
    val age: Int
)

@Serializable
data class PersonDto(
    val id: String? = null,
    val name: String,
    val age: Int
)

fun Person.toDto(): PersonDto =
    PersonDto(
        id = this.id.toString(),
        name = this.name,
        age = this.age
    )
fun PersonDto.toPerson(): Person =
    Person(
        name = this.name,
        age = this.age
    )