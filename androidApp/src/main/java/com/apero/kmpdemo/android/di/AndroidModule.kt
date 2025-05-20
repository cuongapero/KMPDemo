package com.apero.kmpdemo.android.di

import android.app.Activity
import androidx.activity.ComponentActivity
import org.koin.dsl.module

val androidModule = module {
    single<Activity> { get<ComponentActivity>() }
} 