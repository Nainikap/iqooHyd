package com.nightwatch.transport

/**
 * Dependency-injection entry point for the transport layer.
 *
 * REQUIRED CONTENT:
 * - Providers for AlertBuilder, PayloadCrypto, all Transport implementations, and the
 *   StoreAndForwardQueue.
 * - An EscalationCoordinator that picks the transport order and enqueues on failure.
 *   (Place the coordinator here so no app/UI code touches transport internals.)
 */
object TransportModule {
    // TODO: fun alertBuilder(context: Context): AlertBuilder
    // TODO: fun payloadCrypto(context: Context): PayloadCrypto
    // TODO: fun pairedDevice(context: Context): Transport
    // TODO: fun sms(context: Context): Transport
    // TODO: fun relay(context: Context): Transport
    // TODO: fun queue(context: Context): StoreAndForwardQueue
}

/**
 * Orchestrates escalation for an AlertEvent.
 *
 * REQUIRED CONTENT:
 * - Build payload -> try transports in order -> enqueue on failure.
 * - Runs off the critical path; StateMachine transitions happen before delivery.
 */
interface EscalationCoordinator {
    // TODO: suspend fun escalate(event: com.nightwatch.core.model.AlertEvent)
}