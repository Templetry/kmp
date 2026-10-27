plugins {
    alias(libs.plugins.android.app)
}

// androidApp is the Android application: manifest, launcher resources, Application and Activity.
// AGP 9 no longer allows com.android.application next to the KMP plugin, so the shared app lives
// in composeApp (a KMP library) and this module only wires it into Android.
dependencies {
    implementation(project(":composeApp"))
    // What the Android entry points touch directly: the app context for secure storage and the
    // SQLDelight driver factory registered in Koin.
    implementation(project(":core:security"))
    implementation(project(":data:local"))

    implementation(libs.compose.runtime)
    implementation(libs.compose.foundation)
    implementation(libs.compose.ui)
    implementation(libs.androidx.activity.compose)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.koin.core)
    implementation(libs.koin.android)

    debugImplementation(libs.leakcanary)
}
