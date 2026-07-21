package com.logistics.utils

private const val CSV_DELIMITER = ","

fun isBlankLine(line: String): Boolean = line.trim().isEmpty()

fun splitAndTrimCsvLine(line: String): List<String> {
    if (isBlankLine(line)) return emptyList()
    return line.split(CSV_DELIMITER).map { it.trim() }
}