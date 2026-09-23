package com.nightwatch.sensing

/**
 * Dependency-injection entry point for the sensing layer.
 *
 * REQUIRED CONTENT:
 * - Factory functions / providers for FrameSource, CryClassifier, AudioCapture,
 *   MountMonitor, and AmbientDimController.
 * - Test fakes for every interface above (used by fusion and end-to-end tests).
 *
 * NOTE: wires into the app-level DI graph (app/di/AppModule.kt).
 */
object SensingModule {
    // TODO: fun frameSource(context: Context): FrameSource
    // TODO: fun cryClassifier(context: Context): CryClassifier
    // TODO: fun audioCapture(context: Context): AudioCapture
    // TODO: fun mountMonitor(context: Context): MountMonitor
    // TODO: fun ambientDim(context: Context): AmbientDimController
}