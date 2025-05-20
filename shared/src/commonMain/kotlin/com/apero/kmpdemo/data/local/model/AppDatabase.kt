package com.apero.kmpdemo.data.local.model

import app.cash.sqldelight.db.AfterVersion
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema

class AppDatabase(driver: SqlDriver) {
    companion object {
        val Schema = object : SqlSchema<QueryResult.Value<Unit>> {
            override val version: Long = 1

            override fun create(driver: SqlDriver): QueryResult.Value<Unit> {
                driver.execute(null, """
                    CREATE TABLE IF NOT EXISTS StyleEntity (
                        id TEXT NOT NULL PRIMARY KEY,
                        name TEXT NOT NULL,
                        description TEXT NOT NULL,
                        categoryId TEXT NOT NULL,
                        thumbnail TEXT NOT NULL
                    );
                """.trimIndent(), 0)
                
                driver.execute(null, """
                    CREATE TABLE IF NOT EXISTS CategoryEntity (
                        id TEXT NOT NULL PRIMARY KEY,
                        name TEXT NOT NULL,
                        description TEXT NOT NULL,
                        banner_url TEXT NOT NULL,
                        thumbnail_url TEXT NOT NULL
                    );
                """.trimIndent(), 0)

                return QueryResult.Value(Unit)
            }

            override fun migrate(
                driver: SqlDriver,
                oldVersion: Long,
                newVersion: Long,
                vararg callbacks: AfterVersion
            ): QueryResult.Value<Unit> {
                // Add migration logic here when needed
                return QueryResult.Value(Unit)
            }
        }
    }

    private val database = com.apero.kmpdemo.db.AppDatabase(driver)
    val styleEntityQueries get() = database.styleEntityQueries
    val categoryEntityQueries get() = database.categoryEntityQueries
}