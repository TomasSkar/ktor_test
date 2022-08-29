package com.example.di

import com.example.PersonService
import com.example.PersonServiceImp
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val personsModule = module {
    singleOf(::PersonServiceImp){ bind<PersonService>() }
}