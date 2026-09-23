package com.nightwatch.app.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * Restores monitoring after device reboot.
 *
 * REQUIRED CONTENT:
 * - Read persisted armed state; if armed, start MonitoringService.
 * - If disarmed, do nothing.
 * - Guard against starting a foreground service illegally on boot (Android 12+):
 *   use the appropriate foreground-service start path / notification.
 */
class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        // TODO: if (persistedArmed) startForegroundService(context, MonitoringService)
    }
}