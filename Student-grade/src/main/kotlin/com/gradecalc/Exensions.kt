package com.gradecalc.utils

import com.gradecalc.model.Student

// Extension function on Double — format to 2 decimal places
fun Double.fmt(): String = "%.2f".format(this)

// Extension function on List<Student> — get passing students
fun List<Student>.passing(): List<Student> = this.filter { it.grade != "F" }

// Extension function on List<Student> — get failing students
fun List<Student>.failing(): List<Student> = this.filter { it.grade == "F" }

// Extension function on List<Student> — class average
fun List<Student>.classAverage(): Double =
    if (this.isEmpty()) 0.0 else this.map { it.total }.average()

// Extension function on List<Student> — highest scorer
fun List<Student>.topStudent(): Student? = this.maxByOrNull { it.total }

// Extension function on String — truncate long names
fun String.truncate(max: Int = 20): String =
    if (this.length > max) this.take(max) + "..." else this