package com.example.services

import com.example.models.*
import com.mongodb.client.MongoClient
import io.ktor.http.*
import org.bson.types.ObjectId
import org.litote.kmongo.*
import org.litote.kmongo.id.toId

interface MultiQuestionService {
    companion object {
        const val MULTI_QUESTION_DB_NAME = "multi_question_db"
    }

    fun getAllMultiQuestions(): List<MultiQuestion>
    fun postNewMultiQuestion(multiQuestionRequest: PostMultiQuestionRequest): Result<String>
    fun postNewChooseQuestionAnswer(multiAnswerRequest: PostMultiQuestionAnswerRequest): Result<Boolean>
}

class MultiQuestionServiceImpl(
    client: MongoClient
) : MultiQuestionService {

    private val database = client.getDatabase(MultiQuestionService.MULTI_QUESTION_DB_NAME)
    private val multiQuestionCollection = database.getCollection<MultiQuestion>()
    override fun getAllMultiQuestions(): List<MultiQuestion> {
        return multiQuestionCollection.find().toList()
    }

    override fun postNewMultiQuestion(multiQuestionRequest: PostMultiQuestionRequest): Result<String> {
        return try {
            val multiQuestion = MultiQuestion(
                askerId = multiQuestionRequest.askerId,
                question = multiQuestionRequest.question
            )
            val result = multiQuestionCollection.insertOne(multiQuestion)
            val insertId = if (multiQuestion.id != null) multiQuestion.id.toString() else null
            insertId?.let {
                Result.Success(value = it)
            } ?: Result.Failure(code = HttpStatusCode.InternalServerError, "Error while inserting")
        } catch (e: Exception) {
            Result.Failure(code = HttpStatusCode.InternalServerError, e.message)
        }
    }

    override fun postNewChooseQuestionAnswer(multiAnswerRequest: PostMultiQuestionAnswerRequest): Result<Boolean> {
        return try {
            val questionId: Id<MultiQuestion> = ObjectId(multiAnswerRequest.questionId).toId()
            val existingQuestion =
                multiQuestionCollection.findOne(MultiQuestion::id eq questionId)
                    ?: return Result.Failure(
                        code = HttpStatusCode.InternalServerError,
                        "Question with this ID not found"
                    )
            val newAnswers = existingQuestion.answers + listOf(multiAnswerRequest.answer)
            val updateResult = multiQuestionCollection.replaceOne(
                replacement = existingQuestion.copy(
                    answers = newAnswers
                )
            )
            if (updateResult.modifiedCount == 1L) {
                Result.Success(value = true)
            } else {
                Result.Failure(code = HttpStatusCode.InternalServerError, "Error while updating")
            }
        } catch (e: Exception) {
            Result.Failure(code = HttpStatusCode.InternalServerError, e.message)
        }
    }
}