# NightWatch — Builder's Guide

Hands-on reference for setting up the project and implementing each module. For *what* to
build and in what order, see [`IMPLEMENTATION_GUIDE.md`](IMPLEMENTATION_GUIDE.md). For the
system design, see [`ARCHITECTURE.md`](ARCHITECTURE.md).

---

## 1. Environment setup

| Tool | Version | Notes |
|---|---|---|
| Android Studio | latest stable | bundled JDK or JDK 17 |
| JDK | 17 | required by current AGP |
| Android SDK | 34+ | compileSdk/targetSdk 34, minSdk 26 |
| Gradle | via wrapper | never commit a hand-managed Gradle |
| Physical device | required | NPU/DSP, real camera, low-light behavior, thermals |

Do **not** develop CV or thermals on an emulator. Emulators have no meaningful camera
pipeline or thermal behavior, which invalidates P1/P3/P5 tuning.

---

## 2. Gradle / module structure

```
settings.gradle.kts
build.gradle.kts
gradle/libs.versions.toml
app/                       # monitor: camera pipeline, fusion, escalation
contact/                   # receiver app (Phase 7)
core/
  model/                   # BabyState, HazardDetection, AlertEvent, AlertTier
  common/                  # logging, Result types, coroutine utils
perception/                # baby, posture, presence, breathing, hazard
sensing/                   # frame source, audio, imu, ambient light
fusion/                    # tier classifier + state machine + calibration
transport/                 # Transport interface + implementations
```

Key `app/build.gradle.kts` fragments:

```kotlin
android {
    compileSdk = 34
    defaultConfig { minSdk = 26; targetSdk = 34 }
    buildFeatures { compose = true }
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":perception"))
    implementation(project(":sensing"))
    implementation(project(":fusion"))
    implementation(project(":transport"))

    implementation("org.tensorflow:tensorflow-lite:2.16.1")
    implementation("org.tensorflow:tensorflow-lite-gpu:2.16.1")
    implementation("org.tensorflow:tensorflow-lite-support:0.4.4")
    implementation("androidx.camera:camera-core:1.3.4")
    implementation("androidx.camera:camera-camera2:1.3.4")
    implementation("androidx.camera:camera-lifecycle:1.3.4")
    implementation("androidx.security:security-crypto:1.1.0-alpha06")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")
}
```

Select the NNAPI / vendor NPU delegate at runtime where available; fall back to GPU then
CPU, and log which delegate is active.

---

## 3. Package structure

```
com.nightwatch.app
  NightWatchApplication
  ui/            ArmingScreen, CalibrationScreen, PermissionFlow, DebugOverlay
  service/       MonitoringService (foreground), BootReceiver
  di/            dependency graph

com.nightwatch.sensing
  camera/        FrameSource, Frame, FrameThrottle
  audio/         CryClassifier, AudioRingBuffer, AudioCapture
  imu/           MountMonitor
  light/         AmbientDimController

com.nightwatch.perception
  baby/          BabyDetector, BabyTracker
  posture/       PostureClassifier
  presence/      PresenceTracker, CribBoundary
  breathing/     BreathingEstimator
  hazard/        HazardDetector, HazardWhitelist, HazardProximity

com.nightwatch.fusion
  SignalAggregator, TierClassifier, StateMachineImpl, CalibrationStore, SuppressionPolicy

com.nightwatch.transport
  Transport, TransportKind, TransportResult
  paired/        PairedDeviceTransport      // SDK TBD
  sms/           SmsTransport
  relay/         RelayTransport
  queue/         StoreAndForwardQueue

com.nightwatch.core.model
  BabyState, Posture, CryEvent, CryCategory, HazardDetection, MountEvent,
  AlertEvent, AlertTier, AlertPayload, TriggerState
```

---

## 4. Core interfaces

Implement against these; every one has a fake for tests and the debug harness.

