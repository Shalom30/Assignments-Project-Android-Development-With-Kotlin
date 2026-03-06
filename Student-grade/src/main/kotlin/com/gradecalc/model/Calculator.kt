package com.gradecalc.model

/**
 * Abstract base class — Calculator
 * GradeCalculator inherits from this class.
 * Demonstrates: abstract class, inheritance, polymorphism
 */
abstract class Calculator {

    // Abstract function — MUST be overridden by subclass (polymorphism)
    abstract fun calculate(inputs: List<Double>): Double

    // Abstract function — MUST be overridden by subclass
    abstract fun validate(inputs: List<Double>): Boolean

    // Open function — CAN be overridden by subclass
    open fun getDescription(): String = "Base Calculator"

    // Concrete function shared by all calculators
    fun formatResult(value: Double): String = "%.2f".format(value)
}