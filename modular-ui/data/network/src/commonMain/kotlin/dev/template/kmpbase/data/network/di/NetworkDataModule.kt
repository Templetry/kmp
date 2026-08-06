package dev.template.kmpbase.data.network.di

import dev.template.kmpbase.data.network.api.UserApi
import org.koin.dsl.module

val networkDataModule = module {
    single { UserApi(client = get(), baseUrl = getProperty("BASE_URL", "https://api.example.com")) }
}
