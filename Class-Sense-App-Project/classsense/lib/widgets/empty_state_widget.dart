// ClassSense - Day 5 Polish
import 'package:flutter/material.dart';

/// A reusable empty-state placeholder with icon, text, and optional action.
///
/// Demonstrates: optional parameters, conditional rendering, const constructor.
///
/// Example usages:
/// ```dart
/// EmptyStateWidget(
///   icon: Icons.notifications_none,
///   title: "No Notifications",
///   subtitle: "You have no new notifications",
/// )
/// ```
/// ```dart
/// EmptyStateWidget(
///   icon: Icons.class_outlined,
///   title: "No Classes Today",
///   subtitle: "Enjoy your free day!",
///   buttonLabel: "Browse Courses",
///   onButtonPressed: () => navigateToCourses(),
/// )
/// ```
class EmptyStateWidget extends StatelessWidget {
  final IconData icon;
  final String title;
  final String subtitle;
  final String? buttonLabel;
  final VoidCallback? onButtonPressed;

  const EmptyStateWidget({
    super.key,
    required this.icon,
    required this.title,
    required this.subtitle,
    this.buttonLabel,
    this.onButtonPressed,
  });

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Icon(
            icon,
            size: 80,
            color: const Color(0xFF2E86AB).withOpacity(0.4),
          ),
          const SizedBox(height: 24),
          Text(
            title,
            style: const TextStyle(
              fontWeight: FontWeight.bold,
              fontSize: 20,
              color: Color(0xFF1A3A5C),
            ),
          ),
          const SizedBox(height: 8),
          Padding(
            padding: const EdgeInsets.symmetric(horizontal: 32),
            child: Text(
              subtitle,
              textAlign: TextAlign.center,
              style: const TextStyle(
                fontSize: 14,
                color: Colors.grey,
              ),
            ),
          ),
          if (buttonLabel != null) ...[
            const SizedBox(height: 24),
            SizedBox(
              width: 200,
              child: ElevatedButton(
                onPressed: onButtonPressed,
                child: Text(buttonLabel!),
              ),
            ),
          ],
        ],
      ),
    );
  }
}
