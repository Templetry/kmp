package dev.template.kmpbase.data.local

import app.cash.sqldelight.db.SqlDriver
import dev.template.kmpbase.data.local.db.AppDatabase

expect class DatabaseDriverFactory {
    fun create(): SqlDriver
}

fun createDatabase(factory: DatabaseDriverFactory): AppDatabase =
    AppDatabase(factory.create())
