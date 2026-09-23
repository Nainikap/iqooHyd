package com.nightwatch.sensing.camera

import com.nightwatch.core.model.Rect

/**
 * A single camera frame handed to the perception layer.
 *
 * REQUIRED CONTENT:
 * - A pixel buffer reference (e.g. ImageProxy or a decoded Bitmap) + rotation.
 * - width/height and a monotonic timestamp.
 * - A release() to return the buffer to the camera pool; frames are borrowed, not owned.
 *
 * HARD RULE: never persist this to disk.
 */
data class Frame(
    // TODO: val image: ImageProxy
    val width: Int,
    val height: Int,
    val rotationDegrees: Int,
    val timestamp: Long,
) {
    // TODO: fun toNormalized(rect: Rect): Rect
    // TODO: fun release()
}

/**
 * Streams camera frames at a configurable cadence with the screen off.
 *
 * REQUIRED CONTENT:
 * - Flow<Frame> and start()/stop().
 * - Runtime frame-rate throttling driven by the power tier (P1/P8).
 * - No disk writes; frames are dropped when no consumer is keeping up.
 * - Automatic re-acquire after camera interruptions (calls, other apps).
 */
interface FrameSource {
    // TODO: val frames: Flow<Frame>
    // TODO: suspend fun start()
    // TODO: suspend fun stop()
    // TODO: fun setTargetFps(fps: Int)
}

/**
 * Drops/decimates frames to hit a target cadence.
 *
 * REQUIRED CONTENT:
 * - shouldEmit(timestamp): Boolean based on target interval.
 * - Separate cadences for posture (every frame) and hazard (every N frames).
 */
class FrameThrottle(private val targetFps: Int) {
    // TODO: fun shouldEmit(timestamp: Long): Boolean
    // TODO: fun everyNth(n: Int): Boolean
}