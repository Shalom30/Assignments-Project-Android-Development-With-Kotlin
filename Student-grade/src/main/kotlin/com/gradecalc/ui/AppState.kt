package com.gradecalc.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.gradecalc.model.Student

class AppState {

    var currentScreen by mutableStateOf(Screen.HOME)
    var inputFilePath by mutableStateOf("")
    var errorMessage  by mutableStateOf<String?>(null)
    var isLoading     by mutableStateOf(false)
    var downloadMessage by mutableStateOf<String?>(null)

    // lateinit — initialized only after file is parsed
    lateinit var students: List<Student>

    val hasStudents: Boolean
        get() = ::students.isInitialized && students.isNotEmpty()

    val classAverage: Double
        get() = if (hasStudents) students.map { it.total }.average() else 0.0

    val passingCount: Int
        get() = if (hasStudents) students.count { it.grade != "F" } else 0

    val failingCount: Int
        get() = if (hasStudents) students.count { it.grade == "F" } else 0

    val highestTotal: Double
        get() = if (hasStudents) students.maxOf { it.total } else 0.0

    val lowestTotal: Double
        get() = if (hasStudents) students.minOf { it.total } else 0.0

    fun reset() {
        currentScreen   = Screen.HOME
        inputFilePath   = ""
        errorMessage    = null
        isLoading       = false
        downloadMessage = null
    }
}

enum class Screen { HOME, PREVIEW, RESULTS }