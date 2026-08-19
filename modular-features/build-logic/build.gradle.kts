plugins {
    `kotlin-dsl`
}

group = "dev.template.kmpbase.buildlogic"

dependencies {
    compileOnly(libs.plugins.kotlin.multiplatform.toDep())
    compileOnly(libs.plugins.compose.multiplatform.toDep())
    compileOnly(libs.plugins.compose.compiler.toDep())
    compileOnly(libs.plugins.android.application.toDep())
    compileOnly(libs.plugins.android.library.toDep())
    compileOnly(libs.plugins.detekt.toDep())
}

fun Provider<PluginDependency>.toDep() = map {
    "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}"
}

// The kotlin-dsl plugin pins the language version to 1.9 so plugins stay
// consumable by older Gradle. Nothing this module depends on ships metadata
// that old any more, so it is raised to what Gradle 9 embeds. The ceiling is
// real: a plugin compiled with a newer Kotlin than this cannot be read at
// all, which is why sqldelight is held back in libs.versions.toml.
kotlin {
    compilerOptions {
        apiVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_2)
        languageVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_2)
    }
}
