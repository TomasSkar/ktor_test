package com.example.plugins

import com.example.PersonService
import com.example.routes.*
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

fun Application.configureRouting() {
    routing {
        route("/"){
            get { call.respond(status = HttpStatusCode.OK,"api" to "ok") }
        }
        customerRouting()
        orderRoutes()
        personRoutes()
    }
}
