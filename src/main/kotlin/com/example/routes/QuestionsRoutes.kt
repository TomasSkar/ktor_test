package com.example.routes

import com.example.askerIdOrNull
import com.example.badRequestRespond
import com.example.models.ChooseQuestion
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Route.questionsRoutes(){
    route("/questions"){
        // To get all existing questions EXCEPT asker self
        get {
            call.askerIdOrNull()?.let { askerId ->
                fakeList.filterNot { it.askerId == askerId }
            } ?: call.badRequestRespond()
        }
        // To get asker self questions
        get("/asker"){
            call.askerIdOrNull()?.let { askerId ->
                fakeList.filter { it.askerId == askerId }
            } ?: call.badRequestRespond()
        }
    }
}

private val fakeList = listOf(
    ChooseQuestion(
        id = null,
        askerId = "11",
        question = "",
        yesCount = 1,
        noCount = 1
    )
)