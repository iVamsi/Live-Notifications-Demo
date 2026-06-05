// Top-level build file where you can add configuration options common to all sub-projects/modules.
import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
    id("org.owasp.dependencycheck") version "12.1.0"
    id("com.github.ben-manes.versions") version "0.52.0"
}

buildscript {
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${libs.versions.kotlin.get()}")
    }
}

dependencyCheck {
    outputDirectory = "build/reports"
    format = "ALL"
    suppressionFile = "config/owasp-suppressions.xml"

    analyzers {
        assemblyEnabled = false
        nodeEnabled = false
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
