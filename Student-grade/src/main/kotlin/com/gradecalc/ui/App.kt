package com.gradecalc.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.gradecalc.data.ExcelParser
import com.gradecalc.data.ExcelWriter
import com.gradecalc.ui.screens.HomeScreen
import com.gradecalc.ui.screens.PreviewScreen
import com.gradecalc.ui.screens.ResultScreen
import com.gradecalc.ui.theme.GradeCalcTheme
import java.io.File
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter

@Composable
fun App() {
    val state = remember { AppState() }

    GradeCalcTheme {
        when (state.currentScreen) {

            // ── Home Screen ───────────────────────────────────────────────
            Screen.HOME -> HomeScreen(
                filePath         = state.inputFilePath,
                isLoading        = state.isLoading,
                errorMessage     = state.errorMessage,
                onFilePathChange = { path ->
                    state.inputFilePath = path
                    state.errorMessage  = null
                },
                onLoadFile = {
                    if (state.inputFilePath.isBlank()) {
                        state.errorMessage = "Please enter a file path."
                        return@HomeScreen
                    }
                    val file = File(state.inputFilePath.trim())
                    if (!file.exists()) {
                        state.errorMessage = "File not found. Check the path and try again."
                        return@HomeScreen
                    }

                    // Only Excel files allowed
                    val ext = file.extension.lowercase()
                    if (ext !in listOf("xlsx", "xls")) {
                        state.errorMessage = "Only Excel files (.xlsx, .xls) are supported. Please upload an Excel file."
                        return@HomeScreen
                    }

                    state.isLoading    = true
                    state.errorMessage = null

                    try {
                        val parseResult = ExcelParser.parse(file)
                        if (parseResult.students.isEmpty()) {
                            state.errorMessage = "No valid student data found. Check your Excel format."
                        } else {
                            state.students      = parseResult.students
                            state.currentScreen = Screen.PREVIEW
                        }
                    } catch (e: Exception) {
                        state.errorMessage = "Error reading file: ${e.message}"
                    } finally {
                        state.isLoading = false
                    }
                }
            )

            // ── Preview Screen ────────────────────────────────────────────
            Screen.PREVIEW -> PreviewScreen(
                students    = state.students,
                onCalculate = {
                    state.currentScreen = Screen.RESULTS
                },
                onBack = {
                    state.currentScreen = Screen.HOME
                    state.errorMessage  = null
                }
            )

            // ── Results Screen ────────────────────────────────────────────
            Screen.RESULTS -> ResultScreen(
                students        = state.students,
                downloadMessage = state.downloadMessage,
                onBack = {
                    state.currentScreen   = Screen.PREVIEW
                    state.downloadMessage = null
                },
                onReset = {
                    state.reset()
                },
                onDownload = { format ->
                    try {
                        val chooser = JFileChooser()
                        chooser.dialogTitle = "Save Grade Results As"

                        when (format) {

                            "PDF" -> {
                                chooser.selectedFile = File("grade_results.pdf")
                                chooser.fileFilter   = FileNameExtensionFilter("PDF File (*.pdf)", "pdf")
                                val dialogResult = chooser.showSaveDialog(null)
                                if (dialogResult == JFileChooser.APPROVE_OPTION) {
                                    var outputFile = chooser.selectedFile
                                    if (!outputFile.name.endsWith(".pdf")) {
                                        outputFile = File(outputFile.absolutePath + ".pdf")
                                    }
                                    ExcelWriter.saveAsPdf(state.students, outputFile)
                                    state.downloadMessage = "✅ PDF opened in browser — press Ctrl+P → Save as PDF"
                                }
                            }

                            "HTML" -> {
                                chooser.selectedFile = File("grade_results.html")
                                chooser.fileFilter   = FileNameExtensionFilter("HTML File (*.html)", "html")
                                val dialogResult = chooser.showSaveDialog(null)
                                if (dialogResult == JFileChooser.APPROVE_OPTION) {
                                    var outputFile = chooser.selectedFile
                                    if (!outputFile.name.endsWith(".html")) {
                                        outputFile = File(outputFile.absolutePath + ".html")
                                    }
                                    ExcelWriter.saveAsHtml(state.students, outputFile)
                                    state.downloadMessage = "✅ HTML saved and opened: ${outputFile.absolutePath}"
                                }
                            }
                            "EXCEL" -> {
                                chooser.selectedFile = File("grade_results.xlsx")
                                chooser.fileFilter   = FileNameExtensionFilter("Excel File (*.xlsx)", "xlsx")
                                val dialogResult = chooser.showSaveDialog(null)
                                if (dialogResult == JFileChooser.APPROVE_OPTION) {
                                    var outputFile = chooser.selectedFile
                                    if (!outputFile.name.endsWith(".xlsx")) {
                                        outputFile = File(outputFile.absolutePath + ".xlsx")
                                    }
                                    ExcelWriter.writeResults(state.students, outputFile)
                                    state.downloadMessage = "✅ Excel saved: ${outputFile.absolutePath}"
                                }
                            }

                            "WORD" -> {
                                chooser.selectedFile = File("grade_results.docx")
                                chooser.fileFilter   = FileNameExtensionFilter("Word File (*.docx)", "docx")
                                val dialogResult = chooser.showSaveDialog(null)
                                if (dialogResult == JFileChooser.APPROVE_OPTION) {
                                    var outputFile = chooser.selectedFile
                                    if (!outputFile.name.endsWith(".docx")) {
                                        outputFile = File(outputFile.absolutePath + ".docx")
                                    }
                                    ExcelWriter.saveAsWord(state.students, outputFile)
                                    state.downloadMessage = "✅ Word saved: ${outputFile.absolutePath}"
                                }
                            }
                        }
                    } catch (e: Exception) {
                        state.downloadMessage = "❌ Failed: ${e.message}"
                    }
                }
            )
        }
    }
}