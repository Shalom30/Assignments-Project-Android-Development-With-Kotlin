package com.gradecalc.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.gradecalc.data.ExcelParser
import com.gradecalc.data.ExcelWriter
import com.gradecalc.ui.screens.HomeScreen
import com.gradecalc.ui.screens.PreviewScreen
import com.gradecalc.ui.screens.ResultScreen
import com.gradecalc.ui.theme.GradeCalcTheme
import java.io.File
import javax.swing.JFileChooser

@Composable
fun App() {
    val state = remember { AppState() }

    GradeCalcTheme {
        when (state.currentScreen) {

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
                    state.isLoading    = true
                    state.errorMessage = null
                    try {
                        val result = ExcelParser.parse(file)
                        if (result.students.isEmpty()) {
                            state.errorMessage = "No valid student data found. Check your Excel format."
                        } else {
                            state.students      = result.students
                            state.currentScreen = Screen.PREVIEW
                        }
                    } catch (e: Exception) {
                        state.errorMessage = "Error reading file: ${e.message}"
                    } finally {
                        state.isLoading = false
                    }
                }
            )

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
                onDownload = {
                    try {
                        val chooser = JFileChooser()
                        chooser.dialogTitle  = "Save Grade Results As"
                        chooser.selectedFile = File("grade_results.xlsx")
                        val result = chooser.showSaveDialog(null)
                        if (result == JFileChooser.APPROVE_OPTION) {
                            var outputFile = chooser.selectedFile
                            if (!outputFile.name.endsWith(".xlsx")) {
                                outputFile = File(outputFile.absolutePath + ".xlsx")
                            }
                            ExcelWriter.writeResults(state.students, outputFile)
                            state.downloadMessage = "Saved to: ${outputFile.absolutePath}"
                        }
                    } catch (e: Exception) {
                        state.downloadMessage = "Failed: ${e.message}"
                    }
                }
            )
        }
    }
}