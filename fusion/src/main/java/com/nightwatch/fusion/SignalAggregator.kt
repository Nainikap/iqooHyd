package com.nightwatch.fusion

import com.nightwatch.core.model.PerceptionSignal

/**
 * Merges asynchronous signals from every sensor into a single current snapshot.
 *
 * REQUIRED CONTENT:
 * - A sliding recency window per SensorKind (a stale posture must not outlive its frame).
 * - Thread-safe ingestion from camera/audio/IMU producers.
 * - Emits a fused snapshot on every relevant change.
 *
 * NOTE: breathing is corroboration only; the aggregator must not let it drive a tier.
 */
interface SignalAggregator {
    // TODO: fun ingest(signal: PerceptionSignal)
    // TODO: fun snapshot(): FusedSnapshot
}

/**
 * The latest known value of each signal, with timestamps.
 *
 * REQUIRED CONTENT:
 * - Nullable per-signal values (absence is meaningful: UNKNOWN, not "safe").
 * - `staleness(kind): Long` so policy can ignore stale signals.
 */
data class FusedSnapshot(
    val cry: com.nightwatch.core.model.CryEvent?,
    val posture: com.nightwatch.core.model.Posture?,
    val presence: com.nightwatch.core.model.PresenceResult?,
    val breathing: com.nightwatch.core.model.BreathingResult?,
    val hazard: com.nightwatch.core.model.HazardDetection?,
    val mount: com.nightwatch.core.model.MountEvent?,
    val capturedAt: Long,
)