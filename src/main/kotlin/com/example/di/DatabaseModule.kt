package com.example.di

import org.koin.dsl.module
import org.litote.kmongo.KMongo

val databaseModule = module {
    single { KMongo.createClient() }
}