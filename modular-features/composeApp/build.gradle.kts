// tpl:if desktop
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
// tpl:endif

plugins {
    alias(libs.plugins.kmp.app)
}

kotlin {
    sourceSets {
        // tpl:if desktop
        val desktopMain by getting
        // tpl:endif

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.animation)
            implementation(compose.components.resources)
            implementation(libs.lifecycle.viewmodel)
            implementation(libs.lifecycle.viewmodel.compose)
            implementation(libs.lifecycle.runtime.compose)
            implementation(libs.navigation.compose)
            implementation(libs.kotlinx.serialization.json)

            // Koin
            implementation(libs.koin.core)
            implementation(libs.bundles.koin.compose.bundle)

            // Core
            implementation(project(":core:common"))
            implementation(project(":core:ui"))
            implementation(project(":core:security"))
            implementation(project(":core:network"))
            implementation(project(":core:datastore"))
            implementation(project(":core:logging"))

            // Data
            implementation(project(":data:models"))
            implementation(project(":data:local"))
            implementation(project(":data:network"))
            implementation(project(":data:repository"))

            // Domain
            implementation(project(":domain:usecases"))

            // Features
            implementation(project(":feature:auth"))
            implementation(project(":feature:home"))
            implementation(project(":feature:settings"))
            implementation(project(":feature:profile"))
        }

        androidMain.dependencies {
            implementation(libs.kotlinx.coroutines.android)
            implementation(libs.koin.android)
        }

        // tpl:if desktop
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
            implementation(libs.ktor.client.java)
        }
        // tpl:endif
    }
}

dependencies {
    debugImplementation(libs.leakcanary)
}

// tpl:if desktop
compose.desktop {
    application {
        mainClass = "dev.template.kmpbase.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName    = "KMPNativeBase"
            packageVersion = "1.0.0"
            description    = "KMP Multiplatform template"
            vendor         = "Your Name"
        }
    }
}
// tpl:endif
