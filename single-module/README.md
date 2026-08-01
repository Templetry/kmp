# Multiplatform Mod App

Kotlin Multiplatform application targeting **Android, iOS, Web, and Desktop (JVM)** using **Compose Multiplatform** for shared UI across all platforms.

This repository tracks the migration journey of the app through two iterations:

- `AndroidModApp (primera migro)` — first migration attempt
- `App/` — current KMP implementation with full multiplatform support

---

## Platforms

| Platform | Status |
|---|---|
| Android | ✅ |
| iOS | ✅ |
| Desktop (JVM) | ✅ |
| Web (Wasm) | ✅ |
| Web (JS) | ✅ |

---

## Tech Stack

- **Kotlin Multiplatform** — shared business logic across all targets
- **Compose Multiplatform** — shared UI (Android, Desktop, Web); SwiftUI entry point for iOS
- **Gradle** with version catalogs

---

## Getting Started

### Prerequisites
- Android Studio with KMP plugin, or IntelliJ IDEA
- Xcode (for iOS builds)
- JDK 17+

### Clone

```bash
git clone https://github.com/Sebas1705/MultiplatformModApp.git
cd MultiplatformModApp/App
```

### Build & Run

**Android:**
```bash
.\gradlew.bat :composeApp:assembleDebug   # Windows
./gradlew :composeApp:assembleDebug       # macOS/Linux
```

**Desktop (JVM):**
```bash
.\gradlew.bat :composeApp:run
```

**Web (Wasm — modern browsers):**
```bash
.\gradlew.bat :composeApp:wasmJsBrowserDevelopmentRun
```

**Web (JS — legacy browsers):**
```bash
.\gradlew.bat :composeApp:jsBrowserDevelopmentRun
```

**iOS:** Open `/App/iosApp` in Xcode and run.

---

## Project Structure

```
App/
├── composeApp/
│   └── src/
│       ├── commonMain/     ← shared UI and logic (all platforms)
│       ├── androidMain/    ← Android-specific code
│       ├── iosMain/        ← iOS-specific code
│       ├── jvmMain/        ← Desktop-specific code
│       └── wasmJsMain/     ← Web-specific code
└── iosApp/                 ← Xcode project entry point
```

---

## License

MIT
