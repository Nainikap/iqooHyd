package com.nightwatch.app.pipeline

/**
 * The coordinator that wires the whole monitor together.
 *
 * DATA FLOW (ARCHITECTURE §2/§3):
 *   FrameSource ──► BabyTracker ──► Posture/Presence/Breathing/Hazard
 *   CryClassifier ─┐
 *   MountMonitor ──┼─► SignalAggregator ──► StateMachine ──► EscalationCoordinator
 *   AmbientDim ────┘
 *
 * REQUIRED CONTENT:
 * - Own coroutine scopes for camera/audio/IMU producers; cancel on stop.
 * - Per-stage cadence: posture every frame; hazard every N frames; breathing in windows.
 * - Feed every PerceptionSignal into SignalAggregator; drive StateMachine with snapshots.
 * - On state change, call EscalationCoordinator (async; never block tiering).
 * - Thermal-aware degradation hook (drop camera cadence, then drop camera to audio+IMU).
 *
 * HARD RULES:
 * - Frames are borrowed from the camera pool and released after processing.
 * - No disk writes; the only persistence is the encrypted calibration/armed state.
 */
interface MonitoringPipeline {
    // TODO: fun start(scope: kotlinx.coroutines.CoroutineScope)
    // TODO: fun stop()
}

/** Default implementation, constructed via AppModule with all module dependencies. */
class MonitoringPipelineImpl(
    // TODO: inject FrameSource, CryClassifier, AudioCapture, MountMonitor, AmbientDim,
    //       BabyDetector/Tracker, PostureClassifier, PresenceTracker, BreathingEstimator,
    //       HazardDetector/Whitelist/Proximity, SignalAggregator, StateMachine,
    //       EscalationCoordinator, FrameThrottle, Logger, DispatcherProvider.
) : MonitoringPipeline {
    // TODO: override fun start(scope) { ... }
    // TODO: override fun stop() { ... }
}