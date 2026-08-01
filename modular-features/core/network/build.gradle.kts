plugins { alias(libs.plugins.kmp.core) }

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.bundles.ktor.common)
            implementation(project(":core:logging"))
            api(libs.kotlinx.serialization.json)
        }
        androidMain.dependencies { implementation(libs.ktor.client.okhttp) }
        getByName("desktopMain").dependencies { implementation(libs.ktor.client.java) }
        getByName("iosMain").dependencies { implementation(libs.ktor.client.darwin) }
    }
}
