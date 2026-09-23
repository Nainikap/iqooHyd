package com.nightwatch.sensing.imu

import com.nightwatch.core.model.MountEvent

/**
 * Detects if the phone/mount is bumped, knocked, or its angle drifts.
 *
 * REQUIRED CONTENT:
 * - Reads accelerometer + gyroscope (register/unregister with the sensor manager).
 * - Anomaly detection for a sharp bump; baseline-angle drift detection for a moved mount.
 * - Emits MountEvent with `moved=true` when re-calibration is required.
 *
 * ACCEPTANCE (P6): a bump fires within 2 s; a fan/door does not.
 */
interface MountMonitor {
    // TODO: val events: Flow<MountEvent>
    // TODO: suspend fun start()
    // TODO: suspend fun stop()
    // TODO: fun setBaseline()   // call on calibration
}