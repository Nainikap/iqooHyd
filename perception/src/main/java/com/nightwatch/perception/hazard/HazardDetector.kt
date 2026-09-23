package com.nightwatch.perception.hazard

import com.nightwatch.core.model.Detection
import com.nightwatch.core.model.ProximityResult
import com.nightwatch.core.model.Rect
import com.nightwatch.sensing.camera.Frame

/**
 * Runs a COCO-pretrained lightweight detector (YOLO-nano / MobileNet SSD).
 *
 * REQUIRED CONTENT:
 * - Model: hazard_detect.tflite. NO infant-specific training for v1.
 * - Returns RAW detections; filtering happens in HazardWhitelist.
 * - Runs every N frames (see FrameThrottle) to save power.
 */
interface HazardDetector {
    // TODO: fun detect(frame: Frame): List<Detection>
}

/**
 * Filters raw detections down to hazard classes.
 *
 * REQUIRED CONTENT:
 * - A VERSIONED whitelist (cable, bag, bottle, small object, outlet, ...).
 * - label(raw): String? -> null when the class is not a hazard.
 * - Adding classes is additive; existing labels are never renamed.
 */
interface HazardWhitelist {
    // TODO: val version: String
    // TODO: fun label(raw: Detection): String?
}

/**
 * Measures proximity/overlap between a hazard and the baby bbox.
 *
 * REQUIRED CONTENT:
 * - proximity 0..1 from IoU and normalized center distance.
 * - touching=true above a contact threshold.
 * - SCOPE: bbox proximity only. Do NOT attempt hand-object grasping detection.
 *
 * ACCEPTANCE (P5): a cable next to the doll fires within 1 s; a far object does not.
 */
interface HazardProximity {
    // TODO: fun score(hazard: Rect, baby: Rect): ProximityResult
}