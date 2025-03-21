import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.internal.extensions.stdlib.capitalized

fun Project.getLibs(): VersionCatalog =
    extensions.findByType(VersionCatalogsExtension::class.java)?.named("libs")
        ?: error("Vestion Catalog named 'libs' not found.")

val Project.moduleName get() = path
    .split(":")
    .filter { it.isNotBlank() }
    .joinToString("") { it.capitalized() }

val Project.modulePackageName get() = path
    .split(":")
    .filter { it.isNotBlank() }
    .joinToString(".") { it.lowercase() }