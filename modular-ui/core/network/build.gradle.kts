plugins { alias(libs.plugins.kmp.core) }

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.bundles.ktor.common)
            api(libs.ktor.client.core)
            implementation(project(":core:logging"))
            api(libs.kotlinx.serialization.json)
        }
        androidMain.dependencies { implementation(libs.ktor.client.okhttp) }
        // tpl:if desktop
        getByName("desktopMain").dependencies { implementation(libs.ktor.client.java) }
        // tpl:endif
        // tpl:if ios
        iosMain.dependencies { implementation(libs.ktor.client.darwin) }
        // tpl:endif
    }
}
