package com.example

import com.example.models.QuestionsResponse

interface AskerController {
    suspend fun getAskerQuestions(askerId: String): QuestionsResponse?
    suspend fun getNotAskerQuestions(askerId: String): QuestionsResponse?
}
class AskerControllerImp {
}