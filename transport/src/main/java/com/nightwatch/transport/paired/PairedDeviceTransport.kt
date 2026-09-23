package com.nightwatch.transport.paired

import com.nightwatch.core.model.AlertPayload
import com.nightwatch.transport.Transport
import com.nightwatch.transport.TransportKind
import com.nightwatch.transport.TransportResult

/**
 * Primary delivery to the caregiver's paired device.
 *
 * OPEN ITEM: the concrete SDK/protocol is UNDEFINED. Do not call any vendor SDK
 * directly from the escalation layer; everything goes through Transport.
 *
 * IMPLEMENTATION PLAN:
 * 1. Ship a FakePairedDeviceTransport (in-memory loopback) so P7 can proceed.
 * 2. Add a `SdkPairedDeviceTransport` adapter once the SDK is known, implementing
 *    isReachable() from the SDK's connection state and send() from its publish call.
 * 3. Isolate all SDK types behind this class so nothing else imports the SDK.
 */
class PairedDeviceTransport(
    // TODO: inject the SDK client / pairing registry.
) : Transport {

    override val kind: TransportKind = TransportKind.PAIRED_DEVICE

    override suspend fun isReachable(): Boolean {
        // TODO: return the SDK's current paired-device connection state.
        TODO("Implement when the paired-device transport SDK is defined.")
    }

    override suspend fun send(payload: AlertPayload): TransportResult {
        // TODO: publish the encrypted, signed payload; map SDK errors to
        //       Retryable vs Permanent.
        TODO("Implement when the paired-device transport SDK is defined.")
    }
}

/**
 * In-memory stand-in used by tests and by P7 development before the SDK exists.
 * REQUIRED CONTENT: configurable reachability + failure injection (offline, timeout).
 */
class FakePairedDeviceTransport(
    // TODO: var reachable: Boolean, var failWith: Throwable?
) : Transport {
    override val kind: TransportKind = TransportKind.PAIRED_DEVICE
    // TODO: override suspend fun isReachable() = reachable
    // TODO: override suspend fun send(payload): TransportResult
}