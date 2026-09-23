package com.nightwatch.app.ui

import androidx.compose.runtime.Composable

/**
 * Contextual runtime permission requests with rationale.
 *
 * REQUIRED CONTENT:
 * - CAMERA, RECORD_AUDIO, POST_NOTIFICATIONS, BLUETOOTH_*, (optional) SEND_SMS.
 * - Foreground-service prerequisites: request the matching permission BEFORE
 *   startForeground with that service type.
 * - Clear, non-alarming rationale; explain on-device privacy guarantees.
 */
@Composable
fun PermissionFlow() {
    // TODO: check/request permissions; gate arming until required ones are granted.
}