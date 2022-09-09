package com.example.data

import com.example.models.*

interface QuestionsRepository {
    fun postNewChooseQuestion(chooseQuestionRequest: PostChooseQuestionRequest): QuestionsResponse?
    fun postNewMultiQuestion(multiQuestionRequest: PostMultiQuestionRequest): QuestionsResponse?
    fun postChooseAnswer(chooseAnswerRequest: PostChooseQuestionAnswerRequest): QuestionsResponse?
    fun postMultiAnswer(multiAnswerRequest: PostMultiQuestionAnswerRequest): QuestionsResponse?
}

class QuestionsRepositoryImpl(/*inject services*/): QuestionsRepository {
    override fun postNewChooseQuestion(chooseQuestionRequest: PostChooseQuestionRequest): QuestionsResponse? {
        // Filtered Asker questions
        return null
    }

    override fun postNewMultiQuestion(multiQuestionRequest: PostMultiQuestionRequest): QuestionsResponse? {
        // Filtered Asker questions
        return null
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