package com.nightwatch.contact.ui

import androidx.compose.runtime.Composable

/**
 * List of received alerts, newest first.
 *
 * REQUIRED CONTENT:
 * - Tier-colored rows: ROUTINE / URGENT / CRITICAL.
 * - Reasons (e.g. "hazard:cable", "posture:covered") and timestamp.
 * - Optional map link with an accuracy circle when coordinates are present.
 * - Opted-in clip playback when clipSnippet is present.
 */
@Composable
fun AlertListScreen() {
    // TODO: render alerts; open detail/map; play clip if provided.
}