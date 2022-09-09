package com.example.services

import com.example.models.QuestionsResponse
import com.mongodb.client.MongoClient

interface AskerService {
    fun getAskerQuestions(askerId: String): QuestionsResponse?
}

class AskerServiceImp(
    client: MongoClient
): AskerService {

    override fun getAskerQuestions(askerId: String): QuestionsResponse? {
        return null
    }

}