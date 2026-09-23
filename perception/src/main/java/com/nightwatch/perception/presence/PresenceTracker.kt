package com.nightwatch.perception.presence

import com.nightwatch.core.model.Point
import com.nightwatch.core.model.PresenceResult
import com.nightwatch.core.model.Rect

/**
 * The calibrated crib boundary used for presence and rollover math.
 *
 * REQUIRED CONTENT:
 * - Captured during calibration from empty-crib frames.
 * - Serializable for CalibrationStore.
 * - edgeRisk is RELATIVE to this polygon; re-calibrate when the room is rearranged.
 */
data class CribBoundary(
    val polygon: com.nightwatch.core.model.Polygon,
) {
    // TODO: fun edgeRisk(baby: Rect): Float
    // TODO: fun contains(point: Point): Boolean
}

/**
 * Tracks whether the baby is in frame and still inside the crib.
 *
 * REQUIRED CONTENT:
 * - inFrame, inCrib, and edgeRisk (0..1) relative to the crib boundary.
 * - Debounce so a momentary detection drop does not trigger a rollover alert.
 *
 * ACCEPTANCE (P3): rollover to the crib edge fires within 3 s; fully occluded baby
 *                   yields an UNKNOWN/caregiver prompt, never a silent pass.
 */
interface PresenceTracker {
    // TODO: fun update(baby: Rect, boundary: CribBoundary): PresenceResult
    // TODO: fun reset()
}