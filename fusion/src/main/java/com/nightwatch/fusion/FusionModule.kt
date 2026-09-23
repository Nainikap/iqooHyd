package com.nightwatch.fusion

/**
 * Dependency-injection entry point for the fusion layer.
 *
 * REQUIRED CONTENT:
 * - Providers for SignalAggregator, TierClassifier, SuppressionPolicy, CalibrationStore,
 *   and the StateMachine (singleton; the app service observes its StateFlow).
 * - A fake StateMachine and fake classifier for tiering unit tests.
 */
object FusionModule {
    // TODO: fun signalAggregator(): SignalAggregator
    // TODO: fun tierClassifier(): TierClassifier
    // TODO: fun suppressionPolicy(): SuppressionPolicy
    // TODO: fun calibrationStore(context: Context): CalibrationStore
    // TODO: fun stateMachine(...): StateMachine
}