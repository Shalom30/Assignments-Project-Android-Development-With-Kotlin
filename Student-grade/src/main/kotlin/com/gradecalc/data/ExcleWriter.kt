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
import org.apache.poi.xwpf.usermodel.ParagraphAlignment
import org.apache.poi.xwpf.usermodel.XWPFDocument
import java.io.File

/**
 * ExcelWriter — object singleton
 * Handles writing grade results to Excel, Word, HTML and PDF (via browser)
 */
object ExcelWriter {

    // ── Write Excel Results ───────────────────────────────────────────────────

    fun writeResults(students: List<Student>, outputFile: File) {
        val workbook: Workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("Grade Results")

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

        val headers = listOf("No.", "Student Name", "Total", "Grade", "Grade Points", "Remarks")
        val headerRow = sheet.createRow(0)
        headers.forEachIndexed { col, title ->
            val cell = headerRow.createCell(col)
            cell.setCellValue(title)
            cell.cellStyle = headerStyle
        }

        students.forEachIndexed { index, student ->
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

        val summaryStart = students.size + 3

        fun addRow(offset: Int, label: String, value: String) {
            val r = sheet.createRow(summaryStart + offset)
            val lc = r.createCell(0)
            lc.setCellValue(label)
            lc.cellStyle = boldStyle
            r.createCell(1).setCellValue(value)
        }

        val avg     = students.map { it.total }.average()
        val passing = students.count { it.grade != "F" }
        val failing = students.count { it.grade == "F" }
        val highest = students.maxOf { it.total }
        val lowest  = students.minOf { it.total }

        addRow(0, "── SUMMARY ──",    "")
        addRow(1, "Total Students",   "${students.size}")
        addRow(2, "Class Average",    avg.fmt())
        addRow(3, "Highest Total",    highest.fmt())
        addRow(4, "Lowest Total",     lowest.fmt())
        addRow(5, "Passing Students", "$passing")
        addRow(6, "Failing Students", "$failing")

        for (col in 0..5) sheet.autoSizeColumn(col)

        outputFile.parentFile?.mkdirs()
        outputFile.outputStream().use { workbook.write(it) }
        workbook.close()
    }

    // ── Save as Word (.docx) ──────────────────────────────────────────────────

    fun saveAsWord(students: List<Student>, outputFile: File) {
        val doc = XWPFDocument()

        // ── Title ─────────────────────────────────────────────────────────
        val title = doc.createParagraph()
        title.alignment = ParagraphAlignment.CENTER
        val titleRun = title.createRun()
        titleRun.setText("GradeDesk — Grade Results")
        titleRun.isBold = true
        titleRun.fontSize = 18
        titleRun.addBreak()

        // ── Date subtitle ─────────────────────────────────────────────────
        val subtitle = doc.createParagraph()
        subtitle.alignment = ParagraphAlignment.CENTER
        val subtitleRun = subtitle.createRun()
        subtitleRun.setText("Generated: ${java.time.LocalDate.now()}  |  Total Students: ${students.size}")
        subtitleRun.fontSize = 11
        subtitleRun.addBreak()

        // ── Table ─────────────────────────────────────────────────────────
        val table = doc.createTable(students.size + 1, 6)

        // Header row — bold text
        val headerLabels = listOf("No.", "Student Name", "Total", "Grade", "Grade Points", "Remarks")
        headerLabels.forEachIndexed { col, text ->
            val cell = table.getRow(0).getCell(col)
            cell.removeParagraph(0)
            val para = cell.addParagraph()
            val run  = para.createRun()
            run.setText(text)
            run.isBold = true
        }

        // Data rows
        students.forEachIndexed { index, student ->
            val row = table.getRow(index + 1)
            row.getCell(0).text = "${index + 1}"
            row.getCell(1).text = student.name
            row.getCell(2).text = student.total.fmt()
            row.getCell(3).text = student.grade
            row.getCell(4).text = "${student.gradePoints}"
            row.getCell(5).text = student.remarks
        }

        // ── Summary paragraph ─────────────────────────────────────────────
        val avg     = students.map { it.total }.average()
        val passing = students.count { it.grade != "F" }
        val failing = students.count { it.grade == "F" }
        val highest = students.maxOf { it.total }
        val lowest  = students.minOf { it.total }

        doc.createParagraph() // blank line

        val summaryPara = doc.createParagraph()
        val summaryRun  = summaryPara.createRun()
        summaryRun.isBold = true
        summaryRun.setText("Summary")
        summaryRun.addBreak()
        summaryRun.isBold = false

        val detailRun = summaryPara.createRun()
        detailRun.setText(
            "Class Average: ${avg.fmt()}   |   " +
                    "Highest: ${highest.fmt()}   |   " +
                    "Lowest: ${lowest.fmt()}   |   " +
                    "Passing: $passing   |   " +
                    "Failing: $failing"
        )

        // ── Save ──────────────────────────────────────────────────────────
        outputFile.parentFile?.mkdirs()
        outputFile.outputStream().use { doc.write(it) }
        doc.close()
    }

    // ── Save as HTML ──────────────────────────────────────────────────────────

    fun saveAsHtml(students: List<Student>, outputFile: File) {
        outputFile.parentFile?.mkdirs()
        outputFile.writeText(generateHtmlContent(students))
        java.awt.Desktop.getDesktop().browse(outputFile.toURI())
    }

    // ── Save as PDF (via browser print) ──────────────────────────────────────

    fun saveAsPdf(students: List<Student>, outputFile: File) {
        val tempHtml = File(outputFile.parent, outputFile.nameWithoutExtension + "_temp.html")
        tempHtml.parentFile?.mkdirs()
        tempHtml.writeText(generateHtmlContent(students, forPrint = true))
        java.awt.Desktop.getDesktop().browse(tempHtml.toURI())
    }

    // ── Generate HTML content shared by both HTML and PDF ────────────────────

    private fun generateHtmlContent(students: List<Student>, forPrint: Boolean = false): String {
        val avg     = students.map { it.total }.average()
        val passing = students.count { it.grade != "F" }
        val failing = students.count { it.grade == "F" }
        val highest = students.maxOf { it.total }
        val lowest  = students.minOf { it.total }

        val rows = students.mapIndexed { index, s ->
            val color = when (s.grade) {
                "A"  -> "#4CAF50"
                "B+" -> "#4FC3F7"
                "B"  -> "#6366F1"
                "C+" -> "#F59E0B"
                "C"  -> "#EAB308"
                "D+" -> "#F97316"
                "D"  -> "#EF4444"
                else -> "#991B1B"
            }
            """
            <tr>
                <td>${index + 1}</td>
                <td class="name">${s.name}</td>
                <td>${s.total.fmt()}</td>
                <td style="color:${color}; font-weight:700;">${s.grade}</td>
                <td>${s.gradePoints}</td>
                <td style="color:${color};">${s.remarks}</td>
            </tr>
            """.trimIndent()
        }.joinToString("\n")

        val printStyle = if (forPrint) """
            @media print {
                body { background: white !important; color: black !important; }
                .header { background: #1a1a2e !important; }
                th { background: #1a1a2e !important; color: white !important; }
            }
        """ else ""

        return """
        <!DOCTYPE html>
        <html lang="en">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>GradeDesk — Grade Results</title>
            <style>
                * { margin: 0; padding: 0; box-sizing: border-box; }
                body {
                    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                    background: #1E1E1E;
                    color: #D4D4D4;
                    padding: 32px;
                }
                .header {
                    background: linear-gradient(135deg, #252526, #2D2D2D);
                    border: 1px solid #3E3E42;
                    border-radius: 16px;
                    padding: 28px 32px;
                    margin-bottom: 28px;
                    display: flex;
                    justify-content: space-between;
                    align-items: center;
                }
                .header h1 { font-size: 28px; color: #4FC3F7; letter-spacing: -1px; }
                .header p  { font-size: 13px; color: #9CDCFE; margin-top: 4px; }
                .stats {
                    display: grid;
                    grid-template-columns: repeat(5, 1fr);
                    gap: 12px;
                    margin-bottom: 28px;
                }
                .stat-card {
                    background: #2D2D2D;
                    border: 1px solid #3E3E42;
                    border-radius: 12px;
                    padding: 16px;
                    text-align: center;
                }
                .stat-value { font-size: 26px; font-weight: 900; }
                .stat-label { font-size: 11px; color: #9CDCFE; margin-top: 4px; }
                table {
                    width: 100%;
                    border-collapse: collapse;
                    background: #252526;
                    border-radius: 16px;
                    overflow: hidden;
                    border: 1px solid #3E3E42;
                }
                thead { background: #2D2D2D; }
                th {
                    padding: 14px 16px;
                    text-align: left;
                    font-size: 12px;
                    color: #4FC3F7;
                    font-weight: 700;
                    text-transform: uppercase;
                    letter-spacing: 0.5px;
                    border-bottom: 2px solid #3E3E42;
                }
                td {
                    padding: 12px 16px;
                    font-size: 14px;
                    border-bottom: 1px solid #3E3E42;
                }
                tr:nth-child(even) td { background: #2A2A2A; }
                tr:hover td { background: #2D2D2D; }
                .name { font-weight: 600; color: #F1F5F9; }
                .summary {
                    margin-top: 24px;
                    background: #2D2D2D;
                    border: 1px solid #3E3E42;
                    border-radius: 12px;
                    padding: 20px 24px;
                    font-size: 13px;
                    color: #9CDCFE;
                }
                .footer {
                    margin-top: 20px;
                    text-align: center;
                    font-size: 11px;
                    color: #475569;
                }
                $printStyle
            </style>
        </head>
        <body>
            <div class="header">
                <div>
                    <h1>🎓 GradeDesk</h1>
                    <p>Student Grade Results Report</p>
                </div>
                <div style="text-align:right; font-size:12px; color:#9CDCFE;">
                    <p>Total Students: <strong style="color:#4FC3F7;">${students.size}</strong></p>
                    <p>Generated: ${java.time.LocalDate.now()}</p>
                </div>
            </div>

            <div class="stats">
                <div class="stat-card">
                    <div class="stat-value" style="color:#4FC3F7;">${avg.fmt()}</div>
                    <div class="stat-label">Class Average</div>
                </div>
                <div class="stat-card">
                    <div class="stat-value" style="color:#4CAF50;">$passing</div>
                    <div class="stat-label">Passing</div>
                </div>
                <div class="stat-card">
                    <div class="stat-value" style="color:#F44747;">$failing</div>
                    <div class="stat-label">Failing</div>
                </div>
                <div class="stat-card">
                    <div class="stat-value" style="color:#4EC9B0;">${highest.fmt()}</div>
                    <div class="stat-label">Highest</div>
                </div>
                <div class="stat-card">
                    <div class="stat-value" style="color:#CE9178;">${lowest.fmt()}</div>
                    <div class="stat-label">Lowest</div>
                </div>
            </div>

            <table>
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>Student Name</th>
                        <th>Total</th>
                        <th>Grade</th>
                        <th>Grade Points</th>
                        <th>Remarks</th>
                    </tr>
                </thead>
                <tbody>
                    $rows
                </tbody>
            </table>

            <div class="summary">
                <p>📊 Class Average: <strong>${avg.fmt()}</strong> &nbsp;|&nbsp;
                   ✅ Passing: <strong>$passing</strong> &nbsp;|&nbsp;
                   ❌ Failing: <strong>$failing</strong> &nbsp;|&nbsp;
                   ⬆ Highest: <strong>${highest.fmt()}</strong> &nbsp;|&nbsp;
                   ⬇ Lowest: <strong>${lowest.fmt()}</strong>
                </p>
            </div>

            <div class="footer">
                Generated by GradeDesk • ${java.time.LocalDate.now()}
            </div>
        </body>
        </html>
        """.trimIndent()
    }
}