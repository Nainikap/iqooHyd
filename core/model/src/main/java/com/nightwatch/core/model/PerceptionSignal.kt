package com.nightwatch.core.model

/** Which sensor/perception stage produced a signal. */
enum class SensorKind {
    CRY,
    POSTURE,
    PRESENCE,
    BREATHING,
    HAZARD,
    MOUNT,
}

/**
 * Normalized signal handed to the fusion engine.
 *
 * REQUIRED CONTENT:
 * - kind, confidence (0..1), timestamp.
 * - Optional typed payloads so the aggregator can inspect details without casting.
 *
 * DESIGN NOTE: keep this additive; fusion consumes it via SignalAggregator.
 */
data class PerceptionSignal(
    val kind: SensorKind,
    val confidence: Float,
    val timestamp: Long,
    val cry: CryEvent? = null,
    val posture: Posture? = null,
    val presence: PresenceResult? = null,
    val breathing: BreathingResult? = null,
    val hazard: HazardDetection? = null,
    val mount: MountEvent? = null,
)