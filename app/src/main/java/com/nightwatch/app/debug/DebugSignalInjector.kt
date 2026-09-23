package com.nightwatch.app.debug

import com.nightwatch.core.model.CryCategory
import com.nightwatch.core.model.Posture

/**
 * Simulates sensor signals so fusion/tiering/escalation can be tested without a subject.
 * DEBUG BUILDS ONLY. Never compile or expose this in release.
 *
 * REQUIRED CONTENT:
 * - emitCry / emitPosture / emitPresence / emitBreathing / emitHazard / emitMount.
 * - A fake FrameSource that plays recorded/synthetic scenes for deterministic CV tests.
 */
class DebugSignalInjector {
    // TODO: fun emitCry(category: CryCategory, confidence: Float)
    // TODO: fun emitPosture(posture: Posture)
    // TODO: fun emitHazard(label: String, proximity: Float, touching: Boolean)
    // TODO: fun emitMount(disturbance: Float, moved: Boolean)
}