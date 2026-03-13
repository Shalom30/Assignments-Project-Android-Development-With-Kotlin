// ClassSense - Day 5 Polish
import 'package:flutter/material.dart';

/// Adapts layout to small phones, normal phones, and tablets using
/// [LayoutBuilder] constraints.
///
/// Demonstrates: static helper methods, conditional composition pattern,
/// and responsive design without external packages.
///
/// Usage:
/// ```dart
/// ResponsiveWrapper(child: MyScreen())
/// ```
class ResponsiveWrapper extends StatelessWidget {
  final Widget child;

  const ResponsiveWrapper({
    super.key,
    required this.child,
  });

  // ─── Static helper functions ──────────────────────────────
  /// Returns true when the device width is below 360 logical pixels.
  static bool isSmallScreen(BuildContext context) =>
      MediaQuery.of(context).size.width < 360;

  /// Returns true when the device width is 600+ logical pixels (tablet).
  static bool isTablet(BuildContext context) =>
      MediaQuery.of(context).size.width >= 600;

  @override
  Widget build(BuildContext context) {
    return LayoutBuilder(
      builder: (context, constraints) {
        if (constraints.maxWidth < 360) {
          // Very small screen — scale down to fit
          return FittedBox(
            fit: BoxFit.scaleDown,
            child: child,
          );
        } else if (constraints.maxWidth >= 600) {
          // Tablet — center content with max width
          return Center(
            child: ConstrainedBox(
              constraints: const BoxConstraints(maxWidth: 480),
              child: child,
            ),
          );
        }
        // Normal phone — pass through unchanged
        return child;
      },
    );
  }
}
