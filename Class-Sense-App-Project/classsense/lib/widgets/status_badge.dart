import 'package:flutter/material.dart';
import '../utils/app_colors.dart';

/// A generic status badge widget that maps any enum [T] to a colored pill.
/// Uses a higher-order [colorMapper] callback so callers define the mapping.
///
/// Demonstrates: generics on widgets, higher-order function parameters.
class StatusBadge<T> extends StatelessWidget {
  final T status;
  final String Function(T status) labelOf;
  final Color Function(T status) colorOf;

  const StatusBadge({
    super.key,
    required this.status,
    required this.labelOf,
    required this.colorOf,
  });

  /// Convenience factory for [AttendanceStatus]-style string statuses.
  static StatusBadge<String> fromString({
    Key? key,
    required String status,
  }) {
    return StatusBadge<String>(
      key: key,
      status: status,
      labelOf: (s) => s[0].toUpperCase() + s.substring(1),
      colorOf: (s) {
        switch (s.toLowerCase()) {
          case 'present':
            return AppColors.successGreen;
          case 'absent':
            return AppColors.alertRed;
          case 'late':
            return AppColors.warningYellow;
          default:
            return AppColors.skyBlue;
        }
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    final color = colorOf(status);
    return Container(
      padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 4),
      decoration: BoxDecoration(
        color: color.withOpacity(0.15),
        borderRadius: BorderRadius.circular(20),
      ),
      child: Text(
        labelOf(status),
        style: TextStyle(
          color: color,
          fontWeight: FontWeight.w600,
          fontSize: MediaQuery.of(context).size.width * 0.032,
        ),
      ),
    );
  }
}
