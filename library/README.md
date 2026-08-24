# KMPLibrary

A Kotlin Multiplatform **library** — no UI, no application entry point. It is built to be depended on.

Targets: **JVM** always, **Android** and **iOS** optional. Published to Maven, with the iOS side available as a static framework.

## Why a library rather than a module inside the app

Because more than one thing consumes it. The moment a second surface exists — a server, a second app, a desktop console — the types and the rules they share have to live somewhere neither of them owns. Otherwise the same invariant gets written twice, and the two copies stop agreeing on a Tuesday nobody remembers.

That also sets the bar for what goes in: **if only one consumer needs it, it does not belong here.**

## Layout

```
src/commonMain     the library. Almost everything lives here
src/commonTest     tests that run on every target, not just the JVM
src/jvmMain        expect/actual for the JVM
src/androidMain    expect/actual for Android
src/iosMain        expect/actual for iOS
```

## Build

```sh
./gradlew build
```

## Profiles

Per ADR-0018, three profiles: `development`, `staging`, `production`.

A library has no runtime environment of its own — the app embedding it does. What it does have is per-environment defaults its consumers would otherwise each redeclare. Selected at build time:

```sh
./gradlew build -PlibEnv=staging
```

That generates `BuildProfile.kt`, read through `Profile.current`. The file is generated on every build and belongs in neither the repository nor a hand edit.

## Publishing

The destination is never hardcoded — a template naming somebody's repository is a template nobody else can use. Supply it at publish time:

```sh
LIB_PACKAGES_URL=https://... LIB_PACKAGES_USER=... LIB_PACKAGES_TOKEN=... \
  ./gradlew publishAllPublicationsToPackagesRepository
```

Coordinates come from `lib.group` and `lib.version` in `gradle.properties`, overridable with `-P`.
