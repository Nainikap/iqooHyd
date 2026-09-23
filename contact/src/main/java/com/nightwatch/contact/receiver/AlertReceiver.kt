package com.nightwatch.contact.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * Receives raw alerts from the transport (paired device / relay / push).
 *
 * REQUIRED CONTENT:
 * - De-duplicate by AlertPayload.alertId (at-least-once delivery means duplicates).
 * - Verify signature -> decrypt -> parse -> persist via AlertStore.
 * - Post a notification; CRITICAL uses a high-priority channel.
 * - Drop and log payloads that fail verification.
 *
 * HARD RULE: never trust an unsigned/unverified payload.
 */
class AlertReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        // TODO: extract bytes -> PayloadVerifier.verify -> AlertStore.save -> notify
    }
}