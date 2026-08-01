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

## Features of `single-module`

Platform targets are features — pick your platforms at render time (Android is always in):

| Feature | Default | What it toggles |
|---|---|---|
| `ios` | on | iOS targets + `iosMain` sources + darwin/native deps |
| `desktop` | on | JVM target + `jvmMain` sources + compose.desktop packaging + hot reload |
| `web` | on | JS + Wasm targets + `jsMain`/`wasmJsMain`/`webMain` sources + web drivers |

```sh
# Android-only project:
templetry render --template ./kmp/single-module --out ./my-app \
  --set "project_name=My App" --set "base_package=com.me.myapp" \
  --feature ios=false --feature desktop=false --feature web=false
```

`modular-features` gets its target features next. ⚠️ Compile verification of feature combos in CI is pending — treat non-default combos as beta until the parent CI lands.
