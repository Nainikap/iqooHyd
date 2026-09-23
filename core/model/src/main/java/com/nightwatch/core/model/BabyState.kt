package com.nightwatch.core.model

/** Baby sleep posture as classified from the camera. */
enum class Posture {
    SUPINE,
    PRONE,
    SIDE,

    /** Face/body occluded by a blanket or soft object -> treated as risky. */
    COVERED,

    /** Not enough signal (dark, fully occluded, no detection). */
    UNKNOWN,
}

/** Current fused view of the baby. Shared by posture, presence, breathing, hazard. */
data class BabyState(
    val bbox: Rect,
    val posture: Posture,
    val inCrib: Boolean,
    /** 0..1 proximity to the calibrated crib boundary; 1 = at/over the edge. */
    val edgeRisk: Float,
    /** breaths/min, null when not estimable. */
    val breathingRate: Float?,
    val breathingConfidence: Float,
    val updatedAt: Long,
)

/** Output of PresenceTracker. */
data class PresenceResult(
    val inFrame: Boolean,
    val inCrib: Boolean,
    val edgeRisk: Float,
)

/** Output of BreathingEstimator; confidence-gated and never alerts alone. */
data class BreathingResult(
    val rate: Float,
    val confidence: Float,
    val windowMillis: Long,
)