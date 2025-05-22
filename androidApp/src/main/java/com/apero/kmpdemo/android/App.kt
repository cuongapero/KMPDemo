package com.apero.kmpdemo.android

import android.app.Application
import com.apero.composeapp.di.imagePickerModule
import com.apero.composeapp.di.presentationModule
import com.apero.kmpdemo.android.di.androidModule
import com.apero.kmpdemo.di.appModule
import com.apero.kmpdemo.di.databaseModule
import com.apero.kmpdemo.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            modules(
                appModule,
                networkModule,
                databaseModule,
                presentationModule,
                androidModule,
                imagePickerModule
            )
        }
    }
}