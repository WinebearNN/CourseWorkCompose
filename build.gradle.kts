import java.io.FileInputStream
import java.util.Properties

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false



}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath (libs.hilt.android.gradle.plugin)
        classpath (libs.gradle)
        classpath (libs.kotlin.gradle.plugin)
    }

}

val properties = Properties().apply {
    FileInputStream(file("local.properties")).use { load(it) }
}

extra["mapkitApiKey"] = properties.getOrDefault("MAPKIT_API_KEY", "")


