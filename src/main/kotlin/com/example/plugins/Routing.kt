package com.example.plugins

import com.example.routes.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.serialization.Serializable

@Serializable
data class ApiStartupResponse(
    val status: String,
    val version: String
)

fun Application.configureRouting() {
    routing {
        // Default
        route("/") {
            get {
                call.respond(
                    ApiStartupResponse(
                        status = "OK",
                        version = "1.0.0"
                    )
                )
            }
        }
        personRoutes()
        questionRoutes()
        questionsRoutes()
    }
}
