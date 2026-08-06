package dev.template.kmpbase.data.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import dev.template.kmpbase.data.local.db.AppDatabase
import java.io.File

actual class DatabaseDriverFactory {
    actual fun create(): SqlDriver {
        val dbFile = File(System.getProperty("user.home"), ".kmpbase/app.db").also { it.parentFile?.mkdirs() }
        return JdbcSqliteDriver("jdbc:sqlite:${dbFile.absolutePath}").also { AppDatabase.Schema.create(it) }
    }
}
