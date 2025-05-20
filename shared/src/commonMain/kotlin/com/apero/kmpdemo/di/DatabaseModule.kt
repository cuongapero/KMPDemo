package com.apero.kmpdemo.di

import com.apero.kmpdemo.data.local.model.AppDatabase
import org.koin.dsl.module

expect fun platformModule(): org.koin.core.module.Module

val databaseModule = module {
    includes(platformModule())
    single { AppDatabase(get()) }
    single { get<AppDatabase>().categoryEntityQueries }
    single { get<AppDatabase>().styleEntityQueries }
} 