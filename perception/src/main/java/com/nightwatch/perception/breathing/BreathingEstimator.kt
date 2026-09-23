package com.nightwatch.perception.breathing

import com.nightwatch.core.model.BreathingResult
import com.nightwatch.core.model.Rect
import com.nightwatch.sensing.camera.Frame

/**
 * Estimates breathing rate from chest-motion optical flow.
 *
 * REQUIRED CONTENT:
 * - Operates over a stable window (~15–30 s) in the chest region of the baby bbox.
 * - Outputs rate + CONFIDENCE; low confidence must suppress output entirely.
 * - Never alerts on its own; the fusion layer only uses it as corroboration.
 * - Invalidate the window (return null) on posture change or heavy motion.
 *
 * ACCEPTANCE (P4): rate within tolerance on a cooperative subject; posture change
 *                   invalidates the window rather than emitting a stale rate.
 */
interface BreathingEstimator {
    // TODO: fun update(window: List<Frame>, baby: Rect): BreathingResult?
    // TODO: fun reset()
}