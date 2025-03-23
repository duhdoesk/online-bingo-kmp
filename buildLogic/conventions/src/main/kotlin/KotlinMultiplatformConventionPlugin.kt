import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KotlinMultiplatformConventionPlugin : Plugin<Project> {

    override fun apply(target: Project): Unit = with(target) {
        val libs = getLibs()
        plugins.apply(libs.findPlugin("kotlinMultiplatform").get().get().pluginId)
        plugins.apply(libs.findPlugin("ktlint").get().get().pluginId)

        extensions.configure<KotlinMultiplatformExtension> {
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

            sourceSets.configureEach {
                when (name) {
                    "commonMain" -> dependencies {
                        implementation(libs.findLibrary("koin.core").get().get())
                    }
                }
            }
        }
    }
}