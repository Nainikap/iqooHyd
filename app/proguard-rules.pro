# NightWatch (app) ProGuard/R8 rules.
# REQUIRED CONTENT:
# - Keep TFLite/ONNX runtime and delegate classes (they are loaded reflectively).
# - Keep serialization classes for AlertPayload / model types if using reflection.
# - Strip the debug package (DebugSignalInjector, overlay) from release automatically.

# TODO: add keeps for the specific TFLite/ONNX runtime in use.
-keep class org.tensorflow.lite.** { *; }
-dontwarn org.tensorflow.lite.**