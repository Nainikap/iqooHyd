// sensing — raw sensor capture: camera frames, audio, IMU, ambient light.
// Owns hardware access. Must NOT contain domain/alert logic.
// Frames are RAM-only: no component here may write frames/audio to disk.

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.nightwatch.sensing"
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
    implementation(libs.camera.core)
    implementation(libs.camera.camera2)
    implementation(libs.camera.lifecycle)
    implementation(libs.tensorflow.lite)
    implementation(libs.tensorflow.lite.support)
}