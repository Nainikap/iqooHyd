# NightWatch — Implementation Guide

A phase-by-phase roadmap from empty repo to a working multi-sensor crib companion with
tiered alerts and on-device hazard detection. Each phase lists deliverables, interfaces,
and acceptance criteria to verify on a physical device before moving on.

- Design reference: [`ARCHITECTURE.md`](ARCHITECTURE.md)
- Hands-on setup / API details: [`BUILDERS_GUIDE.md`](BUILDERS_GUIDE.md)

**Build order rule:** never start a phase until the previous phase's acceptance criteria
pass on a physical device. CV thresholds depend on stable calibration and stable framing.

---

## Phase overview

| Phase | Goal | Ships |
|---|---|---|
| P0 | Skeleton, arming, dim mode, state machine | monitor shell, no inference |
| P1 | Camera pipeline + baby bbox + calibration | tracked baby, crib boundary |
| P2 | Cry classification | Tier 0 silent buzz |
| P3 | Posture + presence/rollover | Tier 1 urgent alerts |
| P4 | Breathing estimation | approximate, confidence-gated |
| P5 | Hazard detection + proximity | Tier 2 critical alert |
| P6 | IMU mount tamper | tamper alerts |
| P7 | Escalation + transports + receiver | alerts leave the device |
| P8 | Hardening | power, thermals, calibration, tests |

---

## P0 — Skeleton, arming, dim mode, state machine

**Deliverables**
- Android app module (`:app`), Kotlin, min SDK 26, target SDK 34.
- Package skeleton per `BUILDERS_GUIDE.md` §3.
- `AlertTier` enum, `TriggerState` enum, and `StateMachine` from ARCHITECTURE §4.
- Foreground monitoring service (`MonitoringService`) with persistent notification.
- Arming flow + runtime permission flow (camera, mic, notifications, wake lock).
- Ambient-light-driven dim mode: screen off/dim near the crib.
- Encrypted local store stub + `CalibrationStore` interface.

**Interfaces introduced**
```kotlin
enum class AlertTier { ROUTINE, URGENT, CRITICAL }
enum class TriggerState { UNARMED, MONITORING, ROUTINE, URGENT, CRITICAL, TAMPER }

interface StateMachine {
    val state: StateFlow<TriggerState>
    fun onSignal(event: PerceptionSignal)
    fun arm(); fun disarm()
}
```

**Acceptance criteria**
- [ ] App arms/disarms; state survives process death.
- [ ] Foreground service stays alive 30 min with the screen off.
- [ ] Screen dims according to ambient light and never emits visible light at night.
- [ ] Illegal tier transitions rejected and logged.

---

## P1 — Camera pipeline, baby detection, calibration

**Deliverables**
- `FrameSource` at ~5–10 fps, screen off, no disk writes.
- `BabyDetector` producing a normalized `bbox` (person/baby detection + tracking).
- Framing/calibration onboarding: capture the empty crib, the crib boundary polygon, and
  the nominal baby region → `CalibrationStore`.
- `PerceptionSignal` plumbing into the state machine (no alerts yet).
- Debug overlay showing baby bbox + crib polygon.

**Interfaces introduced**
```kotlin
interface BabyDetector {
    fun detect(frame: Frame): Detection?   // bbox + confidence
}

interface CalibrationStore {
    val cribBoundary: Polygon?
    val baselineBabySize: Float?
    suspend fun calibrate(emptyCribFrames: List<Frame>)
}
```

**Acceptance criteria**
- [ ] Baby bbox stays stable (low jitter) across a 5-min session.
- [ ] Works with the screen off and no visible light emitted.
- [ ] Calibration is persisted and restored.
- [ ] Frame pipeline writes nothing to disk (verified via file-system tracing).

---

## P2 — Cry classification → Tier 0

**Deliverables**
- `CryClassifier` on-device (NPU/DSP): categories hungry / pain / discomfort / normal.
- Audio ring buffer (~10 s RAM only), no persistence.
- Debounce per cry episode; route to **Tier 0** (silent buzz).
- Allowlist/tuning for ambient noise (TV, white noise machine, traffic).

**Acceptance criteria**
- [ ] Each cry category predicts with a recorded confidence on held-out samples.
- [ ] Tested against Donate-a-Cry / Baby Chillanto recordings offline.
- [ ] Ambient noise for 10 min produces no Tier 0 alert.
- [ ] Cry → silent buzz within 1 s, fully offline.
- [ ] Ring buffer verifiably RAM-only on non-escalated events.

---

## P3 — Posture and presence/rollover → Tier 1

**Deliverables**
- `PostureClassifier`: SUPINE / PRONE / SIDE / COVERED / UNKNOWN, using the shared baby
  bbox.
- `PresenceTracker`: in-frame / still in crib; `edgeRisk` relative to the calibrated crib
  boundary.
- Tiering policy for **Tier 1** (face-down/covered, edge rollover) with a persistence
  requirement (condition must hold briefly to suppress flicker).
- Suppression window while a condition persists.

**Acceptance criteria**
- [ ] Covering the doll's face/head fires `COVERED` and Tier 1 within 3 s.
- [ ] Rollover to the crib edge fires Tier 1 within 3 s.
- [ ] Normal sleeping motion does not fire Tier 1 for 30 min.
- [ ] Fully occluded baby → `UNKNOWN` → caregiver prompt, never a silent pass.

