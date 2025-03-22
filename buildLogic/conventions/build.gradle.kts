plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.android.gradle.plugin)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("kotlinMultiplatform") {
            id = libs.plugins.hws.kotlinMultiplatform.get().pluginId
            implementationClass = "KotlinMultiplatformConventionPlugin"
        }

        register("androidLibrary") {
            id = libs.plugins.hws.androidLibrary.get().pluginId
            implementationClass = "AndroidLibraryConventionPlugin"
        }
    }
}