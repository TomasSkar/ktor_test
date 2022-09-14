package com.example.plugins

import com.example.di.*
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
fun Application.setupKoin() {
    install(Koin){
        modules(
            databaseModule,
            personsModule,
            chooseQuestionsModule,
            multiQuestionsModule,
            questionsModule
        )
    }
}