---

## P4 — Breathing estimation

**Deliverables**
- `BreathingEstimator`: chest-motion optical flow over ~15–30 s stable windows.
- Confidence-gated output; cross-checked against posture.
- Shown as an approximate rate; **never alerts alone**.
- Degradation when motion/swaddle invalidates the window.

**Acceptance criteria**
- [ ] Rate within a tolerance band of a reference measurement on a cooperative subject.
- [ ] Output carries `breathingConfidence`; low confidence suppresses display.
- [ ] Posture change invalidates the window rather than emitting a stale rate.
- [ ] No alert is ever raised on breathing alone.

---

## P5 — Hazard detection + proximity → Tier 2

**Deliverables**
- `HazardDetector`: COCO-pretrained lightweight detector running every N frames,
  filtered to a versioned hazard whitelist (cable, bag, bottle, small objects, etc.).
- `HazardProximity`: compute overlap/proximity between hazard bbox and baby bbox;
  `babyTouching` above a contact threshold. **No grasping detection.**
- Tier **2** routing: immediate high-priority alert, bypassing the routine path.
- Per-object suppression: no re-alert while the same object persists; re-alert on a new
  object.

**Acceptance criteria**
- [ ] A cable prop placed next to the doll fires Tier 2 within 1 s.
- [ ] A hazard object far from the baby does **not** fire.
- [ ] No re-alert loop while the same hazard persists.
- [ ] Handler runs fully on-device with no network in the critical path.
- [ ] Whitelist is versioned and independently testable.

> This is the differentiating phase. Scope it to proximity/overlap, not grasping.

---

## P6 — IMU mount tamper

**Deliverables**
- `MountMonitor`: IMU anomaly detection for bumps plus baseline-angle shift detection.
- `TAMPER` state + alert; requires re-calibration after a move.
- Reuse the calibration flow (does the crib boundary need re-capture? prompt the user).

**Acceptance criteria**
- [ ] Bumping/moving the mount fires TAMPER within 2 s.
- [ ] Normal vibration (a fan, a door) does not fire.
- [ ] After a move, the app prompts re-calibration and blocks CV alerts until done.

---

## P7 — Escalation, transports, and receiver

**Deliverables**
- `AlertBuilder` producing `AlertPayload` per tier with reasons.
- `Transport` interface + implementations: `PairedDeviceTransport` (**SDK TBD**),
  `SmsTransport` fallback, optional `RelayTransport`.
- Store-and-forward queue with backoff and `alertId` de-duplication.
- Payload signing (device key) + encryption to contact key.
- Trusted-contact receiver app module (`:contact`): tier-colored alert, reasons, optional
  opted-in clip, de-dup.

**Acceptance criteria**
- [ ] Tier 0/1/2 alerts reach a reachable paired device within SLA.
- [ ] Tier 2 does not wait on the transport before state changes locally.
- [ ] Offline primary transport → SMS fallback + queued payload; delivers on reconnect.
- [ ] Tampered payload rejected by receiver.
- [ ] Duplicate deliveries do not duplicate alerts.

> **Open item:** the paired-device transport SDK is undefined. Implement `Transport` plus
> a fake loopback adapter first so P7 can proceed.

---

## P8 — Hardening

**Deliverables**
- Power/thermal profiling across tiers; publish a measured budget for an 8-hour session.
- Frame-rate and hazard-cadence tuning under thermal throttling.
- Calibration UX polish; re-calibration triggers.
- Key rotation + revocation.
- Test matrix: crib vs. toddler room, dark vs. IR, doll vs. real subject, occlusion cases.
- Red-team pass: can an alert be forged, replayed, suppressed, or can frames be
  exfiltrated by a malicious app?

**Acceptance criteria**
- [ ] Documented battery/thermal behavior over 8 hours.
- [ ] False-positive/negative counts on a benchmark set for each tier.
- [ ] Key rotation verified end-to-end.
- [ ] Red-team findings triaged with fixes or accepted risks.

---

## Cross-cutting engineering rules

- **On-device first:** no cloud round-trip in any detection or tiering path.
- **Privacy default:** frames and audio never leave or persist; clips are opt-in.
- **One baby bbox:** all CV consumers share the tracked bbox.
- **Testability:** every detector behind an interface with a fake implementation.
- **Logging:** tagged (`NightWatch.Frame`, `.Baby`, `.Posture`, `.Breathing`, `.Hazard`,
  `.Tier`, `.Escalation`); never log frames, audio, or imagery.
- **Safety honesty:** breathing is approximate; never alert on it alone.

---

## Risks and mitigations

| Risk | Impact | Mitigation |
|---|---|---|
| Low-light CV quality | core feature | IR/night path or degrade to audio + IMU (gap 1) |
| Soft-object/occlusion detection | posture accuracy | treat as occlusion class; persistence gating |
| Continuous CV thermal load | device health | tiered fps, hazard every N frames, thermal degradation |
| Hazard false positives | alert fatigue | proximity/overlap only + per-object suppression |
| Paired-device SDK undefined | blocks P7 | `Transport` + fake adapter first |
| Privacy/consent | launch blocker | no cloud, no live-view, opt-in clips only |