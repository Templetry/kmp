package dev.template.kmpbase

import android.app.Application
import dev.template.kmpbase.core.security.appContext
import dev.template.kmpbase.data.local.DatabaseDriverFactory
import dev.template.kmpbase.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class KmpApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = this

        startKoin {
            androidContext(this@KmpApplication)
            modules(
                appModule,
                module { single { DatabaseDriverFactory(this@KmpApplication) } }
            )
        }
    }
}
