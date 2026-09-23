// Root build script.
// Plugins are declared here with `apply false` and applied per-module.
// Keep this file free of module-specific configuration.

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
}