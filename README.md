# NightWatch

**A multi-sensor crib companion that watches sleep *and* the hazards that come after it.**

NightWatch mounts a phone near the crib, screen off and dim. It listens for cries and
classifies them, watches the baby's posture, presence, and breathing through the camera,
detects if the mount itself is bumped — and, once the baby is mobile, runs on-device
object detection to flag hazard objects (cables, bags, small items) near the child.

Everything runs on-device. No cloud round-trip, no video leaves the phone.

---

## The USP

> Most baby monitors stop at cry and motion detection. NightWatch is an **active safety
> monitor** that keeps working once the baby is mobile — tiered alerts plus real-time,
> on-device hazard-object flagging, which a cloud API is too slow to do reliably.

---

## What it does

| Sensor | Signal | Output |
|---|---|---|
| Mic | On-device cry classifier | hungry / pain / discomfort / normal |
| Camera | Sleep posture | face-down, face covered by blanket/soft object |
| Camera | Presence & rollover | in-frame, still in crib, rolled to edge |
| Camera | Breathing rate | chest-motion optical flow (posture cross-checked) |
| Camera | Hazard-object interaction | hazard class near baby bbox → high-priority alert |
| IMU | Mount tamper / fall | phone moved or knocked off its mount |
| Ambient light | Auto dim / dark mode | screen stays dark near the crib |

---

## Alert tiers

| Tier | Trigger | Delivery |
|---|---|---|
| 0 — Routine | cry or stir | silent buzz to paired device |
| 1 — Urgent | face-covered, edge rollover | urgent alert |
| 2 — Critical | hazard-object contact (near/holding) | immediate high-priority alert |

Tier 2 is different in kind: hazard contact is time-sensitive, so it bypasses the
routine alert path entirely.

```
 [Cry classifier] ─┐
 [Posture]        ─┤
 [Presence/roll]  ─┼─► [Fusion & Tiering Engine]
 [Breathing]      ─┤         │
 [Hazard detect]  ─┤         ├─► Tier 0 → silent buzz
 [IMU tamper]     ─┘         ├─► Tier 1 → urgent alert
                             └─► Tier 2 → critical alert
                                         │
                                [Transport] → paired device
```

Full detail: [`docs/ARCHITECTURE.md`](docs/ARCHITECTURE.md).

---

## Why the hazard layer is feasible

No infant-specific dataset is needed. Hazard detection maps onto general-purpose object
detectors (COCO-pretrained YOLO / MobileNet variants already detect `cable`, `bottle`,
`bag`, and similar classes). The work is **filtering detections to a hazard whitelist**
and measuring overlap/proximity between the hazard bounding box and the tracked baby
bounding box — standard CV, no training.

Scope is deliberately conservative: flag **proximity/overlap**, not fine-grained
hand-object grasping.

Cry classification uses established datasets: **Donate-a-Cry**, **Baby Chillanto**.

---

## Repository map

```
README.md
docs/
  ARCHITECTURE.md          # system design, frame pipeline, data model, tiers, gaps
  IMPLEMENTATION_GUIDE.md  # phased build roadmap with acceptance criteria
  BUILDERS_GUIDE.md        # env setup, module APIs, model integration, debugging
```

---

## Quick start

> Scaffolding is described in the implementation guide.

```bash
./gradlew :app:assembleDebug
./gradlew :app:installDebug
```

Requirements: Android Studio (latest stable), JDK 17, Android SDK 34+, and a physical
device with an NPU/DSP. Emulators cannot validate continuous camera, low-light CV, or
thermal behavior.

Permissions:

- `CAMERA` — posture, presence, breathing, hazard detection.
- `RECORD_AUDIO` — cry classification.
- `FOREGROUND_SERVICE` / `FOREGROUND_SERVICE_CAMERA` / `FOREGROUND_SERVICE_MICROPHONE`
  — always-on monitoring with the screen off.
- `POST_NOTIFICATIONS` — monitoring status + alert mirror.
- `BLUETOOTH_CONNECT` / `BLUETOOTH_SCAN` — paired-device transport.
- `WAKE_LOCK` — keep the camera pipeline running (bounded, thermal-aware).

---

## Live demo script

1. Mount the phone near a crib; screen auto-dims.
2. Play a cry sample → Tier 0 silent buzz on the paired device.
3. Cover the doll's face with a blanket → Tier 1 urgent alert.
4. Place a "cable" prop next to the doll → Tier 2 critical alert, near-instant.
5. Bump the mount → tamper alert.
6. Show the on-device profiler: no network, NPU active, frames never stored.

---

## Status

Early design / scaffold phase. See [`docs/IMPLEMENTATION_GUIDE.md`](docs/IMPLEMENTATION_GUIDE.md).

---

## Safety disclaimer

NightWatch is a supplementary aid. It is **not** a medical device and does **not**
prevent SIDS or guarantee detection of every event. Optical-flow breathing estimation is
approximate and can fail under blankets, swaddles, or motion. Never rely on it in place
of safe-sleep practices or direct supervision.