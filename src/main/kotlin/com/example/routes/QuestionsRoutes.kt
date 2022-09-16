package com.example.routes

import com.example.data.QuestionsRepository
import com.example.models.Result
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.questionsRoutes() {

    val questionsRepository: QuestionsRepository by inject()

    route("/questions") {
        // To get all existing questions EXCEPT asker self
        get {
            when (val result = questionsRepository.getAllQuestions()) {
                is Result.Failure -> call.respond(
                    status = result.code ?: HttpStatusCode.ExpectationFailed,
                    message = result.error ?: "Error in server"
                )

                is Result.Success -> call.respond(result.value)
            }
        }
    }
}
