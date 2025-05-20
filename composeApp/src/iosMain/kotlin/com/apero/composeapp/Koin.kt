package com.apero.composeapp

import com.apero.composeapp.di.presentationModule
import com.apero.kmpdemo.di.appModule
import com.apero.kmpdemo.di.databaseModule
import com.apero.kmpdemo.di.networkModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            appModule,
            networkModule,
            databaseModule,
            presentationModule,
        )
    }

// Called by iOS
fun doInitKoinIos() = initKoin {}