// transport — the ONLY module that reaches the network (ARCHITECTURE §2).
// Isolating delivery here keeps the on-device guarantee auditable and makes
// transports swappable (paired device, SMS, relay).
//
// OPEN ITEM: the paired-device transport SDK ("Office Kit" equivalent) is undefined.
// Implement against the Transport interface with a fake loopback adapter first (P7).

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.nightwatch.transport"
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
    implementation(libs.play.services.location)
}