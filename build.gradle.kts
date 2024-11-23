// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
}
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:${libs.versions.agp}") // Android Gradle Plugin
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${libs.versions.kotlin}") // Kotlin plugin
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.52") // Hilt Gradle plugin
    }
}
