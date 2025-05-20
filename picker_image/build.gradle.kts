import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrains.kotlin.compose)
    alias(libs.plugins.jetbrains.compose)
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    androidTarget()

    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinAndroidTarget> {
        compilations.all {
            compilerOptions.configure {
                jvmTarget.set(JvmTarget.JVM_17)
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "picker_image"
        }
    }

    sourceSets {
        sourceSets {
            val commonMain by getting {
                dependencies {
                    implementation(libs.kotlinx.coroutines.core)
                    implementation(libs.koin.core)
                    implementation(compose.runtime)
                }
            }
            val androidMain by getting {
                dependencies {
                    implementation(libs.androidx.activity.compose)
                }
            }
            val iosX64Main by getting
            val iosArm64Main by getting
            val iosSimulatorArm64Main by getting
            val iosMain by creating {
                dependsOn(commonMain)
                iosX64Main.dependsOn(this)
                iosArm64Main.dependsOn(this)
                iosSimulatorArm64Main.dependsOn(this)
                dependencies {
                }
            }
        }
    }
}

android {
    defaultConfig {
        namespace = "com.apero.picker_image"
        compileSdk = 35
        minSdk = 24
    }
    buildFeatures {
        compose = true
    }
    namespace = "com.apero.picker_image"

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

val iosTargets = listOf(
//    kotlin.targets.getByName("iosX64") as KotlinNativeTarget,
    kotlin.targets.getByName("iosArm64") as KotlinNativeTarget,
    kotlin.targets.getByName("iosSimulatorArm64") as KotlinNativeTarget
)

tasks.register("assembleXCFramework") {
    group = "build"

    doFirst {
        buildDir.resolve("xcode-frameworks").deleteRecursively()
    }

    dependsOn(
        iosTargets.map {
            it.binaries.getFramework("DEBUG").linkTaskProvider
        }
    )

    doLast {
        val outputDir = buildDir.resolve("xcode-frameworks")
        outputDir.mkdirs()

        val frameworks = iosTargets.map {
            it.binaries.getFramework("DEBUG")
        }

        val command = listOf(
            "xcodebuild",
            "-create-xcframework"
        ) + frameworks.flatMap {
            listOf("-framework", it.outputDirectory.resolve("${it.baseName}.framework").absolutePath)
        } + listOf(
            "-output", outputDir.resolve("composeApp.xcframework").absolutePath
        )

        println("➡️ Running: ${command.joinToString(" ")}")
        exec {
            commandLine(command)
        }
    }
}

