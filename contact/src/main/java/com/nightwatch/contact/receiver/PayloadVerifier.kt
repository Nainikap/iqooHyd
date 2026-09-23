package com.nightwatch.contact.receiver

import com.nightwatch.core.model.AlertPayload

/**
 * Verifies and decrypts incoming payloads.
 *
 * REQUIRED CONTENT:
 * - Verify the sender's signature against the paired device's public key.
 * - Decrypt with the receiver's private key (Android Keystore).
 * - Return a typed failure for tampered/unknown-sender payloads; caller drops them.
 * - Handle key rotation and revoked contacts.
 *
 * ACCEPTANCE (P7): a tampered payload is rejected.
 */
interface PayloadVerifier {
    // TODO: fun verifyAndDecrypt(bytes: ByteArray): Result<AlertPayload>
}