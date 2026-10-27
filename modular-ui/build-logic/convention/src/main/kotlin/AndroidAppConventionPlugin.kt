import ext.configureAndroidApp
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

/**
 * Convention for androidApp, the pure-Android application module: `com.android.application` with
 * AGP 9's built-in Kotlin and new DSL, and no KMP plugin. It holds the manifest, the launcher
 * resources and the Android entry points, and depends on composeApp, the KMP library with the
 * shared app. AGP 9 does not allow `com.android.application` and the KMP plugin in one module.
 * Applies: android application + Compose compiler + detekt.
 */
class AndroidAppConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        apply(plugin = "com.android.application")
        apply(plugin = "org.jetbrains.kotlin.plugin.compose")
        apply(plugin = "kmpbase.detekt")

        configureAndroidApp(namespace = "dev.template.kmpbase", applicationId = "dev.template.kmpbase")
    }
}
