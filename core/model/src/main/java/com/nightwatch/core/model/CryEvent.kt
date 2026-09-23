package com.nightwatch.core.model

/** Cry categories produced by the on-device classifier. */
enum class CryCategory {
    HUNGRY,
    PAIN,
    DISCOMFORT,
    NORMAL,
}

/**
 * A classified cry episode.
 *
 * REQUIRED CONTENT:
 * - id (stable, for de-duplication), category, confidence, startedAt.
 * - endedAt (nullable) so episodes can be debounced until they resolve.
 */
data class CryEvent(
    val id: String,
    val category: CryCategory,
    val confidence: Float,
    val startedAt: Long,
    val endedAt: Long?,
)