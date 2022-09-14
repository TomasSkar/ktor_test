package com.example.di

import com.example.data.QuestionsRepository
import com.example.data.QuestionsRepositoryImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val questionsModule = module {
    singleOf(::QuestionsRepositoryImpl){ bind<QuestionsRepository>()}
}