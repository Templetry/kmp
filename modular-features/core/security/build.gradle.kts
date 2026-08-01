plugins { alias(libs.plugins.kmp.core) }

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.bundles.settings.bundle)
        }
        androidMain.dependencies {
            implementation(libs.security.crypto)
            implementation(libs.multiplatform.settings.no.arg)
        }
        getByName("iosMain").dependencies {
            implementation(libs.multiplatform.settings.no.arg)
        }
        getByName("desktopMain").dependencies {
            implementation(libs.multiplatform.settings.no.arg)
        }
    }
}
