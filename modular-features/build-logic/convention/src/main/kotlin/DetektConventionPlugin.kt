import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class DetektConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        apply(plugin = "io.gitlab.arturbosch.detekt")
        configure<DetektExtension> {
            config.setFrom(rootProject.file("config/detekt/detekt.yml"))
            buildUponDefaultConfig = true
            autoCorrect = true
            parallel    = true
        }
        val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
        dependencies {
            "detektPlugins"(libs.findLibrary("detekt-formatting").get())
        }
    }
}
