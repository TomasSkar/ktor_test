package com.example.routes

import com.example.models.Customer
import com.example.models.customerStorage
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.customerRouting() {
    route("/customer"){
        get {
            if(customerStorage.isNotEmpty()){
                call.respond(customerStorage)
            } else {
                call.respondText(
                    text = "No customers found",
                    status = HttpStatusCode.OK
                )
            }
        }
        get("{id?}"){
            val id = call.parameters["id"] ?: return@get call.respondText(
                text = "Missing id",
                status = HttpStatusCode.BadRequest
            )
            val customer = customerStorage.find { it.id == id } ?: call.respondText(
                text = "User not found",
                status = HttpStatusCode.NotFound
            )
            call.respond(customer)
        }
        post {
            val customer = call.receive<Customer>()
            customerStorage.add(customer)
            call.respondText(
                text = "Customer with name ${customer.firstName} created",
                status = HttpStatusCode.Created
            )
        }
        delete("{id}"){
            val id = call.parameters["id"] ?: call.respond(HttpStatusCode.BadRequest)
            if(customerStorage.removeIf{ it.id == id}){
                call.respondText(
                    text = "User removed",
                    status = HttpStatusCode.Accepted
                )
            } else {
                call.respondText(
                    text = "User not found",
                    status = HttpStatusCode.NotFound
                )
            }
        }
    }
}