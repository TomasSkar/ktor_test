package com.example.services

import com.example.models.QuestionsResponse
import com.mongodb.client.MongoClient

interface AskerService {

}

class AskerServiceImp(
    client: MongoClient
): AskerService {

}