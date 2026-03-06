package com.gradecalc.data

import com.gradecalc.model.Student
import com.gradecalc.utils.fmt
import org.apache.poi.ss.usermodel.BorderStyle
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.Font
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File

/**
 * ExcelWriter — object singleton
 * Creates a brand new Excel file with grade results only
 * No CA or Exam columns in the output
 */
object ExcelWriter {

    fun writeResults(students: List<Student>, outputFile: File) {
        val workbook: Workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("Grade Results")

        // ── Styles ────────────────────────────────────────────────────────

        val headerFont: Font = workbook.createFont()
        headerFont.bold = true
        headerFont.fontHeightInPoints = 12
        headerFont.color = IndexedColors.WHITE.index

        val headerStyle: CellStyle = workbook.createCellStyle()
        headerStyle.setFont(headerFont)
        headerStyle.fillForegroundColor = IndexedColors.DARK_BLUE.index
        headerStyle.fillPattern = FillPatternType.SOLID_FOREGROUND
        headerStyle.alignment = HorizontalAlignment.CENTER
        headerStyle.borderBottom = BorderStyle.MEDIUM

        val centerStyle: CellStyle = workbook.createCellStyle()
        centerStyle.alignment = HorizontalAlignment.CENTER

        val numberStyle: CellStyle = workbook.createCellStyle()
        numberStyle.dataFormat = workbook.createDataFormat().getFormat("0.00")
        numberStyle.alignment = HorizontalAlignment.CENTER

        val boldStyle: CellStyle = workbook.createCellStyle()
        val boldFont: Font = workbook.createFont()
        boldFont.bold = true
        boldStyle.setFont(boldFont)

        fun gradeStyle(grade: String): CellStyle {
            val bgColor: Short = when (grade) {
                "A"  -> IndexedColors.LIGHT_GREEN.index
                "B+" -> IndexedColors.LIGHT_BLUE.index
                "B"  -> IndexedColors.LIGHT_CORNFLOWER_BLUE.index
                "C+" -> IndexedColors.YELLOW.index
                "C"  -> IndexedColors.LIGHT_YELLOW.index
                "D+" -> IndexedColors.TAN.index
                "D"  -> IndexedColors.ORANGE.index
                else -> IndexedColors.ROSE.index
            }
            val style: CellStyle = workbook.createCellStyle()
            style.fillForegroundColor = bgColor
            style.fillPattern = FillPatternType.SOLID_FOREGROUND
            style.alignment = HorizontalAlignment.CENTER
            val font: Font = workbook.createFont()
            font.bold = true
            style.setFont(font)
            return style
        }

        // ── Header Row — NO CA or Exam ────────────────────────────────────

        val headers = listOf("No.", "Student Name", "Total", "Grade", "Grade Points", "Remarks")
        val headerRow = sheet.createRow(0)
        headers.forEachIndexed { col, title ->        // Lambda
            val cell = headerRow.createCell(col)
            cell.setCellValue(title)
            cell.cellStyle = headerStyle
        }

        // ── Data Rows — using lambda forEach ─────────────────────────────

        students.forEachIndexed { index, student ->   // Lambda
            val row = sheet.createRow(index + 1)

            val noCell = row.createCell(0)
            noCell.setCellValue((index + 1).toDouble())
            noCell.cellStyle = centerStyle

            row.createCell(1).setCellValue(student.name)

            val totalCell = row.createCell(2)
            totalCell.setCellValue(student.total)
            totalCell.cellStyle = numberStyle

            val gradeCell = row.createCell(3)
            gradeCell.setCellValue(student.grade)
            gradeCell.cellStyle = gradeStyle(student.grade)

            val pointsCell = row.createCell(4)
            pointsCell.setCellValue(student.gradePoints)
            pointsCell.cellStyle = numberStyle

            val remarksCell = row.createCell(5)
            remarksCell.setCellValue(student.remarks)
            remarksCell.cellStyle = centerStyle
        }

        // ── Summary Section ───────────────────────────────────────────────

        val summaryStart = students.size + 3

        fun addRow(offset: Int, label: String, value: String) {
            val r = sheet.createRow(summaryStart + offset)
            val lc = r.createCell(0)
            lc.setCellValue(label)
            lc.cellStyle = boldStyle
            r.createCell(1).setCellValue(value)
        }

        val avg     = students.map { it.total }.average()   // Lambda
        val passing = students.count { it.grade != "F" }    // Lambda
        val failing = students.count { it.grade == "F" }    // Lambda
        val highest = students.maxOf { it.total }           // Lambda
        val lowest  = students.minOf { it.total }           // Lambda

        addRow(0, "── SUMMARY ──",    "")
        addRow(1, "Total Students",   "${students.size}")
        addRow(2, "Class Average",    avg.fmt())
        addRow(3, "Highest Total",    highest.fmt())
        addRow(4, "Lowest Total",     lowest.fmt())
        addRow(5, "Passing Students", "$passing")
        addRow(6, "Failing Students", "$failing")

        // ── Auto-size & Save ──────────────────────────────────────────────

        for (col in 0..5) sheet.autoSizeColumn(col)

        outputFile.parentFile?.mkdirs()
        outputFile.outputStream().use { workbook.write(it) }
        workbook.close()
    }
}