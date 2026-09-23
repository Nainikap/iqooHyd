package com.nightwatch.transport.sms

import com.nightwatch.core.model.AlertPayload
import com.nightwatch.transport.Transport
import com.nightwatch.transport.TransportKind
import com.nightwatch.transport.TransportResult

/**
 * Fallback delivery over carrier SMS when the paired device is unreachable.
 *
 * REQUIRED CONTENT:
 * - Compact payload (SMS length limits): alertId, tier, reasons, short location, link.
 * - Uses SmsManager; requires SEND_SMS permission (request in app module).
 * - Confirm the payload fits; truncate/link-out rather than splitting silently.
 * - Carrier cost/latency must be surfaced in settings.
 */
class SmsTransport(
    // TODO: inject SmsManager + trusted contact phone numbers.
) : Transport {

    override val kind: TransportKind = TransportKind.SMS

    override suspend fun isReachable(): Boolean {
        // TODO: telephony capability + permission + signal check.
        TODO("Implement SMS reachability check.")
    }

    override suspend fun send(payload: AlertPayload): TransportResult {
        // TODO: build the compact text and send; map errors to Retryable/Permanent.
        TODO("Implement SMS fallback send.")
    }
}