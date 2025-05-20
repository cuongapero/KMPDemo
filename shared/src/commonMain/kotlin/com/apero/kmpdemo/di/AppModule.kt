package com.apero.kmpdemo.di

import com.apero.kmpdemo.data.local.datasource.StyleLocalDataSource
import com.apero.kmpdemo.data.local.datasource.StyleLocalDataSourceImpl
import com.apero.kmpdemo.data.remote.datasource.StyleRemoteDataSource
import com.apero.kmpdemo.data.remote.datasource.StyleRemoteDataSourceImpl
import com.apero.kmpdemo.data.repository.StyleRepositoryImpl
import com.apero.kmpdemo.domain.repository.StyleRepository
import com.apero.kmpdemo.domain.usecase.GetStyleHomeUseCase
import org.koin.dsl.module

val appModule = module {
    // Repository
    single<StyleRepository> {
        StyleRepositoryImpl(
            get(),
            get()
        )
    }

    // Data Sources
    single<StyleRemoteDataSource> { StyleRemoteDataSourceImpl(get()) }
    single<StyleLocalDataSource> { StyleLocalDataSourceImpl(get(), get()) }

    // Use Cases
    factory { GetStyleHomeUseCase(get()) }
} 