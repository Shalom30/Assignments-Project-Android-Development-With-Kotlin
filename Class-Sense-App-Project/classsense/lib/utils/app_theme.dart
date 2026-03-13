// ClassSense - Day 5 Polish
import 'package:flutter/material.dart';

/// Centralized Material 3 theme for the ClassSense app.
///
/// Provides a single source of truth for typography, color, shape, and
/// component styles. Uses OOP encapsulation — private constructor prevents
/// instantiation; static getter exposes the theme.
class AppTheme {
  AppTheme._(); // private constructor — utility class

  // ─── Color Constants ────────────────────────────────────────
  static const Color _primaryDarkNavy = Color(0xFF1A3A5C);
  static const Color _secondaryBlue = Color(0xFF2E86AB);
  static const Color _white = Color(0xFFFFFFFF);
  static const Color _lightGrey = Color(0xFFF5F5F5);

  // ─── Light Theme ────────────────────────────────────────────
  static ThemeData get lightTheme {
    return ThemeData(
      fontFamily: 'Roboto',
      useMaterial3: true,

      // Color Scheme
      colorScheme: ColorScheme.fromSeed(
        seedColor: _primaryDarkNavy,
      ),

      scaffoldBackgroundColor: _white,

      // AppBar
      appBarTheme: const AppBarTheme(
        backgroundColor: _primaryDarkNavy,
        foregroundColor: _white,
        elevation: 2,
        centerTitle: true,
        titleTextStyle: TextStyle(
          fontFamily: 'Roboto',
          fontWeight: FontWeight.bold,
          fontSize: 20,
          color: _white,
        ),
      ),

      // Elevated Buttons
      elevatedButtonTheme: ElevatedButtonThemeData(
        style: ElevatedButton.styleFrom(
          backgroundColor: _secondaryBlue,
          foregroundColor: _white,
          minimumSize: const Size(double.infinity, 52),
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(12),
          ),
          textStyle: const TextStyle(
            fontFamily: 'Roboto',
            fontWeight: FontWeight.bold,
            fontSize: 16,
          ),
        ),
      ),

      // Input Fields
      inputDecorationTheme: InputDecorationTheme(
        border: OutlineInputBorder(
          borderRadius: BorderRadius.circular(12),
        ),
        focusedBorder: OutlineInputBorder(
          borderRadius: BorderRadius.circular(12),
          borderSide: const BorderSide(
            color: _secondaryBlue,
            width: 2,
          ),
        ),
        filled: true,
        fillColor: _lightGrey,
        contentPadding: const EdgeInsets.symmetric(
          horizontal: 16,
          vertical: 16,
        ),
      ),

      // Cards
      cardTheme: CardTheme(
        elevation: 3,
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(16),
        ),
        color: _white,
        margin: const EdgeInsets.all(8),
      ),

      // Text Styles
      textTheme: const TextTheme(
        headlineLarge: TextStyle(
          fontSize: 28,
          fontWeight: FontWeight.bold,
          color: _primaryDarkNavy,
        ),
        headlineMedium: TextStyle(
          fontSize: 22,
          fontWeight: FontWeight.bold,
          color: _primaryDarkNavy,
        ),
        bodyLarge: TextStyle(
          fontSize: 16,
          color: Colors.black87,
        ),
        bodyMedium: TextStyle(
          fontSize: 14,
          color: Colors.black54,
        ),
        labelLarge: TextStyle(
          fontSize: 14,
          fontWeight: FontWeight.bold,
          color: _white,
        ),
      ),
    );
  }
}
