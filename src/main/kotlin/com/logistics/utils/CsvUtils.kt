package com.logistics.utils

import java.io.File

private const val CSV_DELIMITER = ","

object CsvUtils {

    fun readLinesWithoutHeader(filePath: String): List<String> {
        val file = File(filePath)
        if (!file.exists()) return emptyList()
        return file.readLines().drop(1)
    }

    fun splitAndTrim(line: String): List<String> {
        return line.split(",").map { it.trim() }
    }

    fun parseSafeDouble(value: String): Double {
        return value.toDoubleOrNull() ?: -1.0
    }

    fun isBlankLine(line: String): Boolean = line.trim().isEmpty()

    fun splitAndTrimCsvLine(line: String): List<String> {
        if (isBlankLine(line)) return emptyList()
        return line.split(CSV_DELIMITER).map { it.trim() }
    }

}