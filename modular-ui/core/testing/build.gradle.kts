plugins { alias(libs.plugins.kmp.core) }

kotlin {
    sourceSets {
        // A test-support module: its public API (CoroutineTestRule) exposes these types,
        // so consumers' test source sets get them through `api`.
        commonMain.dependencies {
            api(kotlin("test"))
            api(libs.kotlinx.coroutines.core)
            api(libs.kotlinx.coroutines.test)
            api(libs.koin.test)
        }
    }
}
