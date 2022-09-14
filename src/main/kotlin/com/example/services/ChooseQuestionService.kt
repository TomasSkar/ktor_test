package com.example.services

import com.example.models.ChooseQuestion
import com.example.models.Person
import com.example.models.PostChooseQuestionRequest
import com.example.models.Result
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import io.ktor.http.*
import org.litote.kmongo.getCollection

interface ChooseQuestionService {
    companion object {
        const val CHOOSE_QUESTION_DB_NAME = "choose_question_db"
    }
    fun getAllChooseQuestions(): List<ChooseQuestion>
    fun getAskerChooseQuestions(askerId: String): List<ChooseQuestion>
    fun getOtherThanAskerChooseQuestions(askerId: String): List<ChooseQuestion>

    suspend fun  postNewChooseQuestion(chooseQuestionRequest: PostChooseQuestionRequest): Result<String>
}

class ChooseQuestionServiceImpl(
    client: MongoClient
): ChooseQuestionService {

    private val database = client.getDatabase(ChooseQuestionService.CHOOSE_QUESTION_DB_NAME)
    private val chooseQuestionCollection = database.getCollection<ChooseQuestion>()

    override fun getAllChooseQuestions(): List<ChooseQuestion> {
        return chooseQuestionCollection.find().toList()
    }

    override fun getAskerChooseQuestions(askerId: String): List<ChooseQuestion> {
        TODO("Not yet implemented")
    }

    override fun getOtherThanAskerChooseQuestions(askerId: String): List<ChooseQuestion> {
        TODO("Not yet implemented")
    }

    override suspend fun postNewChooseQuestion(chooseQuestionRequest: PostChooseQuestionRequest): Result<String> {
        return try {
            val chooseQuestion = ChooseQuestion(
                askerId = chooseQuestionRequest.askerId,
                question = chooseQuestionRequest.question,
            )
            val result = chooseQuestionCollection.insertOne(chooseQuestion)
            val insertId = if(chooseQuestion.id != null) chooseQuestion.id.toString() else null
            insertId?.let {
                Result.Success(value = it)
            } ?: Result.Failure(code = HttpStatusCode.InternalServerError, "Error while inserting")
        } catch (e: Exception){
            Result.Failure(code = HttpStatusCode.InternalServerError, e.message)
        }
    }
}
