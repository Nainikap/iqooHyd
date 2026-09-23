package com.nightwatch.transport.queue

import com.nightwatch.core.model.AlertPayload

/**
 * At-least-once, store-and-forward delivery (ARCHITECTURE §10).
 *
 * REQUIRED CONTENT:
 * - Enqueue on TransportResult.Retryable / when no channel is reachable.
 * - Try transports in order: PAIRED_DEVICE -> SMS -> RELAY (skip unreachable ones).
 * - Exponential backoff; persist the queue (encrypted) so it survives process death.
 * - De-duplication keyed by AlertPayload.alertId.
 * - Clear an entry on Delivered or on a delivery receipt.
 *
 * Tier 2 must NOT block on this queue: dispatch is initiated asynchronously.
 */
interface StoreAndForwardQueue {
    // TODO: suspend fun enqueue(payload: AlertPayload)
    // TODO: suspend fun drain()                 // attempt all queued payloads
    // TODO: fun observePendingCount(): kotlinx.coroutines.flow.StateFlow<Int>
    // TODO: suspend fun clearDelivered(alertId: String)
}