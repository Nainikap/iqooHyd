package com.nightwatch.transport

import com.nightwatch.core.model.AlertEvent
import com.nightwatch.core.model.AlertPayload

/**
 * Assembles a signed, encrypted AlertPayload from an AlertEvent.
 *
 * REQUIRED CONTENT:
 * - Pull fused location (last-known acceptable; include accuracy).
 * - Pull battery/device state (cheap, must not block).
 * - Sign with the device key; encrypt to each contact's public key.
 * - OPTIONAL clipSnippet only when the user opted in AND the event escalated.
 */
interface AlertBuilder {
    // TODO: suspend fun build(event: AlertEvent): AlertPayload
}

/**
 * Signing/encryption for payloads and keys.
 *
 * REQUIRED CONTENT:
 * - Device key generation/storage in the Android Keystore (hardware-backed if present).
 * - sign(payload) and verify(payload) used by the receiver.
 * - Encrypt to a contact public key; key rotation on re-pair + revocation list.
 */
interface PayloadCrypto {
    // TODO: fun sign(bytes: ByteArray): ByteArray
    // TODO: fun verify(bytes: ByteArray, signature: ByteArray): Boolean
    // TODO: fun encryptForContact(contactId: String, bytes: ByteArray): ByteArray
}