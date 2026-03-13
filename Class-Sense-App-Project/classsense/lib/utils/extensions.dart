import 'package:flutter/material.dart';

/// Extension on [String] adding common higher-order utility methods.
extension StringExtensions on String {
  /// Capitalizes the first letter: 'hello' → 'Hello'.
  String get capitalized =>
      isEmpty ? this : this[0].toUpperCase() + substring(1);

  /// Returns the first character, or '?' if empty.
  String get initial => isEmpty ? '?' : this[0].toUpperCase();

  /// Validates an email address format.
  bool get isValidEmail =>
      RegExp(r'^[\w.\-]+@[\w\-]+\.\w{2,4}$').hasMatch(trim());
}

/// Extension on [DateTime] for display formatting.
extension DateTimeExtensions on DateTime {
  /// Formats as 'Mon, Mar 9' style short date.
  String get shortDate {
    const days = ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'];
    const months = [
      'Jan',
      'Feb',
      'Mar',
      'Apr',
      'May',
      'Jun',
      'Jul',
      'Aug',
      'Sep',
      'Oct',
      'Nov',
      'Dec',
    ];
    return '${days[weekday - 1]}, ${months[month - 1]} $day';
  }

  /// Returns 'Today', 'Yesterday', or the short date.
  String get relativeLabel {
    final now = DateTime.now();
    final today = DateTime(now.year, now.month, now.day);
    final thisDay = DateTime(year, month, day);
    final diff = today.difference(thisDay).inDays;
    if (diff == 0) return 'Today';
    if (diff == 1) return 'Yesterday';
    return shortDate;
  }
}

/// Extension on [BuildContext] for quick access to theme & size.
extension BuildContextExtensions on BuildContext {
  /// Shorthand for `MediaQuery.of(context).size`.
  Size get screenSize => MediaQuery.of(this).size;

  /// Shorthand for `Theme.of(context).textTheme`.
  TextTheme get textTheme => Theme.of(this).textTheme;

  /// Shorthand for `Theme.of(context).colorScheme`.
  ColorScheme get colorScheme => Theme.of(this).colorScheme;

  /// Shows a themed SnackBar with the given [message].
  void showSnackBar(String message, {bool isError = false}) {
    ScaffoldMessenger.of(this).showSnackBar(
      SnackBar(
        content: Text(message),
        backgroundColor: isError ? Colors.red.shade700 : Colors.green.shade700,
        behavior: SnackBarBehavior.floating,
        shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(8)),
      ),
    );
  }
}

/// Extension on [List] for common aggregate operations.
extension ListExtensions<T> on List<T> {
  /// Groups elements by a key derived from [keyOf].
  Map<K, List<T>> groupBy<K>(K Function(T element) keyOf) {
    final map = <K, List<T>>{};
    for (final item in this) {
      final key = keyOf(item);
      (map[key] ??= []).add(item);
    }
    return map;
  }

  /// Returns the percentage of elements matching [test], as 0–100.
  double percentWhere(bool Function(T element) test) {
    if (isEmpty) return 0;
    return (where(test).length / length) * 100;
  }
}
