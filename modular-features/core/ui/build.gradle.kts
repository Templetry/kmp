plugins {
    alias(libs.plugins.kmp.core)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.material.icons.core)
            implementation(libs.compose.ui)
            implementation(libs.compose.animation)
            implementation(libs.compose.components.resources)
            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor)
        }
        // tpl:if desktop
        getByName("desktopMain").dependencies {
            implementation(compose.desktop.currentOs)
        }
        // tpl:endif
    }
}
