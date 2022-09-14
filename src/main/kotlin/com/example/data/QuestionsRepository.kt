package com.example.data

import com.example.models.*
import com.example.services.AskerService
import com.example.services.ChooseQuestionService
import com.example.services.MultiQuestionService

interface QuestionsRepository {
    suspend fun postNewChooseQuestion(chooseQuestionRequest: PostChooseQuestionRequest): Result<QuestionsResponse>
    fun postNewMultiQuestion(multiQuestionRequest: PostMultiQuestionRequest): Result<QuestionsResponse>
    fun postChooseAnswer(chooseAnswerRequest: PostChooseQuestionAnswerRequest): QuestionsResponse?
    fun postMultiAnswer(multiAnswerRequest: PostMultiQuestionAnswerRequest): QuestionsResponse?
}

class QuestionsRepositoryImpl(
    private val chooseQuestionService: ChooseQuestionService,
    private val multiQuestionsService: MultiQuestionService,
    // private val askerService: AskerService,
): QuestionsRepository{
    override suspend fun postNewChooseQuestion(chooseQuestionRequest: PostChooseQuestionRequest): Result<QuestionsResponse> {
        // Filtered Asker questions
        return when(val insertResult = chooseQuestionService.postNewChooseQuestion(chooseQuestionRequest)){
            is Result.Failure -> Result.Failure(code = insertResult.code, error = insertResult.error)
            is Result.Success -> {
                val chooseQuestions = chooseQuestionService.getAllChooseQuestions()
                val multiQuestions = multiQuestionsService.getAllMultiQuestions()
                Result.Success(
                    value = QuestionsResponse(
                        chooseQuestions = chooseQuestions.map(ChooseQuestion::toDto),
                        multiQuestions = multiQuestions.map(MultiQuestion::toDto)
                    )
                )
            }
        }
    }

    override fun postNewMultiQuestion(multiQuestionRequest: PostMultiQuestionRequest): Result<QuestionsResponse> {
        // Filtered Asker questions
        return when(val insertResult = multiQuestionsService.postNewMultiQuestion(multiQuestionRequest)){
            is Result.Failure -> Result.Failure(code = insertResult.code, error = insertResult.error)
            is Result.Success -> {
                val chooseQuestions = chooseQuestionService.getAllChooseQuestions()
                val multiQuestions = multiQuestionsService.getAllMultiQuestions()
                Result.Success(
                    value = QuestionsResponse(
                        chooseQuestions = chooseQuestions.map(ChooseQuestion::toDto),
                        multiQuestions = multiQuestions.map(MultiQuestion::toDto)
                    )
                )
            }
        }
    }

    override fun postChooseAnswer(chooseAnswerRequest: PostChooseQuestionAnswerRequest): QuestionsResponse? {
        // Add to Asker answered questions
        return null
    }

    override fun postMultiAnswer(multiAnswerRequest: PostMultiQuestionAnswerRequest): QuestionsResponse? {
        // Add to Asker answered questions
        return null
    }

}