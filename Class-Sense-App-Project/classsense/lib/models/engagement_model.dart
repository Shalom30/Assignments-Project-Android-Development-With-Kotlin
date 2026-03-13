import 'base_model.dart';

class EngagementModel extends BaseModel<EngagementModel> {
  final String studentId;
  final String classId;
  final double focusScore;
  final DateTime timestamp;

  const EngagementModel({
    required super.id,
    required this.studentId,
    required this.classId,
    required this.focusScore,
    required this.timestamp,
  });

  /// Factory that builds an [EngagementModel] from a Firestore document map.
  factory EngagementModel.fromMap(Map<String, dynamic> map, String id) {
    return EngagementModel(
      id: id,
      studentId: BaseModel.getString(map, 'studentId'),
      classId: BaseModel.getString(map, 'classId'),
      focusScore: BaseModel.getDouble(map, 'focusScore'),
      timestamp: BaseModel.timestampToDate(map['timestamp']),
    );
  }

  @override
  Map<String, dynamic> toMap() {
    return {
      'studentId': studentId,
      'classId': classId,
      'focusScore': focusScore,
      'timestamp': BaseModel.dateToTimestamp(timestamp),
    };
  }

  /// Focus level classification.
  EngagementLevel get level {
    if (focusScore > 70) return EngagementLevel.high;
    if (focusScore > 40) return EngagementLevel.medium;
    return EngagementLevel.low;
  }

  EngagementModel copyWith({
    String? id,
    String? studentId,
    String? classId,
    double? focusScore,
    DateTime? timestamp,
  }) {
    return EngagementModel(
      id: id ?? this.id,
      studentId: studentId ?? this.studentId,
      classId: classId ?? this.classId,
      focusScore: focusScore ?? this.focusScore,
      timestamp: timestamp ?? this.timestamp,
    );
  }
}

/// Categorizes engagement focus levels.
enum EngagementLevel { high, medium, low }
