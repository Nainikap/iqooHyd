package com.nightwatch.contact

import android.os.Bundle
import androidx.activity.ComponentActivity

/**
 * Receiver UI host.
 *
 * REQUIRED CONTENT:
 * - Shows AlertListScreen.
 * - Surfaces a high-priority notification for CRITICAL tier (and URGENT).
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: setContent { AlertListScreen(alerts = ...) }
    }
}