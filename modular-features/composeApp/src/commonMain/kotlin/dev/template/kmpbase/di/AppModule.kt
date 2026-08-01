package dev.template.kmpbase.di

import dev.template.kmpbase.core.datastore.AppPreferences
import dev.template.kmpbase.core.datastore.createDataStore
import dev.template.kmpbase.core.network.buildHttpClient
import dev.template.kmpbase.core.security.SecureStorage
import dev.template.kmpbase.data.local.DatabaseDriverFactory
import dev.template.kmpbase.data.local.createDatabase
import dev.template.kmpbase.data.local.dao.UserLocalDao
import dev.template.kmpbase.data.network.api.UserApi
import dev.template.kmpbase.data.network.di.networkDataModule
import dev.template.kmpbase.data.repository.IUserRepository
import dev.template.kmpbase.data.repository.UserRepository
import dev.template.kmpbase.domain.usecases.user.GetMeUseCase
import dev.template.kmpbase.domain.usecases.user.UpdateProfileUseCase
import dev.template.kmpbase.feature.auth.AuthViewModel
import dev.template.kmpbase.feature.home.HomeViewModel
import dev.template.kmpbase.feature.profile.ProfileViewModel
import dev.template.kmpbase.feature.settings.SettingsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // ── Core ──────────────────────────────────────────────────────────────────
    single { SecureStorage() }
    single { AppPreferences(createDataStore()) }
    single { buildHttpClient(tokenProvider = { get<SecureStorage>().getString("auth_token").ifBlank { null } }) }

    // ── Local DB ──────────────────────────────────────────────────────────────
    single { createDatabase(get<DatabaseDriverFactory>()) }
    single { UserLocalDao(get()) }

    // ── Network ───────────────────────────────────────────────────────────────
    single { UserApi(client = get(), baseUrl = get<SecureStorage>().getString("base_url", "https://api.example.com")) }

    // ── Repository ────────────────────────────────────────────────────────────
    single<IUserRepository> { UserRepository(api = get(), dao = get()) }

    // ── Use cases ─────────────────────────────────────────────────────────────
    factory { GetMeUseCase(get()) }
    factory { UpdateProfileUseCase(get()) }

    // ── ViewModels ────────────────────────────────────────────────────────────
    viewModel { AuthViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { ProfileViewModel(get(), get()) }
    viewModel { SettingsViewModel(get(), get()) }
}
