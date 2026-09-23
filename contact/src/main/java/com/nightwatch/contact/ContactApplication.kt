package com.nightwatch.contact

import android.app.Application

/**
 * Receiver app entry point.
 *
 * REQUIRED CONTENT:
 * - Initialize the encrypted AlertStore.
 * - Register the receive path (paired-device listener / relay poller / push handler).
 * - No camera, no audio, no continuous location.
 */
class ContactApplication : Application() {
    // TODO: override fun onCreate() { /* init AlertStore + receive path */ }
}