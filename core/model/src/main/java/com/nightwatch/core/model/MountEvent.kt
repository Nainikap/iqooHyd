package com.nightwatch.core.model

/**
 * IMU mount disturbance (phone bumped, knocked off mount, or angle shifted).
 *
 * REQUIRED CONTENT:
 * - disturbance magnitude from the IMU anomaly detector.
 * - moved flag when the baseline angle shifted beyond a threshold (requires re-calibration).
 */
data class MountEvent(
    val timestamp: Long,
    val disturbance: Float,
    val moved: Boolean,
)