import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';

import 'package:classsense/screens/auth/login_screen.dart';
import 'package:classsense/screens/auth/register_screen.dart';
import 'package:classsense/screens/student/student_home.dart';

import 'mock_firebase.dart';

// Helper to wrap a widget in MaterialApp for testing
Widget _testApp(Widget child) {
  return MaterialApp(
    home: child,
    routes: {
      '/login': (_) => const Scaffold(body: Text('Login')),
      '/register': (_) => const Scaffold(body: Text('Register')),
      '/student-home': (_) => const Scaffold(body: Text('StudentHome')),
      '/teacher-home': (_) => const Scaffold(body: Text('TeacherHome')),
      '/admin-home': (_) => const Scaffold(body: Text('AdminHome')),
      '/onboarding': (_) => const Scaffold(body: Text('Onboarding')),
    },
  );
}

/// Sets the test surface to a phone-sized screen (411 x 891 logical pixels).
void _setPhoneSize(WidgetTester tester) {
  tester.view.physicalSize = const Size(1080, 2340);
  tester.view.devicePixelRatio = 2.625;
  addTearDown(() {
    tester.view.resetPhysicalSize();
    tester.view.resetDevicePixelRatio();
  });
}

void main() {
  setupFirebaseMocks();

  setUpAll(() async {
    await initializeFirebaseForTest();
  });

  // ─── Test 1: Login screen renders correctly ───────────────
  testWidgets('Test 1: Login screen renders email, password, login button',
      (WidgetTester tester) async {
    _setPhoneSize(tester);
    await tester.pumpWidget(_testApp(const LoginScreen()));
    await tester.pumpAndSettle();

    // Email field
    expect(find.widgetWithText(TextFormField, 'Email'), findsOneWidget);

    // Password field
    expect(find.widgetWithText(TextFormField, 'Password'), findsOneWidget);

    // Login button
    expect(find.widgetWithText(ElevatedButton, 'Login'), findsOneWidget);

    // Register link
    expect(find.text('Register'), findsOneWidget);
  });

  // ─── Test 2: Login with empty fields shows validation error ───
  testWidgets('Test 2: Login with empty fields shows validation errors',
      (WidgetTester tester) async {
    _setPhoneSize(tester);
    await tester.pumpWidget(_testApp(const LoginScreen()));
    await tester.pumpAndSettle();

    // Tap login without filling fields
    await tester.tap(find.widgetWithText(ElevatedButton, 'Login'));
    await tester.pumpAndSettle();

    // Should show validation messages
    expect(find.text('Please enter your email'), findsOneWidget);
    expect(find.text('Please enter your password'), findsOneWidget);
  });

  // ─── Test 3: Register screen renders all fields ───────────
  testWidgets('Test 3: Register screen renders all fields',
      (WidgetTester tester) async {
    _setPhoneSize(tester);
    await tester.pumpWidget(_testApp(const RegisterScreen()));
    await tester.pumpAndSettle();

    expect(find.widgetWithText(TextFormField, 'Full Name'), findsOneWidget);
    expect(find.widgetWithText(TextFormField, 'Email'), findsOneWidget);
    expect(find.widgetWithText(TextFormField, 'Password'), findsOneWidget);
    expect(
        find.widgetWithText(TextFormField, 'Confirm Password'), findsOneWidget);
    expect(find.widgetWithText(ElevatedButton, 'Register'), findsOneWidget);
  });

  // ─── Test 4: Password mismatch shows error ────────────────
  testWidgets('Test 4: Password mismatch shows error',
      (WidgetTester tester) async {
    _setPhoneSize(tester);
    await tester.pumpWidget(_testApp(const RegisterScreen()));
    await tester.pumpAndSettle();

    // Fill in name and email
    await tester.enterText(
        find.widgetWithText(TextFormField, 'Full Name'), 'Test User');
    await tester.enterText(
        find.widgetWithText(TextFormField, 'Email'), 'test@mail.com');

    // Enter mismatching passwords
    await tester.enterText(
        find.widgetWithText(TextFormField, 'Password'), 'password123');
    await tester.enterText(
        find.widgetWithText(TextFormField, 'Confirm Password'), 'different');

    // Tap register
    await tester.tap(find.widgetWithText(ElevatedButton, 'Register'));
    await tester.pumpAndSettle();

    expect(find.text('Passwords do not match'), findsOneWidget);
  });

  // ─── Test 5: Role dropdown has all options ─────────────────
  testWidgets('Test 5: Role dropdown shows Student, Teacher, Admin',
      (WidgetTester tester) async {
    _setPhoneSize(tester);
    await tester.pumpWidget(_testApp(const RegisterScreen()));
    await tester.pumpAndSettle();

    // The dropdown should display the default value 'Student'
    expect(find.text('Student'), findsOneWidget);

    // Open the dropdown
    await tester.tap(find.text('Student'));
    await tester.pumpAndSettle();

    // All three options should be visible
    expect(find.text('Student'), findsWidgets);
    expect(find.text('Teacher'), findsOneWidget);
    expect(find.text('Admin'), findsOneWidget);
  });

  // ─── Test 7: Student bottom nav switches tabs ──────────────
  testWidgets('Test 7: StudentHome bottom nav switches tabs',
      (WidgetTester tester) async {
    _setPhoneSize(tester);
    await tester.pumpWidget(_testApp(const StudentHome()));
    await tester.pumpAndSettle();

    // Home tab default
    expect(find.text('Hello, Student!'), findsOneWidget);

    // Tap Attendance tab
    await tester.tap(find.text('Attendance'));
    await tester.pumpAndSettle();
    expect(find.text('Attendance History'), findsOneWidget);

    // Tap Study tab
    await tester.tap(find.text('Study'));
    await tester.pumpAndSettle();
    expect(find.text('Study Assistant'), findsOneWidget);

    // Tap Profile tab
    await tester.tap(find.text('Profile'));
    await tester.pumpAndSettle();
    expect(find.text('Logout'), findsOneWidget);
  });
}
