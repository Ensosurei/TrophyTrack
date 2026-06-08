import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)

    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.buildkonfig)
}

val localProperties = Properties().apply {
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        localPropertiesFile.inputStream().use { load(it) }
    }
}
val rawgApiKey: String = localProperties.getProperty("RAWG_API_KEY") ?: ""

kotlin {
    jvm()

    androidLibrary {
       namespace = "org.ensosurei.trophytrack.shared"
       compileSdk = libs.versions.android.compileSdk.get().toInt()
       minSdk = libs.versions.android.minSdk.get().toInt()

       compilerOptions {
           jvmTarget = JvmTarget.JVM_11
       }
       androidResources {
           enable = true
       }
       withHostTest {
           isIncludeAndroidResources = true
       }
    }
    
    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.ktor.client.okhttp)
        }
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            // LIBRARIES OF ROOM FOR KMP
            implementation(libs.room.runtime)
            implementation(libs.sqlite.bundled)

            // IMPLEMENTATION OF DATASTORE
            implementation(libs.androidx.datastore.preferences)

            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor3)

            implementation(libs.ktor.client.core)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.content.negotiation)

            implementation(libs.filekit.compose)
        }

        val jvmMain by getting {
            dependencies {
                implementation(libs.ktor.client.okhttp)
            }
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }

    targets.all {
        compilations.all {
            compilerOptions.configure {
                freeCompilerArgs.add("-Xexpect-actual-classes")
            }
        }
    }
}

buildkonfig {
    packageName = "org.ensosurei.trophytrack"

    defaultConfigs {
        buildConfigField(
            type = com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING,
            "RAWG_API_KEY",
            rawgApiKey)
    }
}

// SCHEMAS DIRECTORY CONFIGURATION
room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    androidRuntimeClasspath(libs.compose.uiTooling)

    // ROOM COMPILATORS
    add("kspAndroid", libs.room.compiler)
    add("kspJvm", libs.room.compiler)
}