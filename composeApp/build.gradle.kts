import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.hws.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.crashlyticsPlugin) apply false
    alias(libs.plugins.firebasePlugin) apply false
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
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

//            Audio
            implementation(libs.exoPlayer)

//            Ktor
            implementation(libs.ktor.client.okhttp)

//            Firebase
            implementation(project.dependencies.platform("com.google.firebase:firebase-bom:30.0.1"))
        }

        commonMain.dependencies {
            // Projects
            implementation(projects.core)
            implementation(projects.data)
            implementation(projects.domain)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(compose.material3)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)

//            Navigation
            implementation(libs.decompose)
            implementation(libs.decompose.extensions.compose)
            implementation(libs.jetpack.navigation)

//            Ktor
            implementation(libs.ktor.client.core)

//            Coil
            implementation(libs.coil.compose)
            implementation(libs.coil.ktor3)

//            Koin
            implementation(libs.bundles.koin.compose)

//            Firebase
            implementation(libs.gitlive.firebase.analytics)
            implementation(libs.gitlive.firebase.auth)
            implementation(libs.gitlive.firebase.crashlytics)
            implementation(libs.gitlive.firebase.firestore)
            implementation(libs.dev.firebase.common)

//            Supabase
            implementation(libs.supabase.auth.compose)
            implementation(libs.supabase.realtime.kt)

//            Revenue Cat
            implementation(libs.purchases.core)
            implementation(libs.purchases.result)
            implementation(libs.purchases.ui)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }

        named { it.lowercase().startsWith("ios") }.configureEach {
            languageSettings {
                optIn("kotlinx.cinterop.ExperimentalForeignApi")
            }
        }
    }
}

android {
    apply(plugin = "com.google.gms.google-services")
    apply(plugin = "com.google.firebase.crashlytics")

    namespace = "com.duscaranari.themedbingocardsgenerator"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.duscaranari.themedbingocardsgenerator"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 45
        versionName = "6.2"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(libs.androidx.ui.tooling)
    ktlintRuleset(libs.ktlintRuleset)
}
