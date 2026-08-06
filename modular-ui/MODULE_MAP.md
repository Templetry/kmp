# Module Map

## Core modules

| Module | Purpose | Key exports |
|--------|---------|-------------|
| `:core:common` | MVI base, Result, coroutine extensions | `MVIBaseViewModel`, `AppResult`, `safeApiCall` |
| `:core:ui` | Material3 theme, shared components | `AppTheme`, `LoadingScreen`, `ErrorScreen` |
| `:core:security` | Encrypted token storage | `SecureStorage` (expect/actual) |
| `:core:network` | Ktor client factory | `buildHttpClient`, `httpEngineFactory` |
| `:core:datastore` | DataStore preferences | `AppPreferences`, `createDataStore` |
| `:core:logging` | Kermit-based logging | `AppLogger` |
| `:core:testing` | Test utilities | `CoroutineTestRule` |

## Data modules

| Module | Purpose | Key exports |
|--------|---------|-------------|
| `:data:models` | Serializable domain models | `User`, `ApiResponse`, `PaginatedList` |
| `:data:local` | SQLDelight DB + DAOs | `AppDatabase`, `UserLocalDao`, `DatabaseDriverFactory` |
| `:data:network` | Ktor API clients | `UserApi` |
| `:data:repository` | Repository pattern | `IUserRepository`, `UserRepository` |

## Domain modules

| Module | Purpose | Key exports |
|--------|---------|-------------|
| `:domain:usecases` | Business rules | `GetMeUseCase`, `UpdateProfileUseCase` |

## UI module

All screens live in the single `:ui` module, one package per screen:

| Package | Screens | ViewModel |
|--------|---------|-----------|
| `ui.auth` | `LoginScreen` | `AuthViewModel` (token auth + anonymous) |
| `ui.home` | `HomeScreen` | `HomeViewModel` (loads user + list) |
| `ui.settings` | `SettingsScreen` | `SettingsViewModel` (dark mode, URL, token, logout) |
| `ui.profile` | _TODO: `ProfileScreen`_ | `ProfileViewModel` (view + edit profile) |

## App entry points

| Module | Platform | Entry |
|--------|---------|-------|
| `composeApp` | Android | `MainActivity` + `KmpApplication` |
| `composeApp` | Desktop | `main.kt` → `application { Window { App() } }` |
| `composeApp` | iOS | `MainViewController` exposed to Swift |
| `iosApp` | iOS | `ContentView.swift` wraps `MainViewController` |

## Adding a new screen

1. Create a new package `ui/src/commonMain/kotlin/.../ui/<name>/`
2. Create `*State`, `*Intent`, `*ViewModel`, `*Screen` in that package
3. Add any new dependency to `ui/build.gradle.kts`
4. Wire ViewModel in `composeApp/di/AppModule.kt`
5. Add route object + `composable<Route>` in `App.kt`
