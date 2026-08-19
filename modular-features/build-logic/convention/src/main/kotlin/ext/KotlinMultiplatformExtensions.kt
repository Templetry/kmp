package ext

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureKmp(
    block: KotlinMultiplatformExtension.() -> Unit = {},
) {
    extensions.configure<KotlinMultiplatformExtension> {
        // ── Android ──────────────────────────────────────────────────────────
        androidTarget {
            compilerOptions { jvmTarget.set(JvmTarget.JVM_17) }
        }
        // ── Desktop (JVM) ────────────────────────────────────────────────────
        // tpl:if desktop
        jvm("desktop")
        // tpl:endif
        // ── iOS ──────────────────────────────────────────────────────────────
        // tpl:if ios
        listOf(iosX64(), iosArm64(), iosSimulatorArm64()).forEach { target ->
            target.binaries.framework {
                baseName = project.name
                isStatic = true
            }
        }
        // tpl:endif
        // ── Common compiler options ───────────────────────────────────────────
        targets.configureEach {
            compilations.configureEach {
                compileTaskProvider.configure {
                    compilerOptions {
                        freeCompilerArgs.add("-Xexpect-actual-classes")
                    }
                }
            }
        }
        block()
    }
    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions { jvmTarget.set(JvmTarget.JVM_17) }
    }
}

internal fun Project.configureAndroidLibrary(namespace: String) {
    extensions.configure<CommonExtension<*, *, *, *, *, *>>("android") {
        this.namespace = namespace
        compileSdk = 36
        defaultConfig { minSdk = 24 }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
    }
}
