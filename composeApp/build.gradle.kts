import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.jetbrains.kotlin.compose)
    alias(libs.plugins.jetbrains.compose)
}

kotlin {
    androidTarget()

    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinAndroidTarget> {
        compilations.all {
            compilerOptions.configure {
                jvmTarget.set(JvmTarget.JVM_17)
            }
        }
    }

    val xcfName = "composeApp"

    iosX64 {
        binaries.framework {
            baseName = xcfName
            freeCompilerArgs += listOf("-Xbinary=bundleId=com.apero.composeapp")
            linkerOpts("-lsqlite3")
        }
    }
    iosArm64 {
        binaries.framework {
            baseName = xcfName
            freeCompilerArgs += listOf("-Xbinary=bundleId=com.apero.composeapp")
            linkerOpts("-lsqlite3")
        }
    }
    iosSimulatorArm64 {
        binaries.framework {
            baseName = xcfName
            freeCompilerArgs += listOf("-Xbinary=bundleId=com.apero.composeapp")
            linkerOpts("-lsqlite3")
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlin.stdlib)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kotlinx.datetime)

                // Koin
                implementation(libs.koin.core)

                //Compose
                implementation(compose.runtime)
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.components.resources)

                implementation(libs.androidx.lifecycle.viewmodel.compose)
                implementation(libs.landscapist.coil)
                implementation(project(":shared"))
                implementation(project(":picker_image"))
            }
        }

        androidMain {
            dependencies {
                implementation(libs.compose.ui.tooling.preview)
                implementation(libs.androidx.activity.compose)
            }
        }

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        
        iosMain {
            dependsOn(commonMain.get())
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation(compose.runtime)
            }
        }
    }
}

android {
    defaultConfig {
        namespace = "com.apero.composeapp"
        compileSdk = 35
        minSdk = 24
    }
    buildFeatures {
        compose = true
    }
    namespace = "com.apero.composeapp"
    
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


