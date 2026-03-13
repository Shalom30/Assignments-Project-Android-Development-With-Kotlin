package com.gradecalc.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// ── VS Code Dark Grey Color Palette ──────────────────────────────────────────

val DarkBg          = Color(0xFF1E1E1E)   // main background
val DarkSurface     = Color(0xFF252526)   // top bar / surface
val DarkCard        = Color(0xFF2D2D2D)   // card background
val DarkBorder      = Color(0xFF3E3E42)   // border / divider

val AccentBlue      = Color(0xFF4FC3F7)   // primary blue
val AccentCyan      = Color(0xFF4EC9B0)   // teal / cyan
val AccentGreen     = Color(0xFF4CAF50)   // success green
val AccentAmber     = Color(0xFFCE9178)   // warning amber
val AccentRed       = Color(0xFFF44747)   // error red
val AccentPurple    = Color(0xFFC586C0)   // purple highlight

val TextPrimary     = Color(0xFFD4D4D4)   // main text
val TextSecondary   = Color(0xFF9CDCFE)   // muted text
val TextHint        = Color(0xFF6A9955)   // placeholder / hint

// ── Grade Colors ──────────────────────────────────────────────────────────────

val GradeColorA     = Color(0xFF4CAF50)
val GradeColorBPlus = Color(0xFF4FC3F7)
val GradeColorB     = Color(0xFF6366F1)
val GradeColorCPlus = Color(0xFFF59E0B)
val GradeColorC     = Color(0xFFEAB308)
val GradeColorDPlus = Color(0xFFF97316)
val GradeColorD     = Color(0xFFEF4444)
val GradeColorF     = Color(0xFF991B1B)

// ── Grade Color Helper — used in ResultScreen and HomeScreen ──────────────────

fun gradeColor(grade: String): Color = when (grade) {
    "A"  -> GradeColorA
    "B+" -> GradeColorBPlus
    "B"  -> GradeColorB
    "C+" -> GradeColorCPlus
    "C"  -> GradeColorC
    "D+" -> GradeColorDPlus
    "D"  -> GradeColorD
    else -> GradeColorF
}

// ── Material Theme Color Scheme ───────────────────────────────────────────────

private val DarkColorScheme = darkColorScheme(
    primary      = AccentBlue,
    secondary    = AccentCyan,
    background   = DarkBg,
    surface      = DarkSurface,
    onPrimary    = TextPrimary,
    onSecondary  = TextPrimary,
    onBackground = TextPrimary,
    onSurface    = TextPrimary,
    error        = AccentRed
)

// ── App Theme — only ONE declaration ─────────────────────────────────────────

@Composable
fun GradeCalcTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        content     = content
    )
}