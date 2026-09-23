package com.nightwatch.core.common

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Injected dispatchers so every module is testable (no hardcoded Dispatchers.IO).
 *
 * REQUIRED CONTENT:
 * - A default provider backed by Dispatchers.IO / Default / Main.
 * - A test provider using a TestDispatcher.
 */
interface DispatcherProvider {
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
    val main: CoroutineDispatcher
}

/**
 * Sealed result type for operations that can fail without throwing.
 *
 * REQUIRED CONTENT:
 * - Success<T>, Failure(cause), and map/flatMap helpers.
 * - Used by Transport.send and calibration operations.
 */
sealed interface Outcome<out T> {
    data class Success<T>(val value: T) : Outcome<T>
    data class Failure(val cause: Throwable) : Outcome<Nothing>
}