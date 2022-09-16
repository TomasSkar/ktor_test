package com.example.data

import com.example.models.*
import com.example.services.ChooseQuestionService
import com.example.services.MultiQuestionService

interface QuestionsRepository {
    fun getAllQuestions(): Result<QuestionsResponse>
    suspend fun postNewChooseQuestion(chooseQuestionRequest: PostChooseQuestionRequest): Result<QuestionsResponse>
    fun postNewMultiQuestion(multiQuestionRequest: PostMultiQuestionRequest): Result<QuestionsResponse>
    fun postChooseAnswer(chooseAnswerRequest: PostChooseQuestionAnswerRequest): Result<QuestionsResponse>
    fun postMultiAnswer(multiAnswerRequest: PostMultiQuestionAnswerRequest): Result<QuestionsResponse>
}

class QuestionsRepositoryImpl(
    private val chooseQuestionService: ChooseQuestionService,
    private val multiQuestionsService: MultiQuestionService,
) : QuestionsRepository {

    private fun questionsResponseSuccessResult(): Result<QuestionsResponse> {
        val chooseQuestions = chooseQuestionService.getAllChooseQuestions()
        val multiQuestions = multiQuestionsService.getAllMultiQuestions()
        return Result.Success(
            value = QuestionsResponse(
                chooseQuestions = chooseQuestions.map(ChooseQuestion::toDto),
                multiQuestions = multiQuestions.map(MultiQuestion::toDto)
            )
        )
    }

    override fun getAllQuestions(): Result<QuestionsResponse> {
        return questionsResponseSuccessResult()
    }

    override suspend fun postNewChooseQuestion(chooseQuestionRequest: PostChooseQuestionRequest): Result<QuestionsResponse> {
        return when (val insertResult = chooseQuestionService.postNewChooseQuestion(chooseQuestionRequest)) {
            is Result.Failure -> Result.Failure(code = insertResult.code, error = insertResult.error)
            is Result.Success -> questionsResponseSuccessResult()
        }
    }

    override fun postNewMultiQuestion(multiQuestionRequest: PostMultiQuestionRequest): Result<QuestionsResponse> {
        return when (val insertResult = multiQuestionsService.postNewMultiQuestion(multiQuestionRequest)) {
            is Result.Failure -> Result.Failure(code = insertResult.code, error = insertResult.error)
            is Result.Success -> questionsResponseSuccessResult()
        }
    }

    override fun postChooseAnswer(chooseAnswerRequest: PostChooseQuestionAnswerRequest): Result<QuestionsResponse> {
        return when (val postResult = chooseQuestionService.postNewChooseQuestionAnswer(chooseAnswerRequest)) {
            is Result.Failure -> Result.Failure(code = postResult.code, error = postResult.error)
            is Result.Success -> questionsResponseSuccessResult()
        }
    }

    override fun postMultiAnswer(multiAnswerRequest: PostMultiQuestionAnswerRequest): Result<QuestionsResponse> {
        return when (val postResult = multiQuestionsService.postNewChooseQuestionAnswer(multiAnswerRequest)) {
            is Result.Failure -> Result.Failure(code = postResult.code, error = postResult.error)
            is Result.Success -> questionsResponseSuccessResult()
        }
    }

}