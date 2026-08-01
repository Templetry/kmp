package dev.template.kmpbase.data.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import dev.template.kmpbase.data.local.db.AppDatabase

actual class DatabaseDriverFactory {
    actual fun create(): SqlDriver = NativeSqliteDriver(AppDatabase.Schema, "app.db")
}
