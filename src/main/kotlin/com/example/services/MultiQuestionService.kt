package com.example.services

import com.example.models.ChooseQuestion
import com.example.models.MultiQuestion
import com.example.models.Result
import com.example.models.PostMultiQuestionRequest
import com.mongodb.client.MongoClient
import io.ktor.http.*
import org.litote.kmongo.getCollection
import java.lang.Exception

interface MultiQuestionService {
    companion object {
        const val MULTI_QUESTION_DB_NAME = "multi_question_db"
    }
    fun getAllMultiQuestions(): List<MultiQuestion>
    fun getAskerMultiQuestions(askerId: String): List<MultiQuestion>
    fun getOtherThanAskerMultiQuestions(askerId: String): List<MultiQuestion>
    fun postNewMultiQuestion(multiQuestionRequest: PostMultiQuestionRequest): Result<String>
}

class MultiQuestionServiceImpl(
    client: MongoClient
): MultiQuestionService{
    private val database = client.getDatabase(MultiQuestionService.MULTI_QUESTION_DB_NAME)
    private val multiQuestionCollection = database.getCollection<MultiQuestion>()
    override fun getAllMultiQuestions(): List<MultiQuestion> {
        return multiQuestionCollection.find().toList()
    }

    override fun getAskerMultiQuestions(askerId: String): List<MultiQuestion> {
        TODO("Not yet implemented")
    }

    override fun getOtherThanAskerMultiQuestions(askerId: String): List<MultiQuestion> {
        TODO("Not yet implemented")
    }

    override fun postNewMultiQuestion(multiQuestionRequest: PostMultiQuestionRequest): Result<String> {
        return try {
            val multiQuestion = MultiQuestion(
                askerId = multiQuestionRequest.askerId,
                question = multiQuestionRequest.question
            )
            val result = multiQuestionCollection.insertOne(multiQuestion)
            val insertId = if(multiQuestion.id != null) multiQuestion.id.toString() else null
            insertId?.let {
                Result.Success(value = it)
            } ?: Result.Failure(code = HttpStatusCode.InternalServerError, "Error while inserting")
        } catch (e: Exception) {
            Result.Failure(code = HttpStatusCode.InternalServerError, e.message)
        }
    }
}