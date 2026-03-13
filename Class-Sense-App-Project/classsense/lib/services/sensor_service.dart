import 'package:sensors_plus/sensors_plus.dart';
import '../mixins/disposable_mixin.dart';

/// Monitors accelerometer data to detect device motion.
/// Uses [DisposableMixin] for automatic stream lifecycle management.
class SensorService with DisposableMixin {
  double _lastX = 0, _lastY = 0, _lastZ = 0;
  bool _isMoving = false;
  double _movementMagnitude = 0;

  bool get isMoving => _isMoving;
  double get movementMagnitude => _movementMagnitude;

  /// Starts accelerometer monitoring with [threshold]-based motion detection.
  /// Uses [autoListen] from [DisposableMixin] for automatic cleanup.
  void startMonitoring({double threshold = 2.0}) {
    autoListen<AccelerometerEvent>(
      accelerometerEventStream(),
      (AccelerometerEvent event) {
        _movementMagnitude = _calculateDelta(event);
        _isMoving = _movementMagnitude > threshold;
        _lastX = event.x;
        _lastY = event.y;
        _lastZ = event.z;
      },
    );
  }

  /// Calculates the sum-of-absolute-deltas from the last reading.
  double _calculateDelta(AccelerometerEvent event) {
    return (event.x - _lastX).abs() +
        (event.y - _lastY).abs() +
        (event.z - _lastZ).abs();
  }

  /// Stops monitoring by disposing all tracked subscriptions.
  void stopMonitoring() => dispose();
}
