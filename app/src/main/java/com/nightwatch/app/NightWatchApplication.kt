package com.nightwatch.app

import android.app.Application

/**
 * Application entry point.
 *
 * REQUIRED CONTENT:
 * - Initialize the DI graph (AppModule).
 * - Initialize logging (Logger implementation, release redaction).
 * - Restore armed state from the encrypted store so MonitoringService can resume.
 * - Do NOT start the camera/mic here; sensing starts only when armed (P0).
 */
class NightWatchApplication : Application() {
    // TODO: override fun onCreate() { /* build AppModule, restore state */ }
}