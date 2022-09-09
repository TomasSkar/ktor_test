package com.example.routes

import com.example.services.PersonService
import com.example.models.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.personRoutes() {

    val personService: PersonService by inject()

    route("/persons"){
        get {
            val persons = personService.findAll().map(Person::toDto)
            if(persons.isNotEmpty()){
                call.respond(persons)
            } else {
                call.respond(
                    status = HttpStatusCode.NotFound,
                    message = "No persons found"
                )
            }
        }
    }
    route("/person"){
        // Create
        post {
            val request = call.receive<PersonDto>()
            val person = request.toPerson()
            personService.create(person)?.let { userId ->
                call.response.headers.append("My-User-Id-Header", userId.toString())
                call.respond(
                    status = HttpStatusCode.Created,
                    message = "Created new person with name ${person.name}"
                )
            } ?: call.respond(
                status = HttpStatusCode.BadRequest,
                message = ErrorResponse.BAD_REQUEST_RESPONSE
            )
        }
        // Request path parameter
        get("/{id}"){
            val personIdFromRequest = call.parameters["id"].toString()
            personService.findById(personIdFromRequest)?.let { person ->
                val personDto = person.toDto()
                call.respond(personDto)
            } ?: call.respond(
                status = HttpStatusCode.NotFound,
                message = ErrorResponse.NOT_FOUND_RESPONSE
            )
        }
        // Query parameter
        get("/search") {
            val name = call.parameters["name"].toString()
            personService.findByName(name)?.let { persons ->
                val personsDto = persons.map(Person::toDto)
                call.respond(personsDto)
            } ?: call.respond(
                status = HttpStatusCode.NotFound,
                message = ErrorResponse.NOT_FOUND_RESPONSE
            )
        }
        // Update
        put("/{id}") {
            val id = call.parameters["id"].toString()
            val personRequest = call.receive<PersonDto>()
            val person = personRequest.toPerson()
            val updatedSuccessfully = personService.updatePerson(
                personId = id,
                request = person
            )
            if (updatedSuccessfully) {
                call.respond(
                    status = HttpStatusCode.OK,
                    message = "Updated"
                )
            } else {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = ErrorResponse.BAD_REQUEST_RESPONSE
                )
            }
        }
        delete("/{id}"){
            val id = call.parameters["id"].toString()
            val deleteResponse = personService.deletePerson(id)
            if(deleteResponse){
                call.respond(
                    status = HttpStatusCode.OK,
                    message = "Deleted"
                )
            } else {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = ErrorResponse.BAD_REQUEST_RESPONSE
                )
            }
        }
    }
}