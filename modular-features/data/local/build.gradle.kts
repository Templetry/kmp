plugins {
    alias(libs.plugins.kmp.data)
    alias(libs.plugins.sqldelight)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.sqldelight.runtime)
            implementation(libs.sqldelight.coroutines)
            implementation(project(":data:models"))
        }
        androidMain.dependencies  { implementation(libs.sqldelight.android.driver) }
        // tpl:if desktop
        getByName("desktopMain").dependencies { implementation(libs.sqldelight.jvm.driver) }
        // tpl:endif
        // tpl:if ios
        iosMain.dependencies    { implementation(libs.sqldelight.native.driver) }
        // tpl:endif
    }
}

sqldelight {
    databases {
        create("AppDatabase") {
            packageName.set("dev.template.kmpbase.data.local.db")
            srcDirs.setFrom("src/commonMain/sqldelight")
        }
    }
}
