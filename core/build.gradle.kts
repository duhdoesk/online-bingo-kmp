plugins {
    alias(libs.plugins.hws.kotlinMultiplatform)
    alias(libs.plugins.hws.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
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
    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(compose.material3)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization)
        }
    }
}

dependencies {
    debugImplementation(libs.androidx.ui.tooling)
    ktlintRuleset(libs.ktlintRuleset)
}
