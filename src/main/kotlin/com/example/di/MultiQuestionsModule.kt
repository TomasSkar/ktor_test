package com.example.di

import com.example.services.MultiQuestionService
import com.example.services.MultiQuestionServiceImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val multiQuestionsModule = module {
    singleOf(::MultiQuestionServiceImpl){ bind<MultiQuestionService>()}
}