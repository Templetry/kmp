// tpl:if desktop
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
// tpl:endif
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
// tpl:if web
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
// tpl:endif
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    // tpl:if desktop
    alias(libs.plugins.composeHotReload)
    // tpl:endif
    alias(libs.plugins.sqlDelight)
    alias(libs.plugins.kotlinSerialization)
}

sqldelight {
    databases {
        create("CountriesDatabase") {
            packageName.set("es.sebas1705.axiomnode")
        }
    }
}


// ── Environment profiles (ADR-0018) ──────────────────────────────────────────
// tpl:if environments
// KMP has no blessed mechanism for this: Android's buildConfigField does not
// reach iOS, Desktop or Web. So the active profile is resolved here and
// generated into commonMain as one typed object every target reads — no
// per-platform source files, and therefore nothing coupled to which targets
// happen to be switched on.
val templetryProfiles = mapOf(
    //             apiBaseUrl                      verbose  cacheSeconds
    "development" to Triple("http://10.0.2.2:8080", true, 0),
    "staging" to Triple("https://staging.example.com", true, 30),
    "production" to Triple("https://api.example.com", false, 300),
)

val appEnvironment: String =
    (project.findProperty("appEnv") as String?)
        ?: System.getenv("APP_ENV")
        ?: "development"

require(appEnvironment in templetryProfiles) {
    "Unknown appEnv \"$appEnvironment\" — want one of ${templetryProfiles.keys}"
}

val generateAppConfig = tasks.register("generateAppConfig") {
    val environment = appEnvironment
    val profile = templetryProfiles.getValue(environment)
    val outputDir = layout.buildDirectory.dir("generated/templetry/commonMain/kotlin")

    inputs.property("environment", environment)
    outputs.dir(outputDir)

    doLast {
        val packageDir = outputDir.get().asFile.resolve("es/sebas1705/axiomnode/config")
        packageDir.mkdirs()
        packageDir.resolve("AppConfig.kt").writeText(
            """
            |package es.sebas1705.axiomnode.config
            |
            |/**
            | * The active environment profile (ADR-0018).
            | *
            | * Generated at build time from the `appEnv` Gradle property, or the
            | * APP_ENV environment variable, defaulting to development:
            | *
            | *     ./gradlew assembleDebug -PappEnv=staging
            | *
            | * Do not edit by hand — it is rewritten on every build.
            | */
            |object AppConfig {
            |    const val ENVIRONMENT: String = "$environment"
            |    const val API_BASE_URL: String = "${profile.first}"
            |    const val VERBOSE_LOGGING: Boolean = ${profile.second}
            |    const val CACHE_SECONDS: Int = ${profile.third}
            |}
            |
            """.trimMargin()
        )
    }
}
// tpl:endif
kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    // tpl:if ios
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    // tpl:endif

    // tpl:if desktop
    jvm()
    // tpl:endif

    // tpl:if web
    js {
        browser()
        binaries.executable()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }
    // tpl:endif
    
    sourceSets {
        // tpl:if environments
        // The generated AppConfig lands here. Passing the task provider (not a
        // path) lets Gradle infer the dependency, so no compile task can run
        // before the file exists.
        commonMain {
            kotlin.srcDir(generateAppConfig)
        }
        // tpl:endif
        commonMain.dependencies {
            // Android dependencies:
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.kotlinx.coroutines.core)

            // Compose dependencies:
            implementation(libs.compose.runtime)
            api(libs.compose.foundation)
            api(libs.compose.animation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)

            // Ktor dependencies:
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.auth)

            // SQLDelight dependencies:
            implementation(libs.sqldelight.runtime)

            // Koin dependencies:
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            // Kamel dependency for Compose Multiplatform image loading
            implementation(libs.kamel.image)
            implementation(libs.kamel.default)

            implementation(libs.kotlinx.datetime)

        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        androidMain.dependencies {
            // Android dependencies:
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.activity.compose)

            // Ktor dependencies:
            implementation(libs.ktor.client.okhttp)

            // SQLDelight dependencies:
            implementation(libs.sqldelight.android.driver)

            // Koin dependencies:
            implementation(libs.koin.android)
        }
        // tpl:if ios
        iosMain.dependencies {
            // Ktor dependencies:
            implementation(libs.ktor.client.darwin)
            implementation(libs.sqldelight.native.driver)
        }
        // tpl:endif
        // tpl:if desktop
        jvmMain.dependencies {
            // Desktop dependencies:
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)

            // Ktor dependencies:
            implementation(libs.ktor.client.java)

            // SQLDelight dependencies:
            implementation(libs.sqldelight.jvm.driver)
        }
        // tpl:endif
        // tpl:if web
        wasmJsMain.dependencies {
            // Ktor dependencies:
            implementation(libs.ktor.client.js)

            // SQLDelight dependencies:
            implementation(libs.sqldelight.web.driver)
        }
        jsMain.dependencies {
            // Ktor dependencies:
            implementation(libs.ktor.client.js)

            // SQLDelight dependencies:
            implementation(libs.sqldelight.web.driver)

            implementation(npm("@cashapp/sqldelight-sqljs-worker", "2.0.1"))
            implementation(npm("sql.js", "1.8.0"))
        }
        // tpl:endif
    }
}

android {
    namespace = "es.sebas1705.axiomnode"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "es.sebas1705.axiomnode"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(libs.compose.uiTooling)
}

// tpl:if desktop
compose.desktop {
    application {
        mainClass = "es.sebas1705.axiomnode.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "es.sebas1705.axiomnode"
            packageVersion = "1.0.0"
        }
    }
}
// tpl:endif
