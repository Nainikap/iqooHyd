// fusion — signal aggregation, tier classification, state machine, calibration.
// This module turns PerceptionSignals into an AlertTier. It owns alert POLICY:
// tiering and suppression live here so they can be tested without any CV code.

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.nightwatch.fusion"
    compileSdk = 34
    defaultConfig { minSdk = 26 }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions { jvmTarget = "17" }
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:common"))

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.security.crypto)
}