```kotlin
// sensing
interface FrameSource {
    val frames: Flow<Frame>
    suspend fun start(); suspend fun stop()
}

interface CryClassifier {
    val events: Flow<CryEvent>
    suspend fun start(); suspend fun stop()
}

interface MountMonitor {
    val events: Flow<MountEvent>
}

// perception
interface BabyDetector {
    fun detect(frame: Frame): Detection?    // normalized bbox + confidence
}

interface PostureClassifier {
    fun classify(frame: Frame, baby: RectF): Posture
}

interface PresenceTracker {
    fun update(baby: RectF, boundary: Polygon): PresenceResult   // inCrib, edgeRisk
}

interface BreathingEstimator {
    fun update(window: List<Frame>, baby: RectF): BreathingResult?  // rate + confidence
}

interface HazardDetector {
    fun detect(frame: Frame): List<RawDetection>
}

interface HazardWhitelist {
    fun label(raw: RawDetection): String?   // null if not a hazard
    val version: String
}

// fusion
interface TierClassifier {
    fun tier(cry: CryEvent?, posture: Posture?, presence: PresenceResult?,
             hazard: HazardDetection?, mount: MountEvent?): AlertTier?
}

interface StateMachine {
    val state: StateFlow<TriggerState>
    fun onSignal(event: PerceptionSignal)
    fun arm(); fun disarm()
}

interface CalibrationStore {
    val cribBoundary: Polygon?
    val baselineBabySize: Float?
    suspend fun calibrate(emptyCribFrames: List<Frame>)
}

// transport
interface Transport {
    val kind: TransportKind
    suspend fun isReachable(): Boolean
    suspend fun send(payload: AlertPayload): TransportResult
}
```

Hazard proximity:

```kotlin
interface HazardProximity {
    fun score(hazard: RectF, baby: RectF): ProximityResult // proximity 0..1, touching
}
```

---

## 5. Integrating on-device models

1. Place models under `app/src/main/assets/models/` (`baby_detect.tflite`,
   `cry_cls.tflite`, `hazard_detect.tflite`, `posture.tflite`).
2. Load with `Interpreter` and a delegate:
   ```kotlin
   val options = Interpreter.Options().apply {
       setNumThreads(2)
       addDelegate(NnApiDelegate())    // prefer NPU/DSP where supported
   }
   ```
3. **Hazard detection**: use a COCO-pretrained lightweight detector (YOLO-nano / MobileNet
   SSD). Map model class indices through `HazardWhitelist` to filter to hazard labels.
   Do not train anything infant-specific for v1.
   ```kotlin
   val hazards = detector.detect(frame)
       .mapNotNull { raw -> whitelist.label(raw)?.let { it to raw } }
   ```
4. **Proximity**: compute IoU and normalized box-center distance between hazard and baby
   bboxes; `touching` when IoU > threshold or center distance < threshold. Keep the
   thresholds in one place and treat them as calibrated constants.
5. **Cry classification**: stream 16 kHz mono PCM windows through the classifier; map
   softmax output to `CryCategory`; debounce per episode.
6. **Breathing**: estimate chest-region motion with optical flow (e.g. Lucas-Kanade or
   phase-based) over a 15–30 s window; only emit a rate when confidence is high.
7. Keep inference off the main thread; use a dedicated dispatcher. Version models and log
   model hash + delegate at startup.

---

## 6. Permissions and services

`AndroidManifest.xml` essentials:

```xml
<uses-permission android:name="android.permission.CAMERA"/>
<uses-permission android:name="android.permission.RECORD_AUDIO"/>
<uses-permission android:name="android.permission.FOREGROUND_SERVICE"/>
<uses-permission android:name="android.permission.FOREGROUND_SERVICE_CAMERA"/>
<uses-permission android:name="android.permission.FOREGROUND_SERVICE_MICROPHONE"/>
<uses-permission android:name="android.permission.POST_NOTIFICATIONS"/>
<uses-permission android:name="android.permission.WAKE_LOCK"/>
<uses-permission android:name="android.permission.BLUETOOTH_CONNECT"/>
<uses-permission android:name="android.permission.BLUETOOTH_SCAN"/>

<service
    android:name=".service.MonitoringService"
    android:foregroundServiceType="camera|microphone"
    android:exported="false"/>
```

