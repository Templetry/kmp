rootProject.name = "kmplib"

pluginManagement {
    repositories {
        google {
            content {
                // Kotlin string literals: the dot is escaped for the regex and the
                // backslash escaped again for Kotlin. A single one is an illegal
                // escape and the build never parses.
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

// The build declares a toolchain. Without a resolver it only works on a machine
// that already happens to have that exact JDK, which is a bad thing to require
// of a contributor and a worse thing to require of CI.
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}
