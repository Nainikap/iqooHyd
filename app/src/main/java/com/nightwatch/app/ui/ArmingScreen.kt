package com.nightwatch.app.ui

import androidx.compose.runtime.Composable

/**
 * Primary screen: arm/disarm monitoring and show current state.
 *
 * REQUIRED CONTENT:
 * - Arm/disarm toggle -> StateMachine.arm()/disarm() and MonitoringService start/stop.
 * - Current TriggerState + tier banner (UNARMED/MONITORING/ROUTINE/URGENT/CRITICAL/TAMPER).
 * - Entry points to CalibrationScreen and PermissionFlow.
 * - A calm, dark UI; no bright colors at night.
 */
@Composable
fun ArmingScreen() {
    // TODO: collect StateMachine.state; render arm toggle + status; navigate.
}