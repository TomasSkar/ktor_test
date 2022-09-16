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