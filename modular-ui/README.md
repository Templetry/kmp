# KMPNativeBase

> Kotlin Multiplatform project template — **Android + Desktop (JVM) + iOS** — with modular clean architecture, convention plugins, CI, and AI context docs.

Inspired by [AndroidNativeBase](https://github.com/Sebas1705/AndroidNativeBase) but targeting all three platforms.

## Platforms

| Platform | Target | Entry point |
|----------|--------|-------------|
| Android  | KMP `android` target (minSdk 24) + `androidApp` application | `androidApp/src/main/…/MainActivity.kt` |
| Desktop  | `jvm("desktop")` | `composeApp/src/desktopMain/…/main.kt` |
| iOS      | `iosArm64 / iosSimulatorArm64` | `iosApp/iosApp/iOSApp.swift` |

## Quick start

```bash
# Clone
git clone https://github.com/YOUR_USER/KMPNativeBase.git
cd KMPNativeBase

# Rename package (optional)
# Replace all occurrences of "dev.template.kmpbase" with your package

# Compile Desktop
./gradlew :composeApp:compileKotlinDesktop

# Run Desktop
./gradlew :composeApp:run

# Assemble Android APK
./gradlew :androidApp:assembleDebug

# Build iOS framework (macOS only)
./gradlew :composeApp:linkDebugFrameworkIosSimulatorArm64
# Then open iosApp/ in Xcode → Product → Run
```

## Module structure

```
KMPNativeBase/
├── build-logic/          # Convention Gradle plugins
│   └── convention/
│       ├── KmpLibraryConventionPlugin.kt
│       ├── KmpFeatureConventionPlugin.kt
│       ├── KmpDataConventionPlugin.kt
│       ├── KmpDomainConventionPlugin.kt
│       ├── KmpCoreConventionPlugin.kt
│       ├── KmpAppConventionPlugin.kt
│       └── DetektConventionPlugin.kt
├── core/
│   ├── common/     # MVI base, Result, extensions
│   ├── ui/         # Material3 theme + shared components
│   ├── security/   # SecureStorage (expect/actual)
│   ├── network/    # Ktor client factory (expect/actual engine)
│   ├── datastore/  # DataStore preferences (expect/actual)
│   ├── logging/    # Kermit-based AppLogger
│   └── testing/    # CoroutineTestRule, test utilities
├── data/
│   ├── models/     # Serializable domain models
│   ├── local/      # SQLDelight DB + DAOs
│   ├── network/    # Ktor API clients
│   └── repository/ # Repository interfaces + implementations
├── domain/
│   └── usecases/   # Use case base class + user use cases
├── ui/             # Single UI module, one package per screen
│   └── src/.../ui/
│       ├── auth/       # Login screen + AuthViewModel
│       ├── home/       # Home screen + HomeViewModel
│       ├── settings/   # Settings screen + SettingsViewModel
│       └── profile/    # Profile screen + ProfileViewModel
├── androidApp/     # Android application (manifest, launcher resources, MainActivity)
├── composeApp/     # Shared app (KMP library) + Desktop and iOS entry points
└── iosApp/         # Swift wrapper (Xcode project)
```

## Library stack

| Category | Library | Version |
|----------|---------|---------|
| Build | Kotlin / Android Gradle Plugin / Gradle | 2.4.21 / 9.4.1 / 9.8.1 |
| UI | Compose Multiplatform | 1.12.2 |
| DI | Koin | 4.2.2 |
| Networking | Ktor | 3.6.0 |
| Local DB | SQLDelight | 2.4.1 |
| Preferences | DataStore / Multiplatform-Settings | 1.2.1 / 1.3.0 |
| Image loading | Coil3 | 3.6.3 |
| Logging | Kermit | 2.2.0 |
| Navigation | androidx.navigation compose | 2.9.2 |
| Lifecycle | androidx.lifecycle | 2.11.0 |
| Serialization | kotlinx.serialization | 1.11.0 |
| DateTime | kotlinx.datetime | 0.8.0 |
| Immutable | kotlinx.collections.immutable | 0.5.2 |
| Static analysis | Detekt | 1.23.8 |

## Quality commands

```bash
./gradlew detekt                    # Static analysis (all modules)
./gradlew allTests                  # Run all tests
./gradlew coverageUnitTestAll       # Aggregate test + coverage report
./gradlew lintAll                   # Android lint
./gradlew dependencyUpdates         # Check for dependency updates
```

## CI/CD

Workflows in `.github/workflows/`:

| Workflow | Trigger | What it does |
|---------|---------|-------------|
| `validate.yml` | push / PR | Detekt, compile desktop + android + iOS, unit tests, APK |
| `release.yml` | `v*.*.*` tag | Android APK + Desktop binaries on all 3 OSes |
| `security.yml` | push main / weekly | Dependency review + CodeQL |
| `changelog-label-gate.yml` | PR | Requires a changelog label |

## Customising

1. **Rename package**: replace `dev.template.kmpbase` with your reverse domain
2. **Set app name**: `composeApp/build.gradle.kts` → `packageName`, `applicationId`
3. **Set API URL**: `data/network/di/NetworkDataModule.kt` → `BASE_URL`
4. **Add a feature**: follow recipe in `docs/ai/PATTERN_RECIPES.md`

## AI context docs

- Architecture map: `ARCHITECTURE.md`
- Module reference: `MODULE_MAP.md`
- Agent rules: `AGENTS.md`
- Quick lookup: `AI_INDEX.md`
- Guardrails: `docs/ai/ARCHITECTURE_GUARDRAILS.md`
- Code recipes: `docs/ai/PATTERN_RECIPES.md`

## iOS setup

The `iosApp/` directory contains the Swift entry point. To build:

1. Ensure Xcode 15+ and an Apple Developer account are configured
2. Run `./gradlew :composeApp:generateDummyFramework` (or the link task) to produce the KMP framework
3. Open `iosApp/iosApp.xcodeproj` in Xcode → Product → Run
   > Note: the Xcode project file is not included; create it with **File → New → Project** (iOS App, SwiftUI), add a local Swift Package for the KMP framework, or use the KMP wizard in Android Studio / Fleet.

## License

Apache 2.0
