package com.nightwatch.perception.baby

import com.nightwatch.core.model.Detection
import com.nightwatch.core.model.Rect
import com.nightwatch.sensing.camera.Frame

/**
 * Detects the baby in a frame and returns a NORMALIZED bounding box.
 *
 * REQUIRED CONTENT:
 * - Model: baby_detect.tflite (person/baby detection).
 * - Output uses normalized 0..1 frame coordinates (see Geometry.Rect).
 * - If the baby is not visible -> null, so callers can mark UNKNOWN rather than guess.
 *
 * ACCEPTANCE (P1): stable (low-jitter) bbox across a 5-minute session.
 */
interface BabyDetector {
    // TODO: fun detect(frame: Frame): Detection?
}

/**
 * Smooths and tracks the baby bbox across frames.
 *
 * REQUIRED CONTENT:
 * - Temporal smoothing to remove jitter (e.g. exponential moving average / simple tracker).
 * - Grace period when detection drops out (baby briefly occluded) before reporting null.
 * - Shared instance: posture, presence, breathing, and hazard all read from here.
 */
interface BabyTracker {
    // TODO: fun update(detection: Detection?, frameTimestamp: Long): Rect?
    // TODO: fun reset()
}