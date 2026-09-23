package com.nightwatch.core.model

/** A raw object-detection result before whitelist filtering. */
data class Detection(
    val label: String,
    val bbox: Rect,
    val confidence: Float,
)

/**
 * A whitelisted hazard object with its relationship to the baby.
 *
 * REQUIRED CONTENT:
 * - label from the versioned HazardWhitelist (stable string; never renamed).
 * - proximity 0..1 and babyTouching flag (bbox proximity only, NOT grasping).
 */
data class HazardDetection(
    val id: String,
    val label: String,
    val bbox: Rect,
    val detectionConfidence: Float,
    val proximity: Float,
    val babyTouching: Boolean,
    val frameTimestamp: Long,
)

/** Output of HazardProximity. */
data class ProximityResult(
    val proximity: Float,
    val touching: Boolean,
)