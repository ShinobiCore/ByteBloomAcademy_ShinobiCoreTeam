package com.logistics.parsers

import com.logistics.dataholder.Fleet
import java.io.File

private const val EXPECTED_COLUMN_COUNT = 4
private const val VEHICLE_ID_PREFIX = "TRK-"
private const val INVALID_NUMBER_FALLBACK = -1.0

/**
 * Parses a CSV file containing fleet data and converts it into a list of [Fleet] objects.
 * Returns a Pair where:
 * - first: List of valid Fleet objects.
 * - second: List of warning messages for skipped rows.
 */
fun parseFleetFile(filePath: String): Pair<List<Fleet>, List<String>> {
    val file = File(filePath)

    if (!file.exists()) {
        return Pair(emptyList(), listOf("Critical Error: File '$filePath' not found."))
    }

    val validFleets = mutableListOf<Fleet>()
    val warnings = mutableListOf<String>()

    file.useLines { lines ->
        val iterator = lines.iterator()

        if (!iterator.hasNext()) {
            warnings.add("Warning: File '$filePath' is empty.")
            return@useLines
        }

        iterator.next() // Skip header row

        var lineNumber = 2
        for (line in iterator) {
            if (line.isBlank()) {
                lineNumber++
                continue
            }

            // Extract row parsing to keep the main loop readable
            val fleet = parseSingleRow(line, lineNumber, warnings)
            if (fleet != null) {
                validFleets.add(fleet)
            }

            lineNumber++
        }
    }

    return Pair(validFleets, warnings)
}

/**
 * Attempts to parse a single CSV row into a Fleet object.
 * Modifies the [warnings] list directly if validation fails (acting as an error collector).
 *
 * @return A valid [Fleet] object, or null if the row is invalid.
 */
private fun parseSingleRow(line: String, lineNumber: Int, warnings: MutableList<String>): Fleet? {
    val columns = line.split(",")
        .map { it.trim() }
        .dropLastWhile { it.isEmpty() }

    // Guard clauses enforce fast failure and prevent deep nesting
    if (columns.size != EXPECTED_COLUMN_COUNT) {
        warnings.add("Warning at line $lineNumber: Invalid column count (${columns.size} instead of $EXPECTED_COLUMN_COUNT). Skipped.")
        return null
    }

    val (vehicleIdRaw, hubIdRaw, capacityRaw, costRaw) = columns

    val vehicleId = vehicleIdRaw.uppercase()
    if (!vehicleId.startsWith(VEHICLE_ID_PREFIX)) {
        warnings.add("Warning at line $lineNumber: Invalid Vehicle ID '$vehicleId'. Skipped.")
        return null
    }

    if (hubIdRaw.isBlank()) {
        warnings.add("Warning at line $lineNumber: Hub ID is empty. Skipped.")
        return null
    }

    val capacity = capacityRaw.toDoubleOrNull() ?: INVALID_NUMBER_FALLBACK
    if (capacity <= 0.0) {
        warnings.add("Warning at line $lineNumber: Invalid max capacity '$capacityRaw'. Skipped.")
        return null
    }

    val costPerKm = costRaw.toDoubleOrNull() ?: INVALID_NUMBER_FALLBACK

    return Fleet(vehicleId, hubIdRaw, capacity, costPerKm)
}