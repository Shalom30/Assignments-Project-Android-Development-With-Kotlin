package com.gradecalc.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gradecalc.ui.theme.*
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter

@Composable
fun HomeScreen(
    filePath: String,
    isLoading: Boolean,
    errorMessage: String?,
    onFilePathChange: (String) -> Unit,
    onLoadFile: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBg)
    ) {
        // Background glow
        Box(
            modifier = Modifier
                .size(400.dp)
                .offset(x = (-80).dp, y = (-80).dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(AccentBlue.copy(alpha = 0.06f), Color.Transparent)
                    ),
                    shape = CircleShape
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // ── App Icon ──────────────────────────────────────────────────
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(AccentBlue, AccentCyan)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.School,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── Title ─────────────────────────────────────────────────────
            Text(
                text = "GradeDesk",
                fontSize = 36.sp,
                fontWeight = FontWeight.Black,
                color = TextPrimary,
                letterSpacing = (-1).sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Professional student grade management for desktop",
                fontSize = 14.sp,
                color = TextSecondary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            // ── Upload Card ───────────────────────────────────────────────
            Box(
                modifier = Modifier
                    .width(500.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(DarkCard)
                    .border(
                        width = 1.dp,
                        brush = Brush.linearGradient(
                            colors = listOf(
                                AccentBlue.copy(alpha = 0.4f),
                                AccentCyan.copy(alpha = 0.2f)
                            )
                        ),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(32.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(AccentBlue.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.FolderOpen,
                            contentDescription = null,
                            tint = AccentBlue,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // File path text field
                    OutlinedTextField(
                        value = filePath,
                        onValueChange = onFilePathChange,
                        placeholder = {
                            Text(
                                "C:\\Users\\yourname\\Desktop\\students.xlsx",
                                color = TextHint,
                                fontSize = 13.sp
                            )
                        },
                        label = {
                            Text("Excel File Path (.xlsx or .xls)", color = TextSecondary)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor        = TextPrimary,
                            unfocusedTextColor      = TextPrimary,
                            focusedBorderColor      = AccentBlue,
                            unfocusedBorderColor    = DarkBorder,
                            cursorColor             = AccentBlue,
                            focusedContainerColor   = DarkSurface,
                            unfocusedContainerColor = DarkSurface
                        ),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Browse button — Excel only
                    OutlinedButton(
                        onClick = {
                            val chosen = browseForFile()
                            if (chosen != null) onFilePathChange(chosen)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = AccentCyan
                        ),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp, AccentCyan.copy(alpha = 0.5f)
                        )
                    ) {
                        Icon(
                            Icons.Default.FolderOpen,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Browse for Excel File", fontSize = 13.sp)
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Load button
                    Button(
                        onClick = onLoadFile,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AccentBlue
                        ),
                        enabled = filePath.isNotBlank() && !isLoading
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier.size(18.dp),
                                strokeWidth = 2.dp
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text("Loading...", fontWeight = FontWeight.SemiBold)
                        } else {
                            Text(
                                "Load & Calculate Grades",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                        }
                    }

                    // Error message
                    AnimatedVisibility(
                        visible = errorMessage != null,
                        enter   = fadeIn(),
                        exit    = fadeOut()
                    ) {
                        errorMessage?.let {
                            Spacer(modifier = Modifier.height(12.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(AccentRed.copy(alpha = 0.1f))
                                    .border(
                                        1.dp,
                                        AccentRed.copy(alpha = 0.3f),
                                        RoundedCornerShape(10.dp)
                                    )
                                    .padding(12.dp)
                            ) {
                                Text("⚠ $it", color = AccentRed, fontSize = 13.sp)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            GradeScaleRow()
        }
    }
}

@Composable
fun GradeScaleRow() {
    val grades = listOf(
        "A"  to "80-100",
        "B+" to "70-79",
        "B"  to "60-69",
        "C+" to "55-59",
        "C"  to "50-54",
        "D+" to "45-49",
        "D"  to "40-44",
        "F"  to "0-39"
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Grading Scale", fontSize = 12.sp, color = TextHint)
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            grades.forEach { (grade, range) ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(gradeColor(grade).copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            grade,
                            color = gradeColor(grade),
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(range, color = TextHint, fontSize = 8.sp)
                }
            }
        }
    }
}

// ── File picker — Excel files only ───────────────────────────────────────────
private fun browseForFile(): String? {
    val chooser = JFileChooser()
    chooser.dialogTitle = "Select an Excel File"
    chooser.fileFilter  = FileNameExtensionFilter(
        "Excel Files (*.xlsx, *.xls)", "xlsx", "xls"
    )
    val result = chooser.showOpenDialog(null)
    return if (result == JFileChooser.APPROVE_OPTION) chooser.selectedFile.absolutePath else null
}