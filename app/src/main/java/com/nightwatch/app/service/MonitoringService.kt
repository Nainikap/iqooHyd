package com.nightwatch.app.service

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * Always-on foreground monitoring service.
 *
 * RESPONSIBILITIES:
 * - Post the persistent "NightWatch monitoring" notification (status only, no feed).
 * - Drive MonitoringPipeline: camera + audio + IMU -> perception -> fusion.
 * - Start/stop sensing based on the armed state; release everything on stop.
 *
 * REQUIRED CONTENT:
 * - Correct foregroundServiceType handling (camera|microphone) and pre-start permission checks.
 * - Bounded WAKE_LOCK handling: acquire only while needed and RELEASE on teardown.
 * - Thermal/power awareness: degrade CV cadence when the device throttles (P8).
 * - Lost-audio-focus / camera-interruption recovery.
 *
 * HARD RULES:
 * - No disk writes of frames/audio.
 * - No display wake lock.
 */
class MonitoringService : Service() {

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // TODO: startForeground(...); start MonitoringPipeline; return START_STICKY.
        TODO("Implement foreground monitoring startup.")
    }

    override fun onDestroy() {
        // TODO: stop pipeline, release wake lock, unregister sensors/camera.
        super.onDestroy()
    }
}