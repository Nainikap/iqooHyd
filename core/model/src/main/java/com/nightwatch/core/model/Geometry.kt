package com.nightwatch.core.model

/**
 * Minimal geometry types so core:model stays free of Android dependencies.
 * All coordinates are NORMALIZED to the frame (0.0..1.0) unless stated otherwise,
 * making them independent of camera resolution and device model.
 *
 * REQUIRED CONTENT:
 * - Rect: left/top/right/bottom floats + width/height helpers.
 * - Polygon: ordered vertices for the calibrated crib boundary.
 * - Intersection helpers (IoU, center distance) used by HazardProximity.
 */
data class Rect(
    val left: Float,
    val top: Float,
    val right: Float,
    val bottom: Float,
) {
    // TODO: width, height, center, intersection, union, area
    // TODO: intersected(other: Rect): Rect?
    // TODO: iou(other: Rect): Float
}

data class Point(val x: Float, val y: Float)

data class Polygon(
    val vertices: List<Point>,
) {
    // TODO: contains(point: Point): Boolean  — point-in-polygon for inCrib checks
    // TODO: boundaryDistance(point: Point): Float — used for edgeRisk
    // REQUIRED: must be serializable for CalibrationStore persistence.
}