import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    // tpl:if serialization
    alias(libs.plugins.kotlin.serialization)
    // tpl:endif
    // tpl:if android
    alias(libs.plugins.android.library)
    // tpl:endif
    `maven-publish`
}

group = providers.gradleProperty("lib.group").getOrElse("dev.template.kmplib")
version = providers.gradleProperty("lib.version").getOrElse("0.1.0-SNAPSHOT")

// tpl:if environments
// ADR-0018. A library has no runtime environment of its own, but it very often
// carries per-environment defaults — a base URL, a log level, a feature gate —
// and baking those in at build time is how a consumer avoids redeclaring them
// on every platform. Selected with -PlibEnv=staging.
val libEnv: String = providers.gradleProperty("libEnv").getOrElse("development")
val generatedConfigDir = layout.buildDirectory.dir("generated/templetry/commonMain/kotlin")

val generateLibraryConfig by tasks.registering {
    val target = generatedConfigDir
    val env = libEnv
    inputs.property("libEnv", env)
    outputs.dir(target)
    doLast {
        // Derived from the package rather than written as a path, so identity
        // replacement rewrites both together when the template is rendered.
        val pkg = "dev.template.kmplib.config"
        val pkgDir = target.get().asFile.resolve(pkg.replace('.', '/'))
        pkgDir.mkdirs()
        pkgDir.resolve("BuildProfile.kt").writeText(
            """
            package $pkg

            /** Generated at build time from -PlibEnv. Do not edit. */
            public object BuildProfile {
                public const val ENVIRONMENT: String = "$env"
            }
            """.trimIndent() + "\n"
        )
    }
}
// tpl:endif

kotlin {
    jvmToolchain(17)

    // tpl:if android
    androidTarget {
        publishLibraryVariants("release")
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions { jvmTarget.set(JvmTarget.JVM_17) }
    }
    // tpl:endif

    // Always present: it is the target a library is tested on, and the one a
    // server consumer uses.
    jvm()

    // tpl:if ios
    listOf(iosX64(), iosArm64(), iosSimulatorArm64()).forEach { target ->
        target.binaries.framework {
            baseName = "kmplib"
            isStatic = true
        }
    }
    // tpl:endif

    sourceSets {
        commonMain {
            // tpl:if environments
            kotlin.srcDir(generatedConfigDir)
            // tpl:endif
            dependencies {
                api(libs.kotlinx.coroutines.core)
                // tpl:if serialization
                api(libs.kotlinx.serialization.json)
                // tpl:endif
            }
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

// tpl:if environments
// Every Kotlin compilation waits for the generated profile, not just the common
// one: a target compiling before it exists fails with an unresolved reference
// that points at the wrong thing entirely.
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask<*>>().configureEach {
    dependsOn(generateLibraryConfig)
}
// tpl:endif

// tpl:if android
android {
    namespace = "dev.template.kmplib"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig { minSdk = libs.versions.android.minSdk.get().toInt() }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
// tpl:endif

// Publishing is the point of a library, but the destination is never hardcoded:
// a template that names somebody's repository is a template nobody else can use.
publishing {
    repositories {
        val packagesUrl = providers.gradleProperty("lib.packages.url")
            .orElse(providers.environmentVariable("LIB_PACKAGES_URL"))
        if (packagesUrl.isPresent) {
            maven {
                name = "packages"
                url = uri(packagesUrl.get())
                credentials {
                    username = providers.environmentVariable("LIB_PACKAGES_USER").getOrElse("")
                    password = providers.environmentVariable("LIB_PACKAGES_TOKEN").getOrElse("")
                }
            }
        }
    }
}
