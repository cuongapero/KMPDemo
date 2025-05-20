enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven(url = "https://s01.oss.sonatype.org/content/repositories/releases/")
    }
}

rootProject.name = "KMPDemo"
include(":androidApp")
include(":shared")
include(":composeApp")
