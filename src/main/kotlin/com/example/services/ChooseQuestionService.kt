package com.example.services

import com.example.models.ChooseQuestion

interface ChooseQuestionService {
    fun getAllChooseQuestions(): List<ChooseQuestion>
    fun getAskerChooseQuestions(askerId: String): List<ChooseQuestion>
    fun getOtherThanAskerChooseQuestions(askerId: String): List<ChooseQuestion>
}