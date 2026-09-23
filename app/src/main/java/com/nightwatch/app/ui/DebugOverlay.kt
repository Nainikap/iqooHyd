package com.nightwatch.app.ui

import androidx.compose.runtime.Composable

/**
 * Debug-only overlay (never shipped in release).
 *
 * REQUIRED CONTENT:
 * - Baby bbox + crib polygon drawn over the current frame.
 * - Live per-signal confidence, fused tier, and suppression state.
 * - Buttons wired to DebugSignalInjector (Force Tier 0/1/2, Force TAMPER, Clear Suppression).
 */
@Composable
fun DebugOverlay() {
    // TODO: draw bboxes/polygon; show signals; injection buttons.
}