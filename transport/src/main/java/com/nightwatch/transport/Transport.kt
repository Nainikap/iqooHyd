package com.nightwatch.transport

import com.nightwatch.core.model.AlertPayload

/** Delivery channels, tried in order of reachability/cost. */
enum class TransportKind {
    PAIRED_DEVICE,
    SMS,
    RELAY,
}

/**
 * Result of a send attempt.
 *
 * REQUIRED CONTENT:
 * - Delivered (with optional receipt id), Retryable(cause), Permanent(cause).
 * - A permanent failure must not spin the retry queue forever.
 */
sealed interface TransportResult {
    data class Delivered(val receiptId: String? = null) : TransportResult
    data class Retryable(val cause: Throwable) : TransportResult
    data class Permanent(val cause: Throwable) : TransportResult
}

/**
 * A single alert delivery channel.
 *
 * CONTRACT:
 * - MUST NOT be called from the tiering critical path synchronously; Tier 2 state
 *   changes locally first, delivery is attempted asynchronously (ARCHITECTURE §10).
 * - All implementations are at-least-once; receiver de-dupes by alertId.
 */
interface Transport {
    val kind: TransportKind

    /** Cheap reachability probe so the escalation layer can pick a channel. */
    suspend fun isReachable(): Boolean

    /** Attempt delivery once. Never throws; report failure via TransportResult. */
    suspend fun send(payload: AlertPayload): TransportResult
}