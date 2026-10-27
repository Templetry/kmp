// The Android application: manifest, launcher resources and MainActivity.
// It is a plain Android module (AGP 9 built-in Kotlin, no KMP plugin) that
// depends on composeApp, the Kotlin Multiplatform library with the shared UI.
// AGP 9 no longer lets com.android.application share a module with the KMP
// plugin, which is why the app and the shared code are two modules.
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeCompiler)
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
    implementation(project(":composeApp"))

    implementation(libs.androidx.activity.compose)
    implementation(libs.compose.runtime)
    implementation(project.dependencies.platform(libs.koin.bom))
    implementation(libs.koin.android)

    debugImplementation(libs.compose.uiTooling)
}
