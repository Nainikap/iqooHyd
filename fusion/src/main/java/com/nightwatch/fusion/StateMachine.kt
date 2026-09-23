package com.nightwatch.fusion

import com.nightwatch.core.model.TriggerState
import kotlinx.coroutines.flow.StateFlow

/**
 * Owns the monitor state machine (ARCHITECTURE §4).
 *
 * STATES: UNARMED -> MONITORING -> {ROUTINE | URGENT | CRITICAL | TAMPER}
 * Transitions must be validated; illegal transitions are rejected and logged.
 *
 * REQUIRED CONTENT:
 * - arm()/disarm() from the UI; state survives process death.
 * - onSignal(FusedSnapshot) applies TierClassifier then SuppressionPolicy.
 * - Persisted state so a service restart resumes MONITORING if previously armed.
 */
interface StateMachine {
    // TODO: val state: StateFlow<TriggerState>
    // TODO: fun onSignal(snapshot: FusedSnapshot)
    // TODO: fun arm()
    // TODO: fun disarm()
}

/** Default implementation; persists state via CalibrationStore/encrypted store. */
class StateMachineImpl(
    // TODO: private val classifier: TierClassifier,
    // TODO: private val suppression: SuppressionPolicy,
    // TODO: private val store: CalibrationStore,
) : StateMachine {
    // TODO: override val state: StateFlow<TriggerState>
    // TODO: override fun onSignal(snapshot: FusedSnapshot) { ... }
    // TODO: override fun arm() { ... }
    // TODO: override fun disarm() { ... }
}