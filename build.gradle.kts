// Top-level build file where you can add configuration options common to all sub-projects/modules.
import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
    id("org.owasp.dependencycheck") version "12.2.2"
    id("com.github.ben-manes.versions") version "0.54.0"
}

buildscript {
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${libs.versions.kotlin.get()}")
    }
}

dependencyCheck {
    outputDirectory.set(layout.buildDirectory.dir("reports"))
    format.set("ALL")
    suppressionFile.set("config/owasp-suppressions.xml")

    analyzers {
        assemblyEnabled.set(false)
        nodePackage {
            enabled.set(false)
        }
    }

    nvd {
        apiKey = System.getenv("NVD_API_KEY") ?: ""
        delay = 1000
    }
}

tasks.withType<DependencyUpdatesTask>().configureEach {
    checkForGradleUpdate = true
    outputFormatter = "json,xml,html"
    outputDir = "build/dependencyUpdates"
    rejectVersionIf {
        candidate.version.isNonStable()
    }
}

fun String.isNonStable(): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { uppercase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(this)
    return !isStable
}
