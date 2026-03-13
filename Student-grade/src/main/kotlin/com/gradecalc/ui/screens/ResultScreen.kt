package com.gradecalc.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gradecalc.model.Student
import com.gradecalc.ui.theme.*
import com.gradecalc.utils.classAverage
import com.gradecalc.utils.failing
import com.gradecalc.utils.fmt
import com.gradecalc.utils.passing
import androidx.compose.material.icons.filled.TableChart
import androidx.compose.material.icons.filled.Article

@Composable
fun ResultScreen(
    students: List<Student>,
    downloadMessage: String?,
    onBack: () -> Unit,
    onReset: () -> Unit,
    onDownload: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBg)
    ) {
        // ── Top Bar ───────────────────────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(DarkSurface)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = TextSecondary
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    "Grade Results",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    "${students.size} students processed",
                    fontSize = 12.sp,
                    color = TextSecondary
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // ── Download Buttons ──────────────────────────────────────────
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                Button(
                    onClick = { onDownload("PDF") },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AccentRed)
                ) {
                    Icon(
                        Icons.Default.Description,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Download PDF", fontWeight = FontWeight.SemiBold)
                }

                Button(
                    onClick = { onDownload("HTML") },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AccentBlue)
                ) {
                    Icon(
                        Icons.Default.Language,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Download HTML", fontWeight = FontWeight.SemiBold)
                }

                OutlinedButton(
                    onClick = onReset,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = TextSecondary),
                    border = BorderStroke(1.dp, DarkBorder)
                ) {
                    Icon(
                        Icons.Default.Refresh,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("New File")
                }
            }
        }

        // ADD these 2 buttons after the HTML button

        Button(
            onClick = { onDownload("EXCEL") },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AccentGreen)
        ) {
            Icon(
                Icons.Default.TableChart,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text("Download Excel", fontWeight = FontWeight.SemiBold)
        }

        Button(
            onClick = { onDownload("WORD") },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AccentPurple)
        ) {
            Icon(
                Icons.Default.Article,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text("Download Word", fontWeight = FontWeight.SemiBold)
        }

        // ── Download message banner ───────────────────────────────────────
        downloadMessage?.let { message ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        if (message.startsWith("❌"))
                            AccentRed.copy(alpha = 0.1f)
                        else
                            AccentGreen.copy(alpha = 0.1f)
                    )
                    .padding(horizontal = 24.dp, vertical = 10.dp)
            ) {
                Text(
                    message,
                    color = if (message.startsWith("❌")) AccentRed else AccentGreen,
                    fontSize = 13.sp
                )
            }
        }

        // ── Scrollable Body ───────────────────────────────────────────────
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Stats row
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatCard("Class Avg", students.classAverage().fmt(),    AccentBlue,  Modifier.weight(1f))
                    StatCard("Passing",   "${students.passing().size}",      AccentGreen, Modifier.weight(1f))
                    StatCard("Failing",   "${students.failing().size}",      AccentRed,   Modifier.weight(1f))
                    StatCard("Highest",   students.maxOf { it.total }.fmt(), AccentCyan,  Modifier.weight(1f))
                    StatCard("Lowest",    students.minOf { it.total }.fmt(), AccentAmber, Modifier.weight(1f))
                }
            }

            // Grade distribution chart
            item { GradeDistributionCard(students) }

            // Section label
            item {
                Text(
                    "Student Results",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextSecondary
                )
            }

            // Student result cards
            items(students) { student ->
                StudentResultCard(student)
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

// ── Stat Card ─────────────────────────────────────────────────────────────────
@Composable
fun StatCard(
    label: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(DarkCard)
            .border(1.dp, color.copy(alpha = 0.2f), RoundedCornerShape(14.dp))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(value, fontSize = 22.sp, fontWeight = FontWeight.Black, color = color)
            Spacer(modifier = Modifier.height(2.dp))
            Text(label, fontSize = 11.sp, color = TextSecondary)
        }
    }
}

// ── Grade Distribution Chart ──────────────────────────────────────────────────
@Composable
fun GradeDistributionCard(students: List<Student>) {
    val gradeOrder = listOf("A", "B+", "B", "C+", "C", "D+", "D", "F")
    val counts     = gradeOrder.associateWith { g -> students.count { it.grade == g } }
    val maxCount   = counts.values.maxOrNull()?.takeIf { it > 0 } ?: 1

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(DarkCard)
            .border(1.dp, DarkBorder, RoundedCornerShape(16.dp))
            .padding(20.dp)
    ) {
        Column {
            Text(
                "Grade Distribution",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = TextSecondary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                gradeOrder.forEach { grade ->
                    val count    = counts[grade] ?: 0
                    val fraction = count.toFloat() / maxCount.toFloat()
                    val color    = gradeColor(grade)

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Text("$count", fontSize = 11.sp, color = color, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        Box(
                            modifier = Modifier
                                .width(28.dp)
                                .height((fraction * 70f).dp.coerceAtLeast(4.dp))
                                .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                                .background(color.copy(alpha = 0.8f))
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(grade, fontSize = 11.sp, color = TextSecondary, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

// ── Student Result Card ───────────────────────────────────────────────────────
@Composable
fun StudentResultCard(student: Student) {
    val color    = gradeColor(student.grade)
    val fraction = (student.total / 100.0).toFloat().coerceIn(0f, 1f)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(DarkCard)
            .border(1.dp, color.copy(alpha = 0.15f), RoundedCornerShape(14.dp))
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {

            // Grade badge
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Text(student.grade, fontSize = 18.sp, fontWeight = FontWeight.Black, color = color)
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    student.name,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(student.remarks, fontSize = 12.sp, color = color)
                Spacer(modifier = Modifier.height(8.dp))

                // Progress bar — explicit height on both boxes fixes the error
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(CircleShape)
                        .background(DarkBorder)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(fraction = fraction)
                            .height(4.dp)
                            .clip(CircleShape)
                            .background(color)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Score
            Column(horizontalAlignment = Alignment.End) {
                Text(student.total.fmt(), fontSize = 22.sp, fontWeight = FontWeight.Black, color = color)
                Text("/ 100", fontSize = 11.sp, color = TextSecondary)
                Text("${student.gradePoints} pts", fontSize = 11.sp, color = TextHint)
            }
        }
    }
}