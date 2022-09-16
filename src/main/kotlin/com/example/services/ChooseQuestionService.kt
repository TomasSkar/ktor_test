package com.example.services

import com.example.models.*
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import io.ktor.http.*
import org.bson.types.ObjectId
import org.litote.kmongo.*
import org.litote.kmongo.id.toId

interface ChooseQuestionService {
    companion object {
        const val CHOOSE_QUESTION_DB_NAME = "choose_question_db"
    }

    fun getAllChooseQuestions(): List<ChooseQuestion>

    suspend fun postNewChooseQuestion(chooseQuestionRequest: PostChooseQuestionRequest): Result<String>
    fun postNewChooseQuestionAnswer(chooseAnswerRequest: PostChooseQuestionAnswerRequest): Result<Boolean>
}

class ChooseQuestionServiceImpl(
    client: MongoClient
) : ChooseQuestionService {

    private val database = client.getDatabase(ChooseQuestionService.CHOOSE_QUESTION_DB_NAME)
    private val chooseQuestionCollection = database.getCollection<ChooseQuestion>()

    override fun getAllChooseQuestions(): List<ChooseQuestion> {
        return chooseQuestionCollection.find().toList()
    }

    override suspend fun postNewChooseQuestion(chooseQuestionRequest: PostChooseQuestionRequest): Result<String> {
        return try {
            val chooseQuestion = ChooseQuestion(
                askerId = chooseQuestionRequest.askerId,
                question = chooseQuestionRequest.question,
            )
            val result = chooseQuestionCollection.insertOne(chooseQuestion)
            val insertId = if (chooseQuestion.id != null) chooseQuestion.id.toString() else null
            insertId?.let {
                Result.Success(value = it)
            } ?: Result.Failure(code = HttpStatusCode.InternalServerError, "Error while inserting")
        } catch (e: Exception) {
            Result.Failure(code = HttpStatusCode.InternalServerError, e.message)
        }
    }

    override fun postNewChooseQuestionAnswer(chooseAnswerRequest: PostChooseQuestionAnswerRequest): Result<Boolean> {
        return try {
            val questionId: Id<ChooseQuestion> = ObjectId(chooseAnswerRequest.questionId).toId()
            val existingQuestion =
                chooseQuestionCollection.findOne(ChooseQuestion::id eq questionId)
                    ?: return Result.Failure(
                        code = HttpStatusCode.InternalServerError,
                        "Question with this ID not found"
                    )
            val yesCount = if (chooseAnswerRequest.answer == PostChooseQuestionAnswerType.YES) {
                existingQuestion.yesCount + 1
            } else {
                existingQuestion.yesCount
            }
            val noCount = if (chooseAnswerRequest.answer == PostChooseQuestionAnswerType.NO) {
                existingQuestion.noCount + 1
            } else {
                existingQuestion.noCount
            }
            val updateResult = chooseQuestionCollection.replaceOne(
                replacement = existingQuestion.copy(
                    yesCount = yesCount,
                    noCount = noCount
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
