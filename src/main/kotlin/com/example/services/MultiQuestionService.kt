package com.example.services

import com.example.models.MultiQuestion

interface MultiQuestionService {
    fun getAllMultiQuestions(): List<MultiQuestion>
    fun getAskerMultiQuestions(askerId: String): List<MultiQuestion>
    fun getOtherThanAskerMultiQuestions(askerId: String): List<MultiQuestion>
}