Notes:

- Start the foreground service from a visible context (arming screen), not a background
  broadcast, to satisfy Android 12+ restrictions.
- `foregroundServiceType="camera"` and `microphone` require the corresponding runtime
  permission before `startForeground`.
- The persistent notification must show monitoring status only — never a live feed.
- Keep the screen off and dim; do not acquire a display wake lock.

---

## 7. Debug harness

Build a `DebugSignalInjector` so fusion/tiering/escalation can be tested without real
subjects:

```kotlin
debug.emitCry(CryCategory.PAIN, confidence = 0.92f)
debug.emitPosture(Posture.COVERED)
debug.emitHazard(label = "cable", proximity = 0.81f, touching = true)
debug.emitMount(disturbance = 4.2f, moved = true)
```

Wire it to a debug-only menu with actions: `Force Tier 0/1/2`, `Force TAMPER`,
`Clear Suppression`. Also provide a **fake frame source** that plays prerecorded clips or
synthetic scenes so CV phases can be tested deterministically.

Log tags:

| Tag | Emits |
|---|---|
| `NightWatch.Frame` | fps, dropped frames, delegate |
| `NightWatch.Baby` | bbox, confidence |
| `NightWatch.Posture` | class + confidence |
| `NightWatch.Breathing` | rate + confidence + window length |
| `NightWatch.Hazard` | label, bbox, proximity, whitelist version |
| `NightWatch.Tier` | aggregated tier + reasons + suppression state |
| `NightWatch.Escalation` | transport attempts, queue state, delivery result |

Never log frames, audio, or imagery.

---

## 8. Testing strategy

| Layer | Approach |
|---|---|
| State machine / tiering | pure unit tests over all tier transitions + suppression |
| Proximity | table-driven geometry tests (overlap, near, far) |
| Whitelist | unit tests mapping raw classes to hazard labels |
| Detectors | recorded/synthetic fixtures; precision/recall offline |
| Breathing | tolerance-band tests on reference clips |
| Escalation | fake `Transport`s: success, timeout, offline, partial |
| Queue | process-death simulation, backoff, de-duplication |
| End-to-end | debug injector drives a real device to a real receiver app |

Benchmark sets:

- **Cry:** Donate-a-Cry, Baby Chillanto + own ambient recordings (white noise, TV).
- **Hazard:** a room set with cable/bag/bottle props at varying distances.
- **Posture:** doll with blanket variations, prone/supine/side, partial occlusion.
- **Night:** dark-room recording to evaluate degradation behavior.

Treat false positives per tier as a first-class metric.

---

## 9. Common pitfalls

- **Testing CV on an emulator.** Invalid; use a device.
- **Letting the screen emit light.** Confirm the display is off/dim at night.
- **Writing frames to disk.** The pipeline must be RAM-only outside opted-in clips.
- **Alerting on breathing alone.** Never; confidence-gate and cross-check posture.
- **Re-alerting on the same persistent hazard.** Use per-object suppression.
- **Doing grasping detection.** Out of scope; use bbox proximity/overlap.
- **Assuming bright conditions.** Gate CV on ambient light and degrade to audio + IMU.
- **Bypassing the `Transport` interface** before the paired-device SDK is defined.
- **Leaving `WAKE_LOCK` held after disarm.** Release it in service teardown.

---

## 10. Definition of done (per module)

A module is done when: it compiles, has unit tests for its pure logic, exposes a fake for
integration tests, logs through its tag, writes nothing to disk unexpectedly, and its
acceptance criteria in [`IMPLEMENTATION_GUIDE.md`](IMPLEMENTATION_GUIDE.md) pass on a
physical device.