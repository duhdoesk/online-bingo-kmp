plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.firebasePlugin) apply false
    alias(libs.plugins.crashlyticsPlugin) apply false
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.ktlint)
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
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            binaryOption("bundleId", "com.duscaranari.themedbingocardsgenerator")
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)

//            Ktor
            implementation(libs.ktor.client.okhttp)

//            Firebase
            implementation(project.dependencies.platform("com.google.firebase:firebase-bom:30.0.1"))

//            Koin
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.koin.android)
        }

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(compose.material3)

//            Decompose
            implementation(libs.decompose)
            implementation(libs.decompose.extensions.compose)

//            Ktor
            implementation(libs.ktor.client.core)

//            Coil
            implementation(libs.coil.compose.core)
            implementation(libs.coil.compose)
            implementation(libs.coil.mp)
            implementation(libs.coil.network.ktor)

//            Koin
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.koin.compose)

//            Firebase
            implementation(libs.gitlive.firebase.analytics)
            implementation(libs.gitlive.firebase.auth)
            implementation(libs.gitlive.firebase.crashlytics)
            implementation(libs.gitlive.firebase.firestore)
            implementation(libs.dev.firebase.common)
            implementation(libs.jetbrains.kotlinx.serialization.json)

//            DateTime
            implementation(libs.kotlinx.datetime)

//            Supabase
            implementation(project.dependencies.platform(libs.supabase.bom.get()))
            implementation(libs.supabase.auth.kt)
            implementation(libs.supabase.gotrue.kt)

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

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

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
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
    }
}

dependencies {
    ktlintRuleset(libs.ktlintRuleset)
}
