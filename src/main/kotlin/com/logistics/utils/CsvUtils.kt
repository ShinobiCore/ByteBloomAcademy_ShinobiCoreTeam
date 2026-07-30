package com.logistics.utils

import java.io.File

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
        val cleaned = value.removeSuffix("km").trim()
        return cleaned.toDoubleOrNull() ?: -1.0
    }
    fun parseSafeInt(value: String): Int? {
        return value.trim().toIntOrNull()
    }
}