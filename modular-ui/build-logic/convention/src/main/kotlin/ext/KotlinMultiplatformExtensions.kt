package ext

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal const val COMPILE_SDK = 37
internal const val TARGET_SDK = 36
internal const val MIN_SDK = 24

/**
 * The targets every KMP module shares, apart from Android, plus the common compiler flags.
 * Android is configured by [configureAndroidLibrary] (`com.android.kotlin.multiplatform.library`):
 * since AGP 9 every KMP module is an Android library, and the application is the pure-Android
 * `androidApp` module ([configureAndroidApp]).
 */
internal fun Project.configureKmp(
    block: KotlinMultiplatformExtension.() -> Unit = {},
) {
    extensions.configure<KotlinMultiplatformExtension> {
        // ── Desktop (JVM) ────────────────────────────────────────────────────
        // tpl:if desktop
        jvm("desktop") {
            compilerOptions { jvmTarget.set(JvmTarget.JVM_17) }
        }
        // tpl:endif
        // ── iOS ──────────────────────────────────────────────────────────────
        // tpl:if ios
        listOf(iosArm64(), iosSimulatorArm64()).forEach { target ->
            target.binaries.framework {
                baseName = project.name
                isStatic = true
            }
        }
        // tpl:endif
        // ── Common compiler options ───────────────────────────────────────────
        compilerOptions {
            freeCompilerArgs.add("-Xexpect-actual-classes")
        }
        block()
    }
}

/**
 * Every KMP module: the Android Gradle library plugin for KMP. It has no build types;
 * JVM tests of Android code live in `src/androidHostTest` and run with `testAndroidHostTest`.
 */
internal fun Project.configureAndroidLibrary(namespace: String) {
    extensions.configure<KotlinMultiplatformExtension> {
        (this as ExtensionAware).extensions.configure<KotlinMultiplatformAndroidLibraryTarget>("android") {
            this.namespace = namespace
            compileSdk = COMPILE_SDK
            minSdk = MIN_SDK
            compilerOptions { jvmTarget.set(JvmTarget.JVM_17) }
            // Off by default in this plugin. Modules with src/androidMain/res need it, and so do modules
            // with Compose resources: Compose Multiplatform ships a library's resources as Android assets,
            // and the plugin only packages assets when Android resources are enabled. Without it the APK
            // still builds, and then misses every string and drawable of that module.
            androidResources {
                enable = file("src/androidMain/res").isDirectory ||
                    file("src/commonMain/composeResources").isDirectory
            }
            withHostTest {}
        }
    }
}

/**
 * androidApp: `com.android.application` with AGP 9's built-in Kotlin and new DSL, so no Kotlin plugin
 * is applied and the Kotlin jvmTarget follows `compileOptions.targetCompatibility`.
 */
internal fun Project.configureAndroidApp(namespace: String, applicationId: String) {
    extensions.configure<ApplicationExtension> {
        this.namespace = namespace
        compileSdk = COMPILE_SDK
        defaultConfig {
            this.applicationId = applicationId
            minSdk = MIN_SDK
            targetSdk = TARGET_SDK
            versionCode = 1
            versionName = "1.0.0"
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
        buildTypes {
            debug { isDebuggable = true }
            release {
                isMinifyEnabled = true
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro",
                )
            }
        }
    }
}
