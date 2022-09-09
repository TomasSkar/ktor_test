package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(val message: String) {
    companion object {
        val NOT_FOUND_RESPONSE = ErrorResponse("Content not found")
        val BAD_REQUEST_RESPONSE = ErrorResponse("Invalid request")
        val DATABASE_ERROR = ErrorResponse("Error with database")
    }
}