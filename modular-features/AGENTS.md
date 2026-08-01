# Agent Operating Contract

This file defines rules for AI agents editing this codebase.

## Must-read before any change

1. `ARCHITECTURE.md` — layers, patterns, what depends on what
2. `MODULE_MAP.md` — which module exports which class
3. `docs/ai/ARCHITECTURE_GUARDRAILS.md` — what is forbidden

## Non-negotiable rules

- **Never put suspend calls inside `updateState { }` lambda** — it is a non-suspend `(S) -> S`
- **Never skip `execute { }` wrapper** — all coroutine work in ViewModels goes through it
- **Never import across forbidden layer boundaries** — feature cannot import feature; domain cannot import data:network
- **Always declare `:core:security` explicitly** in any module that transitively uses `SecureStorage`
- **Always use convention plugins** — do not copy-paste `kotlin.multiplatform { }` blocks
- **Detekt must pass** — run `./gradlew detekt` before marking task done

## Adding a feature

Follow the 5-step recipe in `MODULE_MAP.md#adding-a-new-feature-module`.

## Changing the database schema

1. Edit the `.sq` file in `data/local/src/commonMain/sqldelight/`
2. Run `./gradlew generateCommonMainAppDatabaseInterface` to regenerate
3. Update the DAO and repository accordingly

## Environment

- Kotlin 2.1.0 / Compose Multiplatform 1.7.3
- Targets: `androidTarget` (minSdk 24), `jvm("desktop")`, `iosX64 + iosArm64 + iosSimulatorArm64`
- DI: Koin 4.x (no Hilt — incompatible with KMP)
- DB: SQLDelight 2.x (no Room — not yet fully KMP)
