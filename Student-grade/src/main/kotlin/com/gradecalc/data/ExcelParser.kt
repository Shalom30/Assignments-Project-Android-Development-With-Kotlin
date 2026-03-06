package com.gradecalc.data

import com.gradecalc.model.GradeCalculator
import com.gradecalc.model.Student
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.File

/**
 * ExcelParser — object singleton
 * Reads Excel file and returns list of Students
 */
object ExcelParser {

    // Late init — calculator is initialized once when first needed
    private lateinit var calculator: GradeCalculator

    data class ParseResult(
        val students: List<Student>,
        val errors: List<String>
    )

    fun parse(file: File): ParseResult {
        // Late init usage — initialize only when parsing starts
        if (!::calculator.isInitialized) {
            calculator = GradeCalculator()
        }

        require(file.exists()) { "File not found: ${file.absolutePath}" }
        require(file.extension.lowercase() in listOf("xlsx", "xls")) {
            "Only .xlsx and .xls files are supported."
        }

        val workbook = WorkbookFactory.create(file)
        val sheet    = workbook.getSheetAt(0)
        val errors   = mutableListOf<String>()
        val rawData  = mutableListOf<Triple<Int, String, Pair<Double, Double>>>()

        for (rowIdx in 1..sheet.lastRowNum) {
            val row = sheet.getRow(rowIdx) ?: continue

            // Column A = ID
            val id = row.getCell(0)?.let {
                when (it.cellType) {
                    CellType.NUMERIC -> it.numericCellValue.toInt()
                    CellType.STRING  -> it.stringCellValue.trim().toIntOrNull() ?: rowIdx
                    else             -> rowIdx
                }
            } ?: rowIdx

            // Column B = Name
            val name = row.getCell(1)?.let {
                when (it.cellType) {
                    CellType.STRING  -> it.stringCellValue.trim()
                    CellType.NUMERIC -> it.numericCellValue.toInt().toString()
                    else             -> null
                }
            }

            if (name.isNullOrBlank()) {
                errors.add("Row ${rowIdx + 1}: skipped — no name in column B.")
                continue
            }

            // Column C = CA
            val ca = row.getCell(2)?.let {
                when (it.cellType) {
                    CellType.NUMERIC -> it.numericCellValue
                    CellType.STRING  -> it.stringCellValue.trim().toDoubleOrNull()
                    else             -> null
                }
            }

            if (ca == null) {
                errors.add("Row ${rowIdx + 1} ($name): skipped — no CA in column C.")
                continue
            }

            // Column D = Exam
            val exam = row.getCell(3)?.let {
                when (it.cellType) {
                    CellType.NUMERIC -> it.numericCellValue
                    CellType.STRING  -> it.stringCellValue.trim().toDoubleOrNull()
                    else             -> null
                }
            }

            if (exam == null) {
                errors.add("Row ${rowIdx + 1} ($name): skipped — no Exam in column D.")
                continue
            }

            // Validate using GradeCalculator
            if (!calculator.validate(listOf(ca, exam))) {
                errors.add("Row ${rowIdx + 1} ($name): CA must be 0-40 and Exam must be 0-60.")
                continue
            }

            rawData.add(Triple(id, name, Pair(ca, exam)))
        }

        workbook.close()

        // Use GradeCalculator.processStudents with lambda internally
        val students = calculator.processStudents(rawData)
        return ParseResult(students, errors)
    }
}