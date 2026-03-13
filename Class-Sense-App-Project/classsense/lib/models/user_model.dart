import 'base_model.dart';

class UserModel extends BaseModel<UserModel> {
  final String name;
  final String email;
  final String role;
  final DateTime createdAt;

  const UserModel({
    required String uid,
    required this.name,
    required this.email,
    required this.role,
    required this.createdAt,
  }) : super(id: uid);

  /// Alias for readability — [id] inherited from [BaseModel].
  String get uid => id;

  /// Factory that builds a [UserModel] from a Firestore document map.
  factory UserModel.fromMap(Map<String, dynamic> map, String uid) {
    return UserModel(
      uid: uid,
      name: BaseModel.getString(map, 'name'),
      email: BaseModel.getString(map, 'email'),
      role: BaseModel.getString(map, 'role', 'student'),
      createdAt: BaseModel.timestampToDate(map['createdAt']),
    );
  }

  @override
  Map<String, dynamic> toMap() {
    return {
      'name': name,
      'email': email,
      'role': role,
      'createdAt': BaseModel.dateToTimestamp(createdAt),
    };
  }

  /// Creates a copy with optional field overrides (immutable update pattern).
  UserModel copyWith({
    String? uid,
    String? name,
    String? email,
    String? role,
    DateTime? createdAt,
  }) {
    return UserModel(
      uid: uid ?? this.uid,
      name: name ?? this.name,
      email: email ?? this.email,
      role: role ?? this.role,
      createdAt: createdAt ?? this.createdAt,
    );
  }
}
