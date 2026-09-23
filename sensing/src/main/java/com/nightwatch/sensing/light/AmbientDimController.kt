package com.nightwatch.sensing.light

/**
 * Drives screen dimming/dark-mode from the ambient light sensor.
 *
 * REQUIRED CONTENT:
 * - Reads the ambient light sensor and exposes a brightness recommendation.
 * - Ensures the display emits no visible light in a dark room near the crib.
 * - Never acquires a display wake lock.
 */
interface AmbientDimController {
    // TODO: val isDark: Flow<Boolean>
    // TODO: fun start()
    // TODO: fun stop()
}