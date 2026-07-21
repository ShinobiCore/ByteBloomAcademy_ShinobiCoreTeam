package com.logistics.parsers

import com.logistics.dataholder.Package
import com.logistics.utils.splitAndTrimCsvLine
import com.logistics.utils.isBlankLine
import java.io.InputStream

fun parsePackagesFromCsv(inputStream: InputStream): List<Package> {
    val rawLines = inputStream.bufferedReader().readLines()
    if (rawLines.isEmpty()) return emptyList()
    return processDataLines(rawLines.drop(1))
}

private fun processDataLines(dataLines: List<String>): List<Package> {
    val validRecords = mutableListOf<Package>()
    dataLines.forEachIndexed { index, line ->
        parseSingleRow(line, lineNumber = index + 2)?.let { validRecords.add(it) }
    }
    return validRecords
}

private fun parseSingleRow(line: String, lineNumber: Int): Package? {
    if (isBlankLine(line)) return null
    val columns = splitAndTrimCsvLine(line)
    if (columns.size < 4) return logAndSkip(lineNumber, "Missing columns")
    return buildPackageFromColumns(columns) ?: logAndSkip(lineNumber, "Blank ID or Hub")
}

private fun buildPackageFromColumns(cols: List<String>): Package? {
    val id = cols[0]
    val hub = cols[3]
    if (id.isEmpty() || hub.isEmpty()) return null

    val weight = cols[1].toDoubleOrNull() ?: -1.0
    val priority = parsePriorityOrDefault(cols[2])
    return Package(packageId = id, weight = weight, priority = priority, destinationHub = hub)
}

fun parsePriorityOrDefault(rawPriority: String): String {
    val normalized = rawPriority.trim().uppercase()
    if (normalized == "URGENT") return "URGENT"
    if (normalized == "STANDARD") return "STANDARD"
    return "LOW"
}

private fun logAndSkip(lineNumber: Int, reason: String): Package? {
    println("Diagnostic Warning: Line $lineNumber skipped ($reason).")
    return null
}