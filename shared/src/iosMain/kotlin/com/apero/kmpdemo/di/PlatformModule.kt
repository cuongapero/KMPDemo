package com.apero.kmpdemo.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.apero.kmpdemo.data.local.model.AppDatabase
import org.koin.dsl.module

actual fun platformModule() = module {
    single<SqlDriver> {
        NativeSqliteDriver(
            schema = AppDatabase.Schema,
            name = "app.db"
        )
    }
} 