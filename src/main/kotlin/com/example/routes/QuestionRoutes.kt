package com.example.routes

import com.example.badRequestRespond
import com.example.data.QuestionsRepository
import com.example.isValid
import com.example.models.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.questionRoutes() {

    val questionsRepository: QuestionsRepository by inject()

    route("/question") {
        // To post new choose question
        post("/choose") {
            val questionRequest = call.receive<PostChooseQuestionRequest>()
            if (questionRequest.isValid()) {
                when (val postResult = questionsRepository.postNewChooseQuestion(questionRequest)) {
                    is Result.Failure -> call.respond(
                        status = postResult.code ?: HttpStatusCode.ExpectationFailed,
                        message = postResult.error ?: "Error in server"
                    )

                    is Result.Success -> call.respond(postResult.value)
                }
            } else {
                call.badRequestRespond()
            }
        }
        // To post new multi question
        post("/multi") {
            val questionRequest = call.receive<PostMultiQuestionRequest>()
            if (questionRequest.isValid()) {
                when (val postResult = questionsRepository.postNewMultiQuestion(questionRequest)) {
                    is Result.Failure -> call.respond(
                        status = postResult.code ?: HttpStatusCode.ExpectationFailed,
                        message = postResult.error ?: "Error in server"
                    )

                    is Result.Success -> call.respond(postResult.value)
                }
            } else {
                call.badRequestRespond()
            }
        }
        //To post answer to question
        route("/answer") {
            post("/choose") {
                val answerRequest = call.receive<PostChooseQuestionAnswerRequest>()
                if (answerRequest.isValid()) {
                    when (val postResult = questionsRepository.postChooseAnswer(answerRequest)) {
                        is Result.Failure -> call.respond(
                            status = postResult.code ?: HttpStatusCode.ExpectationFailed,
                            message = postResult.error ?: "Error in server"
                        )

                        is Result.Success -> call.respond(postResult.value)
                    }
                } else {
                    call.badRequestRespond()
                }
            }
            // To post new multi question
            post("/multi") {
                val answerRequest = call.receive<PostMultiQuestionAnswerRequest>()
                if (answerRequest.isValid()) {
                    when (val postResult = questionsRepository.postMultiAnswer(answerRequest)) {
                        is Result.Failure -> call.respond(
                            status = postResult.code ?: HttpStatusCode.ExpectationFailed,
                            message = postResult.error ?: "Error in server"
                        )

                        is Result.Success -> call.respond(postResult.value)
                    }
                } else {
                    call.badRequestRespond()
                }
            }
        }
    }
}
