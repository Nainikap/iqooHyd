package com.nightwatch.app

import android.os.Bundle
import androidx.activity.ComponentActivity

/**
 * Single activity hosting the Compose UI.
 *
 * REQUIRED CONTENT:
 * - Hosts ArmingScreen, CalibrationScreen, and PermissionFlow.
 * - Requests runtime permissions; starts MonitoringService from THIS visible context
 *   (Android 12+ requires starting a foreground service from a visible context).
 * - HARD RULES: turnScreenOn=false, showWhenLocked=false (never light the screen).
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: setContent { NightWatchApp(deps = ...) }
    }
}