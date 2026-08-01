package dev.template.kmpbase.data.local

import android.content.Context
import app.cash.sqldelight.android.driver.AndroidSqliteDriver
import app.cash.sqldelight.db.SqlDriver
import dev.template.kmpbase.data.local.db.AppDatabase

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun create(): SqlDriver = AndroidSqliteDriver(AppDatabase.Schema, context, "app.db")
}
