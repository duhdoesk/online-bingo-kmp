import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.hws.kotlinMultiplatform)
    alias(libs.plugins.hws.androidLibrary)
    alias(libs.plugins.kotlinSerialization)
}

ktlint {
    verbose.set(true)
    outputToConsole.set(true)
    enableExperimentalRules.set(true)
    filter {
        exclude { element ->
            element.file.path.contains("/build/generated/") || element.file.path.contains("/generated/")
        }
    }
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    sourceSets {
        commonMain.dependencies {
            // Project
            implementation(projects.core)

            // Kotlin
            implementation(libs.bundles.kotlin)
        }
    }
}

dependencies {
    ktlintRuleset(libs.ktlintRuleset)
}
