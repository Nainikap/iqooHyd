package com.nightwatch.contact.store

import com.nightwatch.core.model.AlertPayload
import kotlinx.coroutines.flow.StateFlow

/**
 * Persists received alerts locally (encrypted).
 *
 * REQUIRED CONTENT:
 * - Backed by AndroidX Security / Keystore.
 * - De-duplication by alertId on save.
 * - A bounded retention policy (e.g. 30 days) so storage does not grow unbounded.
 * - Exposes a StateFlow of alerts for the UI.
 */
interface AlertStore {
    // TODO: suspend fun save(payload: AlertPayload): Boolean   // false if duplicate
    // TODO: val alerts: StateFlow<List<AlertPayload>>
    // TODO: suspend fun purgeOlderThan(millis: Long)
}