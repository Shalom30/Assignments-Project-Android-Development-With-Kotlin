package com.gradecalc.model

/**
 * GradeCalculator — derives from Calculator
 * Demonstrates: inheritance, polymorphism, override
 */
class GradeCalculator : Calculator() {

    // Override — polymorphism: calculates total from CA + Exam
    override fun calculate(inputs: List<Double>): Double {
        require(inputs.size >= 2) { "Need at least CA and Exam scores" }
        val ca   = inputs[0].coerceIn(0.0, 40.0)
        val exam = inputs[1].coerceIn(0.0, 60.0)
        return ca + exam
    }

    // Override — validates that scores are in range
    override fun validate(inputs: List<Double>): Boolean {
        if (inputs.size < 2) return false
        val ca   = inputs[0]
        val exam = inputs[1]
        return ca in 0.0..40.0 && exam in 0.0..60.0
    }

    // Override — custom description
    override fun getDescription(): String = "Student Grade Calculator (CA + Exam)"

    // Grade from total score — lambda-friendly
    fun toGrade(total: Double): String = when {
        total >= 80 -> "A"
        total >= 70 -> "B+"
        total >= 60 -> "B"
        total >= 55 -> "C+"
        total >= 50 -> "C"
        total >= 45 -> "D+"
        total >= 40 -> "D"
        else        -> "F"
    }

    fun toRemarks(grade: String): String = when (grade) {
        "A"  -> "Excellent"
        "B+" -> "Very Good"
        "B"  -> "Good"
        "C+" -> "Above Average"
        "C"  -> "Average"
        "D+" -> "Below Average"
        "D"  -> "Pass"
        else -> "Fail"
    }

    fun toGradePoints(grade: String): Double = when (grade) {
        "A"  -> 4.0
        "B+" -> 3.5
        "B"  -> 3.0
        "C+" -> 2.5
        "C"  -> 2.0
        "D+" -> 1.5
        "D"  -> 1.0
        else -> 0.0
    }

    // Process a raw student into a graded Student using lambdas
    fun processStudents(raw: List<Triple<Int, String, Pair<Double, Double>>>): List<Student> {
        return raw.map { (id, name, scores) ->           // Lambda
            val total  = calculate(listOf(scores.first, scores.second))
            val grade  = toGrade(total)
            Student(
                id          = id,
                name        = name,
                ca          = scores.first,
                exam        = scores.second,
                total       = total,
                grade       = grade,
                gradePoints = toGradePoints(grade),
                remarks     = toRemarks(grade)
            )
        }
    }
}