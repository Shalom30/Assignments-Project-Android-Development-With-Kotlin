import '../models/attendance_model.dart';
import '../models/engagement_model.dart';
import '../models/base_model.dart';
import '../utils/extensions.dart';
import 'base_repository.dart';

/// High-level service that composes generic [BaseRepository] instances
/// for each domain model, demonstrating OOP composition + generics.
class FirestoreService {
  late final BaseRepository<AttendanceModel> _attendanceRepo;
  late final BaseRepository<EngagementModel> _engagementRepo;

  FirestoreService() {
    _attendanceRepo = BaseRepository<AttendanceModel>(
      collectionPath: 'attendance',
      fromMap: AttendanceModel.fromMap,
    );
    _engagementRepo = BaseRepository<EngagementModel>(
      collectionPath: 'engagement',
      fromMap: EngagementModel.fromMap,
    );
  }

  // ─── Generic batch write (works with any BaseModel) ──────
  /// Writes a list of models to Firestore in parallel.
  /// Demonstrates generics: accepts any [BaseModel] subclass + its repo.
  Future<void> batchWrite<T extends BaseModel<T>>(
    BaseRepository<T> repo,
    List<T> models,
  ) async {
    await Future.wait(models.map((model) => repo.set(model)));
  }

  // ─── Generic aggregate helper ─────────────────────────────
  /// Computes grouped statistics from a stream using higher-order functions.
  /// [keySelector] picks the grouping key; [valueSelector] picks the value.
  Stream<Map<K, List<V>>> groupedStream<T, K, V>(
    Stream<List<T>> source, {
    required K Function(T) keySelector,
    required V Function(T) valueSelector,
  }) {
    return source.map((items) {
      return items.groupBy(keySelector).map(
            (key, group) => MapEntry(key, group.map(valueSelector).toList()),
          );
    });
  }

  // ─── Attendance (delegates to generic repository) ─────────
  Future<void> markAttendance(AttendanceModel attendance) =>
      _attendanceRepo.add(attendance).then((_) {});

  Stream<List<AttendanceModel>> getAttendanceByClass(String classId) =>
      _attendanceRepo.streamWhere(
        field: 'classId',
        isEqualTo: classId,
        orderByField: 'date',
        descending: true,
      );

  Stream<List<AttendanceModel>> getStudentAttendance(String studentId) =>
      _attendanceRepo.streamWhere(
        field: 'studentId',
        isEqualTo: studentId,
        orderByField: 'date',
        descending: true,
      );

  /// Returns attendance records grouped by date (higher-order stream transform).
  Stream<Map<String, List<AttendanceModel>>> getAttendanceGroupedByDate(
      String classId) {
    return getAttendanceByClass(classId).map(
      (records) => records.groupBy(
        (r) => r.date.shortDate,
      ),
    );
  }

  // ─── Engagement (delegates to generic repository) ─────────
  Future<void> recordEngagement(EngagementModel engagement) =>
      _engagementRepo.add(engagement).then((_) {});

  Stream<List<EngagementModel>> getEngagementByClass(String classId) =>
      _engagementRepo.streamWhere(
        field: 'classId',
        isEqualTo: classId,
        orderByField: 'timestamp',
        descending: true,
      );

  /// Calculates average engagement score from a list (higher-order reduction).
  double calculateAverageEngagement(List<EngagementModel> records) {
    if (records.isEmpty) return 0;
    return records.fold<double>(0, (sum, r) => sum + r.focusScore) /
        records.length;
  }
}
