package com.nightwatch.app.ui

import androidx.compose.runtime.Composable

/**
 * One-time (and re-runnable) calibration flow.
 *
 * REQUIRED CONTENT:
 * - Guide the user to frame the empty crib; capture frames to build the CribBoundary.
 * - Record baseline baby size / lighting.
 * - Persist via CalibrationStore.
 * - Show a prompt to re-run after any mount move (CalibrationStore.needsRecalibration).
 *
 * ACCEPTANCE (P1): calibration persists and restores; CV alerts are blocked until done.
 */
@Composable
fun CalibrationScreen() {
    // TODO: step-by-step capture UI + progress + save.
}