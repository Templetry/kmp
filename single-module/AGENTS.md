# AGENTS

Operating contract for AI agents and automation helpers working in this project.

## Mission

- One Compose Multiplatform codebase serving Android, iOS, Desktop and Web. Shared code is the default; a platform-specific file is a decision that needs a reason.

## Core Rules

- Everything lives in `composeApp/src/commonMain` unless the platform genuinely differs. Reach for `expect`/`actual` only when the API does not exist in common.
- The targets are feature-gated: iOS, Desktop and Web can each be absent. Never assume a source set exists — check `build.gradle.kts` before adding to it.
- Compose UI stays declarative and stateless where it can; hoist state rather than holding it in composables.
- Dependencies are declared in `gradle/libs.versions.toml`, never inline in a build file.
- Update docs in the same change when behaviour or process changes.

## Safe Change Workflow

1. Read the affected files fully before editing.
2. Make the smallest change that solves the task.
3. Build, then review the diff with git before committing.

## Required checks before finishing

```sh templetry:checks
./gradlew build
```

## This project came from a template

Four facts you cannot infer from the code in front of you:

- **Never hand-edit `.templetry-answers.yml`.** It records what generated this project. Editing it makes the next update merge against a state that never existed.
- **Before writing a capability by hand, run `templetry pieces`.** Auth, RBAC, audit trails, API keys and whole CRUD resources may already exist as pieces for this template. Adopting one is `templetry add <name>`, and it brings its own tests.
- **`templetry update` pulls improvements from the template** through a three-way merge that keeps your edits. Use it instead of copying files from the template by hand.
- **Directives like `tpl:if` belong to the template, not here.** If you find one in this project, it is a rendering bug worth reporting — do not try to interpret it.
