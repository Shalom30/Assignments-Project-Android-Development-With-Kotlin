package com.gradecalc.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gradecalc.model.Student
import com.gradecalc.ui.theme.*

@Composable
fun PreviewScreen(
    students: List<Student>,
    onCalculate: () -> Unit,
    onBack: () -> Unit
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
                    "Preview Data",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    "${students.size} students found",
                    fontSize = 12.sp,
                    color = TextSecondary
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = onCalculate,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AccentGreen)
            ) {
                Icon(
                    Icons.Default.Calculate,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Calculate Grades", fontWeight = FontWeight.SemiBold)
            }
        }

        // ── Stats Row ─────────────────────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatChip("Students", "${students.size}", AccentBlue,   Modifier.weight(1f))
            StatChip("Max CA",   "40",               AccentCyan,   Modifier.weight(1f))
            StatChip("Max Exam", "60",               AccentPurple, Modifier.weight(1f))
            StatChip("Total Max","100",              AccentGreen,  Modifier.weight(1f))
        }

        // ── Table ─────────────────────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(1.dp, DarkBorder, RoundedCornerShape(16.dp))
        ) {
            Column {
                // Header row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(AccentBlue.copy(alpha = 0.2f))
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    TableHead("No.",           0.08f)
                    TableHead("Student Name",  0.40f)
                    TableHead("CA (Max 40)",   0.26f)
                    TableHead("Exam (Max 60)", 0.26f)
                }

                HorizontalDivider(color = DarkBorder)

                // Data rows
                LazyColumn {
                    itemsIndexed(students) { index, student ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    if (index % 2 == 0) DarkCard else DarkSurface
                                )
                                .padding(horizontal = 16.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TableCell("${index + 1}",                  0.08f, TextSecondary)
                            TableCell(student.name,                    0.40f, TextPrimary)
                            TableCell("%.1f".format(student.ca),       0.26f, AccentCyan)
                            TableCell("%.1f".format(student.exam),     0.26f, AccentPurple)
                        }
                        HorizontalDivider(color = DarkBorder.copy(alpha = 0.5f))
                    }
                }
            }
        }
    }
}

@Composable
fun RowScope.TableHead(text: String, weight: Float) {
    Text(
        text     = text,
        modifier = Modifier.weight(weight),
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color    = AccentBlue
    )
}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float,
    color: androidx.compose.ui.graphics.Color = TextPrimary
) {
    Text(
        text     = text,
        modifier = Modifier.weight(weight),
        fontSize = 13.sp,
        color    = color,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun StatChip(
    label: String,
    value: String,
    color: androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(color.copy(alpha = 0.1f))
            .border(1.dp, color.copy(alpha = 0.3f), RoundedCornerShape(10.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(value, fontSize = 18.sp, fontWeight = FontWeight.Black, color = color)
            Text(label, fontSize = 10.sp, color = TextSecondary)
        }
    }
}