import 'package:firebase_core/firebase_core.dart';
import 'package:firebase_core_platform_interface/test.dart';
import 'package:flutter_test/flutter_test.dart';

/// Sets up Firebase Core mocks so widgets that depend on Firebase
/// can be instantiated in unit / widget tests.
void setupFirebaseMocks() {
  TestWidgetsFlutterBinding.ensureInitialized();
  setupFirebaseCoreMocks();
}

/// Call this in [setUpAll] after [setupFirebaseMocks].
Future<void> initializeFirebaseForTest() async {
  await Firebase.initializeApp();
}
