package com.nightwatch.perception.posture

import com.nightwatch.core.model.Posture
import com.nightwatch.core.model.Rect
import com.nightwatch.sensing.camera.Frame

/**
 * Classifies the baby's posture from the shared baby bbox.
 *
 * REQUIRED CONTENT:
 * - Model: posture.tflite -> SUPINE / PRONE / SIDE / COVERED / UNKNOWN.
 * - COVERED is an occlusion class (blanket/soft object over face/body), NOT a COCO
 *   "blanket" detection (see ARCHITECTURE gap 2).
 * - Return UNKNOWN, never a confident guess, when signal is poor.
 *
 * ACCEPTANCE (P3): covering the doll's face fires COVERED within 3 s; normal
 *                   sleeping motion does not fire for 30 min.
 */
interface PostureClassifier {
    // TODO: fun classify(frame: Frame, baby: Rect): Posture
    // TODO: fun confidence(): Float
}