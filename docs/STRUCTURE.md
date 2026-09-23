# NightWatch — Repository Structure

Auto-generated tree of the scaffolded project. Every file is a skeleton with a comment
describing its required contents; see `docs/BUILDERS_GUIDE.md` for implementation detail.

```
NightWatch/
├── .gitignore
├── .gitattributes
├── README.md
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── gradle/
│   ├── libs.versions.toml
│   └── wrapper/
│       ├── gradle-wrapper.properties
│       └── README.md
├── docs/
│   ├── ARCHITECTURE.md
│   ├── BUILDERS_GUIDE.md
│   ├── IMPLEMENTATION_GUIDE.md
│   └── STRUCTURE.md
├── core/
│   ├── model/            shared data shapes (no dependencies)
│   └── common/           logging, dispatchers, Outcome
├── sensing/              camera, audio, IMU, ambient light (hardware access)
├── perception/           baby, posture, presence, breathing, hazard (CV)
├── fusion/               aggregator, tier classifier, state machine, calibration
├── transport/            Transport interface, paired/SMS/relay, retry queue
├── app/                  monitor app: UI, service, pipeline, DI
└── contact/              caregiver receiver app
```

## Module dependency direction

```
app ─► fusion ─► core:model
 │       │
 │       └─► core:common
 ├─► perception ─► sensing ─► core:{model,common}
 └─► transport ─► core:{model,common}
contact ─► transport, core:{model,common}
```

Rules:

- `core:model` has **no** dependencies (safe to share with the receiver).
- `sensing` owns all hardware access; `perception` consumes frames; `fusion` owns policy.
- `transport` is the **only** module that touches the network.
- `app`/`contact` are the only Android application modules.