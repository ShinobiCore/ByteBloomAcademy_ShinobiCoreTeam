package com.logistics.parsers

import com.logistics.dataholder.VehicleParseResult
import com.logistics.dataholder.VehicleRaw
import java.io.File

private const val EXPECTED_COLUMN_COUNT = 4
private const val INVALID_NUMBER_FALLBACK = -1.0

fun parseVehicleFile(filePath: String): VehicleParseResult {
    val file = File(filePath)

    if (!file.exists()) {
        val criticalWarning = "Critical Error: File '$filePath' not found."
        return VehicleParseResult(emptyList(), listOf(criticalWarning))
    }

    val vehicleRaws = mutableListOf<VehicleRaw>()
    val warnings = mutableListOf<String>()

    file.useLines { lines ->
        val iterator = lines.iterator()

        if (!iterator.hasNext()) {
            warnings.add("Warning: File '$filePath' is empty.")
            return@useLines
        }

        iterator.next()

        var lineNumber = 2
        for (line in iterator) {
            if (line.isBlank()) {
                lineNumber++
                continue
            }

            val parsedRow = parseSingleRow(line, lineNumber, warnings)
            if (parsedRow != null) {
                vehicleRaws.add(parsedRow)
            }

            lineNumber++
        }
    }

    return VehicleParseResult(vehicleRaws, warnings)
}

private fun parseSingleRow(line: String, lineNumber: Int, warnings: MutableList<String>): VehicleRaw? {
    val columns = line.split(",")
        .map { it.trim() }
        .dropLastWhile { it.isEmpty() }

    if (columns.size != EXPECTED_COLUMN_COUNT) {
        warnings.add("Warning at line $lineNumber: Invalid column count (${columns.size} instead of $EXPECTED_COLUMN_COUNT). Skipped.")
        return null
    }

    val (vehicleIdRaw, hubIdRaw, capacityRaw, costRaw) = columns

    val vehicleId = vehicleIdRaw.uppercase()

    if (hubIdRaw.isBlank()) {
        warnings.add("Warning at line $lineNumber: Hub ID is missing. Skipped.")
        return null
    }
    val currentHubId = hubIdRaw.uppercase()

    val maxCapacityKg = capacityRaw.toDoubleOrNull() ?: INVALID_NUMBER_FALLBACK
    val costPerKm = costRaw.toDoubleOrNull() ?: INVALID_NUMBER_FALLBACK

    return VehicleRaw(vehicleId, currentHubId, maxCapacityKg, costPerKm)
}