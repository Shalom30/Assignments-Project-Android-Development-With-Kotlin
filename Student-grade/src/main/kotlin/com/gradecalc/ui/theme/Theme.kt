package com.gradecalc.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// ── Color Palette — Deep Navy Professional Dark ───────────────────────────────
val NavyDeep        = Color(0xFF0A0E1A)   // main background
val NavySurface     = Color(0xFF111827)   // card/surface
val NavyCard        = Color(0xFF1A2235)   // elevated card
val NavyBorder      = Color(0xFF1E2D45)   // border/divider
val AccentBlue      = Color(0xFF3B82F6)   // primary accent
val AccentCyan      = Color(0xFF06B6D4)   // secondary accent
val AccentGreen     = Color(0xFF10B981)   // success / grade A
val AccentAmber     = Color(0xFFF59E0B)   // warning
val AccentRed       = Color(0xFFEF4444)   // error / grade F
val AccentPurple    = Color(0xFF8B5CF6)   // highlight
val TextPrimary     = Color(0xFFF1F5F9)   // main text
val TextSecondary   = Color(0xFF94A3B8)   // muted text
val TextHint        = Color(0xFF475569)   // placeholder

// Grade colors
val GradeColorA     = Color(0xFF10B981)
val GradeColorBPlus = Color(0xFF3B82F6)
val GradeColorB     = Color(0xFF6366F1)
val GradeColorCPlus = Color(0xFFF59E0B)
val GradeColorC     = Color(0xFFEAB308)
val GradeColorDPlus = Color(0xFFF97316)
val GradeColorD     = Color(0xFFEF4444)
val GradeColorF     = Color(0xFF991B1B)

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

private val DarkColorScheme = darkColorScheme(
    primary        = AccentBlue,
    secondary      = AccentCyan,
    background     = NavyDeep,
    surface        = NavySurface,
    onPrimary      = TextPrimary,
    onSecondary    = TextPrimary,
    onBackground   = TextPrimary,
    onSurface      = TextPrimary,
    error          = AccentRed
)

@Composable
fun GradeCalcTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        content     = content
    )
}