package com.example

import com.example.models.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

suspend fun ApplicationCall.badRequestRespond() {
    respond(
        status = HttpStatusCode.BadRequest,
        message = ErrorResponse.BAD_REQUEST_RESPONSE
    )
}

suspend fun ApplicationCall.databaseErrorRespond(message: String? = null) {
    respond(
        status = HttpStatusCode.ExpectationFailed,
        message = message ?: ErrorResponse.DATABASE_ERROR
    )
}

fun ApplicationCall.askerIdOrNull(): String? {
    val name = parameters["askerId"].toString()
    return name.ifBlank { return null }
}

fun PostChooseQuestionRequest.isValid(): Boolean {
    return askerId.isNotBlank() && question.isNotBlank()
}

fun PostMultiQuestionRequest.isValid(): Boolean {
    return askerId.isNotBlank() && question.isNotBlank()
}

fun PostChooseQuestionAnswerRequest.isValid(): Boolean {
    return askerId.isNotBlank() && questionId.isNotBlank()
}

fun PostMultiQuestionAnswerRequest.isValid(): Boolean {
    return askerId.isNotBlank() && questionId.isNotBlank() && answer.isNotBlank()
}