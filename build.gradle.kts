
// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath(libs.secrets.gradle.plugin)
    }
}
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    id("com.google.dagger.hilt.android") version "2.49" apply false
    id("androidx.room") version "2.6.1" apply false
    alias(libs.plugins.google.gms.google.services) apply false
    alias(libs.plugins.compose.compiler) apply false
}

allprojects {
    repositories {
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots/") }
    }
}

