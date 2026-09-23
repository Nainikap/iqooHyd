package com.nightwatch.fusion

import com.nightwatch.core.model.AlertTier

/**
 * Maps a fused snapshot to an alert tier.
 *
 * POLICY (from ARCHITECTURE §4):
 *   ROUTINE  <- cry / stir
 *   URGENT   <- face-down/covered posture OR edge rollover
 *   CRITICAL <- hazard within proximity of the baby
 * Precedence: CRITICAL > URGENT > ROUTINE. Lower-tier signals during a critical event
 * are recorded as reasons but do not change the tier.
 *
 * REQUIRED CONTENT:
 * - Persistence requirement per condition (must hold briefly to suppress flicker).
 * - reasons set (e.g. {"posture:covered", "hazard:cable"}) for the alert payload.
 */
interface TierClassifier {
    // TODO: fun classify(snapshot: FusedSnapshot): Classification?
}

/** Result of classification: the tier plus the human-readable reasons. */
data class Classification(
    val tier: AlertTier,
    val reasons: Set<String>,
)