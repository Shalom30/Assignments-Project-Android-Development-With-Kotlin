pluginManagement {
    repositories {
        gradlePluginPortal()
        maven { url = uri("https://maven.google.com") }
        maven { url = uri("https://plugins.gradle.org/m2/") }
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositories {
        maven { url = uri("https://maven.google.com") }
        maven { url = uri("https://plugins.gradle.org/m2/") }
        mavenCentral()
    }
}

rootProject.name = "Student-grade"