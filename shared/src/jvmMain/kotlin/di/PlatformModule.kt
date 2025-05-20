package di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import data.local.model.AppDatabase
import org.koin.dsl.module
import java.io.File

actual fun platformModule() = module {
    single<SqlDriver> {
        val databasePath = File(System.getProperty("user.home"), "app.db")
        val driver = JdbcSqliteDriver(url = "jdbc:sqlite:${databasePath.absolutePath}")
        AppDatabase.Schema.create(driver)
        driver
    }
} 