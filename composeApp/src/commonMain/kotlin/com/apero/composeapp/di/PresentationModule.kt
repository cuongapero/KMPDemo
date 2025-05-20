package com.apero.composeapp.di

import com.apero.composeapp.presentation.home.HomeViewModel
import org.koin.dsl.module

val presentationModule = module {
    factory { HomeViewModel() }
}
