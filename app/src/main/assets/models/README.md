# NightWatch on-device models

Place TFLite / ONNX model binaries here. They are intentionally git-ignored by
default (see the bottom of `.gitignore`); choose ONE delivery method:

1. **Git LFS** — track `*.tflite` and `*.onnx` via `.gitattributes`, or
2. **Fetch step** — document a download command in `docs/BUILDERS_GUIDE.md` and run it
   before building.

## Expected files

| File | Purpose | Notes |
|---|---|---|
| `baby_detect.tflite` | Baby/person detection -> bbox | normalized 0..1 coords |
| `cry_cls.tflite` | Cry classification | hungry/pain/discomfort/normal |
| `posture.tflite` | Posture classifier | SUPINE/PRONE/SIDE/COVERED/UNKNOWN |
| `hazard_detect.tflite` | COCO-pretrained object detector | YOLO-nano / MobileNet SSD; NO infant fine-tuning for v1 |

## Rules

- Log the model hash + active delegate (NNAPI/GPU/CPU) at startup.
- Version models alongside code; keep the artifact + its version in sync.
- The hazard label mapping lives in code (`HazardWhitelist`), not in the model.
- Never commit model binaries without choosing one of the methods above.