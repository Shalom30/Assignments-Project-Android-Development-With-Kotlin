package com.gradecalc.model

/**
 * Student data class — holds all grade information
 * Late init used in AppState for the student list
 */
data class Student(
    val id: Int,
    val name: String,
    val ca: Double,
    val exam: Double,
    val total: Double,
    val grade: String,
    val gradePoints: Double,
    val remarks: String,
    val rowIndex: Int = 0
)