import 'base_model.dart';

class AttendanceModel extends BaseModel<AttendanceModel> {
  final String studentId;
  final String studentName;
  final String classId;
  final AttendanceStatus status;
  final DateTime date;

  const AttendanceModel({
    required super.id,
    required this.studentId,
    required this.studentName,
    required this.classId,
    required this.status,
    required this.date,
  });

  /// Factory that builds an [AttendanceModel] from a Firestore document map.
  factory AttendanceModel.fromMap(Map<String, dynamic> map, String id) {
    return AttendanceModel(
      id: id,
      studentId: BaseModel.getString(map, 'studentId'),
      studentName: BaseModel.getString(map, 'studentName'),
      classId: BaseModel.getString(map, 'classId'),
      status: AttendanceStatus.fromString(
          BaseModel.getString(map, 'status', 'absent')),
      date: BaseModel.timestampToDate(map['date']),
    );
  }

  @override
  Map<String, dynamic> toMap() {
    return {
      'studentId': studentId,
      'studentName': studentName,
      'classId': classId,
      'status': status.value,
      'date': BaseModel.dateToTimestamp(date),
    };
  }

  AttendanceModel copyWith({
    String? id,
    String? studentId,
    String? studentName,
    String? classId,
    AttendanceStatus? status,
    DateTime? date,
  }) {
    return AttendanceModel(
      id: id ?? this.id,
      studentId: studentId ?? this.studentId,
      studentName: studentName ?? this.studentName,
      classId: classId ?? this.classId,
      status: status ?? this.status,
      date: date ?? this.date,
    );
  }
}

/// Enum for attendance status — replaces raw string comparison.
enum AttendanceStatus {
  present('present'),
  absent('absent'),
  late_('late');

  final String value;
  const AttendanceStatus(this.value);

  factory AttendanceStatus.fromString(String s) {
    switch (s.toLowerCase()) {
      case 'present':
        return AttendanceStatus.present;
      case 'late':
        return AttendanceStatus.late_;
      default:
        return AttendanceStatus.absent;
    }
  }

  String get displayName => value[0].toUpperCase() + value.substring(1);
}
