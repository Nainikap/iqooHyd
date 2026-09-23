package com.nightwatch.sensing.audio

import com.nightwatch.core.model.CryEvent

/**
 * On-device cry classifier (NPU/DSP preferred).
 *
 * REQUIRED CONTENT:
 * - Continuous audio in fixed windows (e.g. 16 kHz mono PCM16, 40 ms frames).
 * - Model: cry_cls.tflite -> softmax over CryCategory.
 * - Episode debouncing: emit one CryEvent per episode, not per window.
 * - Ambient-noise rejection (TV, white noise machine, traffic).
 * - Must run without the main CPU (verify via profiler).
 */
interface CryClassifier {
    // TODO: val events: Flow<CryEvent>
    // TODO: suspend fun start()
    // TODO: suspend fun stop()
    // TODO: fun setConfidenceThreshold(threshold: Float)
}

/**
 * Fixed-size RAM-only ring buffer of recent audio.
 *
 * REQUIRED CONTENT:
 * - Capacity ~10 s.
 * - Never persisted unless an escalated, opted-in clip is requested.
 * - Thread-safe write from the audio thread, snapshot read for clip extraction.
 */
interface AudioRingBuffer {
    // TODO: fun write(samples: ShortArray)
    // TODO: fun snapshotLast(millis: Long): ShortArray
    // TODO: fun clear()
}

/**
 * Microphone capture feeding the classifier and ring buffer.
 *
 * REQUIRED CONTENT:
 * - Uses AudioRecord with the correct sample rate/channel config.
 * - Handles audio-focus interruptions (calls, assistants) and resumes.
 * - No disk writes.
 */
interface AudioCapture {
    // TODO: val samples: Flow<ShortArray>
    // TODO: suspend fun start()
    // TODO: suspend fun stop()
}