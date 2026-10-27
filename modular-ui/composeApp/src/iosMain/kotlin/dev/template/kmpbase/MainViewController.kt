package dev.template.kmpbase

import androidx.compose.ui.window.ComposeUIViewController
import dev.template.kmpbase.core.security.IosSecureStorage
import dev.template.kmpbase.core.security.SecureStorage
import dev.template.kmpbase.data.local.DatabaseDriverFactory
import dev.template.kmpbase.di.appModule
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun MainViewController() = ComposeUIViewController(
    configure = {
        startKoin {
            modules(
                appModule,
                module {
                    single { DatabaseDriverFactory() }
                    single<SecureStorage> { IosSecureStorage() }
                },
            )
        }
    }
) { App() }
