// ClassSense - Day 5 Polish
import 'package:flutter/material.dart';

/// Full-screen error view with optional retry and back navigation.
///
/// Demonstrates: optional parameters, conditional UI rendering, and
/// encapsulated navigation logic.
class ErrorScreen extends StatelessWidget {
  final String? errorMessage;
  final VoidCallback? onRetry;

  const ErrorScreen({
    super.key,
    this.errorMessage,
    this.onRetry,
  });

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      body: Center(
        child: Padding(
          padding: const EdgeInsets.symmetric(horizontal: 32),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              const Icon(
                Icons.error_outline,
                size: 90,
                color: Color(0xFFC62828),
              ),
              const SizedBox(height: 24),
              const Text(
                'Something went wrong',
                style: TextStyle(
                  fontWeight: FontWeight.bold,
                  fontSize: 22,
                  color: Color(0xFF1A3A5C),
                ),
              ),
              const SizedBox(height: 12),
              Text(
                errorMessage ??
                    'An unexpected error occurred. Please try again.',
                textAlign: TextAlign.center,
                style: const TextStyle(
                  fontSize: 14,
                  color: Colors.grey,
                ),
              ),
              const SizedBox(height: 32),
              if (onRetry != null)
                SizedBox(
                  width: 200,
                  child: ElevatedButton(
                    onPressed: onRetry,
                    child: const Text('Try Again'),
                  ),
                ),
              const SizedBox(height: 12),
              TextButton(
                onPressed: () => Navigator.pop(context),
                child: const Text(
                  'Go Back',
                  style: TextStyle(color: Color(0xFF2E86AB)),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}

/// Displayed when no internet connection is detected.
///
/// Self-contained, zero-parameter widget for quick drop-in use.
class NetworkErrorWidget extends StatelessWidget {
  const NetworkErrorWidget({super.key});

  @override
  Widget build(BuildContext context) {
    return const Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Icon(
            Icons.wifi_off,
            size: 80,
            color: Colors.grey,
          ),
          SizedBox(height: 24),
          Text(
            'No Internet Connection',
            style: TextStyle(
              fontWeight: FontWeight.bold,
              fontSize: 18,
            ),
          ),
          SizedBox(height: 8),
          Padding(
            padding: EdgeInsets.symmetric(horizontal: 32),
            child: Text(
              'Please check your connection and try again',
              textAlign: TextAlign.center,
              style: TextStyle(
                fontSize: 14,
                color: Colors.grey,
              ),
            ),
          ),
        ],
      ),
    );
  }
}

/// Displayed when a required OS permission has not been granted.
///
/// Shows a placeholder "Open Settings" button that prompts the user
/// via a SnackBar (no platform-channel call needed for the UI layer).
class NoPermissionWidget extends StatelessWidget {
  const NoPermissionWidget({super.key});

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          const Icon(
            Icons.lock_outline,
            size: 80,
            color: Color(0xFFF57F17),
          ),
          const SizedBox(height: 24),
          const Text(
            'Permission Required',
            style: TextStyle(
              fontWeight: FontWeight.bold,
              fontSize: 18,
              color: Color(0xFF1A3A5C),
            ),
          ),
          const SizedBox(height: 8),
          const Padding(
            padding: EdgeInsets.symmetric(horizontal: 32),
            child: Text(
              'Please grant the required permissions to use this feature',
              textAlign: TextAlign.center,
              style: TextStyle(
                fontSize: 14,
                color: Colors.grey,
              ),
            ),
          ),
          const SizedBox(height: 24),
          SizedBox(
            width: 200,
            child: ElevatedButton(
              onPressed: () {
                ScaffoldMessenger.of(context).showSnackBar(
                  const SnackBar(
                    content: Text('Please open app settings manually'),
                  ),
                );
              },
              child: const Text('Open Settings'),
            ),
          ),
        ],
      ),
    );
  }
}
