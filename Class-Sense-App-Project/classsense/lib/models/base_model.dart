import 'package:cloud_firestore/cloud_firestore.dart';

/// Abstract generic base class for all Firestore data models.
/// Enforces consistent serialization via [toMap] / [fromMap] pattern
/// and provides shared utility methods through generics.
abstract class BaseModel<T extends BaseModel<T>> {
  final String id;

  const BaseModel({required this.id});

  /// Serializes the model to a Firestore-compatible map.
  Map<String, dynamic> toMap();

  /// Converts a Firestore [Timestamp] to [DateTime], with a fallback.
  static DateTime timestampToDate(dynamic value) {
    if (value is Timestamp) return value.toDate();
    return DateTime.now();
  }

  /// Wraps a [DateTime] into a Firestore [Timestamp].
  static Timestamp dateToTimestamp(DateTime date) {
    return Timestamp.fromDate(date);
  }

  /// Safely reads a [String] from a map with a fallback default.
  static String getString(Map<String, dynamic> map, String key,
      [String fallback = '']) {
    return map[key]?.toString() ?? fallback;
  }

  /// Safely reads a [double] from a map with a fallback default.
  static double getDouble(Map<String, dynamic> map, String key,
      [double fallback = 0.0]) {
    final value = map[key];
    if (value is num) return value.toDouble();
    return fallback;
  }

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is BaseModel && runtimeType == other.runtimeType && id == other.id;

  @override
  int get hashCode => id.hashCode;

  @override
  String toString() => '$runtimeType(id: $id)';
}

/// A typedef for factory constructors that build a model from Firestore data.
typedef ModelFactory<T> = T Function(Map<String, dynamic> map, String id);
