package com.nightwatch.fusion

import com.nightwatch.core.model.Polygon
import kotlinx.coroutines.flow.StateFlow

/**
 * Persists calibration/baseline data (crib boundary, baseline baby size, lighting).
 *
 * REQUIRED CONTENT:
 * - Backed by the encrypted local store (AndroidX Security / Keystore).
 * - Serialization of Polygon and baseline values.
 * - A `needsRecalibration` flag set when MountMonitor reports a moved mount.
 *
 * NOTE: re-calibration is REQUIRED after any mount move before CV alerts resume
 *       (ARCHITECTURE gap 5 / P6 acceptance).
 */
interface CalibrationStore {
    // TODO: val cribBoundary: StateFlow<Polygon?>
    // TODO: val baselineBabySize: StateFlow<Float?>
    // TODO: val needsRecalibration: StateFlow<Boolean>
    // TODO: suspend fun setCribBoundary(polygon: Polygon)
    // TODO: suspend fun markNeedsRecalibration()
}