package com.nightwatch.app.di

/**
 * Manual dependency-injection graph for the app (or replace with Hilt later).
 *
 * REQUIRED CONTENT:
 * - Construct sensing, perception, fusion, and transport providers (their Module
 *   objects) and expose singletons: CalibrationStore, StateMachine, MonitoringPipeline,
 *   EscalationCoordinator.
 * - Swap real implementations for fakes in tests and in the debug harness.
 * - Inject DispatcherProvider so no module hardcodes Dispatchers.
 */
object AppModule {
    // TODO: fun providePipeline(context: Context): MonitoringPipeline
    // TODO: fun provideStateMachine(context: Context): StateMachine
    // TODO: fun provideEscalation(context: Context): EscalationCoordinator
}