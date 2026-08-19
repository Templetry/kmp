plugins {
    `kotlin-dsl`
}

group = "dev.template.kmpbase.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.plugins.kotlin.multiplatform.toDep())
    compileOnly(libs.plugins.kotlin.serialization.toDep())
    compileOnly(libs.plugins.compose.multiplatform.toDep())
    compileOnly(libs.plugins.compose.compiler.toDep())
    compileOnly(libs.plugins.android.application.toDep())
    compileOnly(libs.plugins.android.library.toDep())
    compileOnly(libs.plugins.sqldelight.toDep())
    compileOnly(libs.plugins.detekt.toDep())
}

fun Provider<PluginDependency>.toDep() = map {
    "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}"
}

gradlePlugin {
    plugins {
        register("kmpLibrary") {
            id = "kmpbase.kmp.library"
            implementationClass = "KmpLibraryConventionPlugin"
        }
        register("kmpFeature") {
            id = "kmpbase.kmp.feature"
            implementationClass = "KmpFeatureConventionPlugin"
        }
        register("kmpData") {
            id = "kmpbase.kmp.data"
            implementationClass = "KmpDataConventionPlugin"
        }
        register("kmpDomain") {
            id = "kmpbase.kmp.domain"
            implementationClass = "KmpDomainConventionPlugin"
        }
        register("kmpCore") {
            id = "kmpbase.kmp.core"
            implementationClass = "KmpCoreConventionPlugin"
        }
        register("kmpApp") {
            id = "kmpbase.kmp.app"
            implementationClass = "KmpAppConventionPlugin"
        }
        register("kmpbaseDetekt") {
            id = "kmpbase.detekt"
            implementationClass = "DetektConventionPlugin"
        }
    }
}
