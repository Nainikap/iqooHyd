package com.nightwatch.fusion

import com.nightwatch.core.model.AlertTier

/**
 * Prevents alert fatigue (ARCHITECTURE gap 11).
 *
 * REQUIRED CONTENT:
 * - Per-tier suppression windows (a persistent condition does not re-alert).
 * - Per-hazard-object suppression keyed by object identity; re-alert only on a NEW object.
 * - A clean/reset path when the condition clears for t_stable.
 *
 * REQUIRED FOR TESTING: pure logic, no Android dependencies, table-driven tests.
 */
interface SuppressionPolicy {
    // TODO: fun shouldEmit(tier: AlertTier, reasons: Set<String>, now: Long): Boolean
    // TODO: fun onCleared(reasons: Set<String>, now: Long)
    // TODO: fun reset()
}