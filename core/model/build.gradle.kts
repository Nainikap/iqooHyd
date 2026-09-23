// core:model — pure data shapes shared across every module.
// KEEP THIS MODULE DEPENDENCY-FREE (no Android, no coroutines, no TFLite).
// These types are serialized and exchanged with the receiver app, so they are
// the most expensive thing to change later. Version changes deliberately.

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.nightwatch.core.model"
    compileSdk = 34
    defaultConfig { minSdk = 26 }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions { jvmTarget = "17" }
}

dependencies {
    // Intentionally empty. Models must not pull in implementation libraries.
}