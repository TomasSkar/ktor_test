package com.example.routes

import com.example.askerIdOrNull
import com.example.badRequestRespond
import com.example.data.QuestionsRepository
import com.example.databaseErrorRespond
import com.example.isValid
import com.example.models.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.questionRoutes() {

    val questionsRepository: QuestionsRepository by inject()

    route("/question"){
        // To post new choose question
        post("/choose") {
            val questionRequest = call.receive<PostChooseQuestionRequest>()
            if(questionRequest.isValid()){
                val postResult = questionsRepository.postNewChooseQuestion(questionRequest)
                postResult?.let { call.respond(postResult) } ?: call.databaseErrorRespond()
            } else {
                call.badRequestRespond()
            }
        }
        // To post new multi question
        post("/multi"){
            val questionRequest = call.receive<PostMultiQuestionRequest>()
            if(questionRequest.isValid()){
                val postResult = questionsRepository.postNewMultiQuestion(questionRequest)
                postResult?.let { call.respond(postResult) } ?: call.databaseErrorRespond()
            } else {
                call.badRequestRespond()
            }
        }
        //To post answer to question
        route("/answer"){
            post("/choose") {
                val answerRequest = call.receive<PostChooseQuestionAnswerRequest>()
                if(answerRequest.isValid()){
                    val postResult: QuestionsResponse? = questionsRepository.postChooseAnswer(answerRequest)
                    postResult?.let { call.respond(postResult) } ?: call.databaseErrorRespond()
                } else {
                    call.badRequestRespond()
                }
            }
            // To post new multi question
            post("/multi"){
                val answerRequest = call.receive<PostMultiQuestionAnswerRequest>()
                if(answerRequest.isValid()){
                    val postResult: QuestionsResponse? = questionsRepository.postMultiAnswer(answerRequest)
                    postResult?.let { call.respond(postResult) } ?: call.databaseErrorRespond()
                } else {
                    call.badRequestRespond()
                }
            }
        }
    }
}