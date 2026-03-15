import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm") version "1.9.23"
    id("org.jetbrains.compose") version "1.6.2"
    id("org.jetbrains.dokka") version "1.9.10"
}

group = "com.gradecalc"
version = "1.0.0"

repositories {
    mavenCentral()
    google()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    maven { url = uri("https://maven.google.com") }
    maven { url = uri("https://jcenter.bintray.com") }
    maven { url = uri("https://plugins.gradle.org/m2/") }
    mavenCentral()
    gradlePluginPortal()

}

kotlin {
    jvmToolchain(21)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "21"
    }
}

tasks.withType<JavaCompile> {
    sourceCompatibility = "21"
    targetCompatibility = "21"
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(compose.material3)
    implementation(compose.materialIconsExtended)

    implementation("org.apache.poi:poi:5.2.5")
    implementation("org.apache.poi:poi-ooxml:5.2.5")
    implementation("org.apache.xmlbeans:xmlbeans:5.1.1")
    implementation("org.apache.commons:commons-compress:1.26.1")
    implementation("commons-io:commons-io:2.15.1")
    implementation("org.apache.logging.log4j:log4j-core:2.20.0")
    dokkaHtmlPlugin("org.jetbrains.dokka:kotlin-as-java-plugin:1.9.10")
}

compose.desktop {
    application {
        mainClass = "com.gradecalc.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Msi)
            packageName = "GradeDesk"
            packageVersion = "1.0.0"
        }
    }
}