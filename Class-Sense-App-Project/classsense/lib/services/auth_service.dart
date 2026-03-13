import 'package:firebase_auth/firebase_auth.dart';
import '../models/user_model.dart';
import 'base_repository.dart';

/// Authentication service combining Firebase Auth with a generic
/// [BaseRepository] for user profile data in Firestore.
///
/// Demonstrates: OOP composition — wraps FirebaseAuth and delegates
/// user-profile persistence to a generic [BaseRepository<UserModel>].
class AuthService {
  final FirebaseAuth _auth = FirebaseAuth.instance;
  final BaseRepository<UserModel> _userRepo = BaseRepository<UserModel>(
    collectionPath: 'users',
    fromMap: UserModel.fromMap,
  );

  User? get currentUser => _auth.currentUser;

  Stream<User?> get authStateChanges => _auth.authStateChanges();

  /// Signs in with email and password.
  Future<UserCredential> signIn(String email, String password) {
    return _auth.signInWithEmailAndPassword(
      email: email,
      password: password,
    );
  }

  /// Registers a new account.
  Future<UserCredential> register(String email, String password) {
    return _auth.createUserWithEmailAndPassword(
      email: email,
      password: password,
    );
  }

  /// Uses the generic repository [set] to persist user profile.
  Future<void> saveUserData(UserModel user) => _userRepo.set(user);

  /// Reads user role via the generic repository.
  Future<String> getUserRole(String uid) async {
    final user = await _userRepo.getById(uid);
    return user?.role ?? 'student';
  }

  /// Gets the full user data via the generic repository.
  Future<UserModel?> getUserData(String uid) => _userRepo.getById(uid);

  /// Returns a real-time stream of the user profile.
  Stream<UserModel?> streamUserData(String uid) => _userRepo.streamById(uid);

  Future<void> signOut() => _auth.signOut();
}
