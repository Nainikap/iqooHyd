package com.nightwatch.core.model

/** Alert severity tiers. Precedence: CRITICAL > URGENT > ROUTINE. */
enum class AlertTier {
    /** Routine cry or stir -> silent buzz to the paired device. */
    ROUTINE,

    /** Face-covered / edge rollover -> urgent alert. */
    URGENT,

    /** Hazard object within proximity of the baby -> immediate high-priority alert. */
    CRITICAL,
}

/** Fine-grained monitor state used for UI and suppression logic. */
enum class TriggerState {
    UNARMED,
    MONITORING,
    ROUTINE,
    URGENT,
    CRITICAL,
    TAMPER,
}

/**
 * A fully-formed alert ready for transport.
 *
 * REQUIRED CONTENT:
 * - id, tier, reasons, payload, createdAt.
 * - schemaVersion (add on first release; receiver updates independently).
 *
 * TODO: add `schemaVersion: Int` before the first release.
 */
data class AlertEvent(
    val id: String,
    val tier: AlertTier,
    val reasons: Set<String>,
    val payload: AlertPayload,
    val createdAt: Long,
)

/**
 * Signed, encrypted payload delivered to the trusted contact.
 *
 * REQUIRED CONTENT:
 * - Every optional field must tolerate null (partial failures must not block an alert).
 * - clipSnippet is opt-in and null by default; never populated implicitly.
 * - signature is produced with the device key (see transport module).
 */
data class AlertPayload(
    val alertId: String,
    val tier: AlertTier,
    val sentAt: Long,
    val babyState: BabyState?,
    val hazard: HazardDetection?,
    val cryEvent: CryEvent?,
    val clipSnippet: ByteArray?,
    val signature: ByteArray,
) {
    // TODO: equals/hashCode that respects ByteArray fields (or use a wrapper type).
}