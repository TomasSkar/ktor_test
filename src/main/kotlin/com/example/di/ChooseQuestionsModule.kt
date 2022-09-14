package com.example.di

import com.example.services.ChooseQuestionService
import com.example.services.ChooseQuestionServiceImpl
import com.example.services.PersonService
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val chooseQuestionsModule = module {
    singleOf(::ChooseQuestionServiceImpl){ bind<ChooseQuestionService>() }
}