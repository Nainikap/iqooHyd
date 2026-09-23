package com.nightwatch.core.common

/**
 * Central log tags. Every module logs through these tags only.
 *
 * HARD RULE: never log frames, audio, imagery, or precise coordinates.
 * Structured, low-noise logging is required for post-incident debugging.
 *
 * REQUIRED CONTENT:
 * - A logger abstraction (so tests can inject a fake).
 * - Optional release-build redaction for any field marked sensitive.
 */
object LogTags {
    const val FRAME = "NightWatch.Frame"
    const val BABY = "NightWatch.Baby"
    const val POSTURE = "NightWatch.Posture"
    const val BREATHING = "NightWatch.Breathing"
    const val HAZARD = "NightWatch.Hazard"
    const val TIER = "NightWatch.Tier"
    const val ESCALATION = "NightWatch.Escalation"
}

/**
 * Thin logging interface so modules do not depend on android.util.Log directly.
 *
 * REQUIRED CONTENT:
 * - debug/info/warn/error methods.
 * - A default Android implementation and a no-op/test implementation.
 */
interface Logger {
    // TODO: fun debug(tag: String, message: String)
    // TODO: fun warn(tag: String, message: String, throwable: Throwable? = null)
    // TODO: fun error(tag: String, message: String, throwable: Throwable? = null)
}