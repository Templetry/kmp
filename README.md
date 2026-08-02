# Templetry parent: kmp

Kotlin Multiplatform templates for [Templetry](https://github.com/Templetry). One **parent repo**, multiple **forms** — each form is a subdirectory that compiles on its own and carries its own `template.yml` ([ADR-0011](https://github.com/Templetry/wiki/blob/main/adr/0011-template-forms.md)).

| Form | What it is | Status |
|---|---|---|
| [`modular-features/`](modular-features/) | Multi-module production base — Android + Desktop + iOS, `core/data/domain/feature` layers, convention plugins | ✅ ready |
| [`single-module/`](single-module/) | Single-module starter — Android, iOS, Desktop and Web/Wasm | ✅ ready |
| `modular-ui/` | Multi-module base with a single `ui` layer instead of feature modules | 🏗️ planned |

## Usage

```sh
git clone https://github.com/Templetry/kmp
templetry render --template ./kmp/modular-features --out ./my-app \
  --set "project_name=My App" --set "base_package=com.me.myapp"
```

Forms are **chosen**, not combined. Inside a form, the manifest's features are freely combinable.

## Platform features

Platform targets are features in **both forms** — pick your platforms at render time (Android is always in):

| Feature | Form(s) | Default | What it toggles |
|---|---|---|---|
| `ios` | both | on | iOS targets + `iosMain` sources + darwin/native deps (+ convention-plugin targets in modular-features) |
| `desktop` | both | on | JVM target + desktop sources + compose.desktop packaging |
| `web` | single-module | on | JS + Wasm targets + `jsMain`/`wasmJsMain`/`webMain` sources + web drivers |

```sh
# Android-only project:
templetry render --template ./kmp/single-module --out ./my-app \
  --set "project_name=My App" --set "base_package=com.me.myapp" \
  --feature ios=false --feature desktop=false --feature web=false
```

✅ Every form and feature combo above is compile-verified in CI: the workflow renders each combo with the released `templetry` CLI and builds the output (Android/JVM/JS/Wasm on Ubuntu, iOS on macOS).
