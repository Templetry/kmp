# Changelog

## [Unreleased]

### Changed
- Android Gradle Plugin 9.4.1 and Gradle 9.8.1 (from 8.7.3 / 8.10.2), Kotlin 2.4.21, Compose Multiplatform 1.12.2
  and the libraries that go with them; compileSdk 37, targetSdk 36
- New `androidApp` module (convention `kmpbase.android.app`): the Android application with AGP 9 built-in Kotlin.
  `composeApp` is now a KMP library; the Android build is `./gradlew :androidApp:assembleDebug`
- Every KMP module uses `com.android.kotlin.multiplatform.library` instead of `com.android.library`; JVM tests of
  Android code go in `src/androidHostTest` (`testAndroidHostTest`)
- `iosX64` target removed (Compose Multiplatform no longer publishes it)
- `SecureStorage` is an interface with `AndroidSecureStorage` / `DesktopSecureStorage` / `IosSecureStorage`,
  bound in Koin by each platform entry point instead of an `expect class` built in common code

### Fixed
- `./gradlew build` compiles again: common code no longer calls the `expect class SecureStorage` constructor
  (`compileCommonMainKotlinMetadata` failed), and `:core:testing` declares `kotlinx-coroutines-test`

### Added
- Initial KMPNativeBase template
- Android + Desktop + iOS targets
- Clean architecture with 14 modules
- Koin 4.x DI
- Ktor 3.x networking (expect/actual engine)
- SQLDelight 2.x local database
- DataStore preferences (expect/actual)
- SecureStorage expect/actual (Android EncryptedSharedPrefs / Desktop Preferences / iOS NSUserDefaults)
- MVI base classes (`MVIBaseViewModel`, `MVIBaseState`, `MVIBaseIntent`)
- Type-safe navigation (navigation-compose 2.9.1)
- Compose Multiplatform 1.7.3 theme
- Coil3 image loading
- Kermit logging
- Convention Gradle plugins (7 plugins in build-logic)
- Detekt static analysis with KMP-aware config
- GitHub Actions: validate, release (Android + Desktop 3-OS matrix), security (CodeQL), changelog gate
- AI context docs (AGENTS.md, ARCHITECTURE.md, MODULE_MAP.md, AI_INDEX.md)
