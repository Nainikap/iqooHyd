package com.nightwatch.transport.relay

import com.nightwatch.core.model.AlertPayload
import com.nightwatch.transport.Transport
import com.nightwatch.transport.TransportKind
import com.nightwatch.transport.TransportResult

/**
 * Optional server-relayed fallback (ARCHITECTURE §8 open item).
 *
 * DECISION PENDING: whether a relay backend is in scope at all. If included:
 * - The server must only forward opaque, encrypted payloads (no plaintext, no video).
 * - Auth via per-device credentials stored in secrets.properties (never committed).
 * - The receiver app polls on foreground if push is unavailable.
 *
 * REQUIRED CONTENT:
 * - Base URL + auth token injection (BuildConfig / DI, not hardcoded).
 * - Retry/backoff handled by StoreAndForwardQueue, not here.
 */
class RelayTransport(
    // TODO: inject base URL + HTTP client + device credentials.
) : Transport {

    override val kind: TransportKind = TransportKind.RELAY

    override suspend fun isReachable(): Boolean {
        // TODO: lightweight health check against the relay.
        TODO("Implement if the relay backend is in scope.")
    }

    override suspend fun send(payload: AlertPayload): TransportResult {
        // TODO: POST the encrypted payload; map HTTP errors to Retryable/Permanent.
        TODO("Implement if the relay backend is in scope.")
    }
}