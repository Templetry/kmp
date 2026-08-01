package dev.template.kmpbase

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import dev.template.kmpbase.data.local.DatabaseDriverFactory
import dev.template.kmpbase.di.appModule
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun main() {
    startKoin {
        modules(
            appModule,
            module { single { DatabaseDriverFactory() } }
        )
    }

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title          = "KMP Native Base",
            state          = rememberWindowState(width = 1200.dp, height = 800.dp),
        ) {
            App()
        }
    }
}
