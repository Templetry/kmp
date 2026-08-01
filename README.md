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

Forms are **chosen**, not combined. Inside a form, the manifest's features are freely combinable — platform targets and capabilities land as features next (see the [catalog](https://github.com/Templetry/catalog)).
