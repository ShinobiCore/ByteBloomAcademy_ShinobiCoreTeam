package com.logistics.parsers

import com.logistics.dataholder.FleetParseResult
import com.logistics.dataholder.Vehicle
import com.logistics.utils.CsvUtils
import java.io.File

object FleetParser {

    private const val EXPECTED_COLUMN_COUNT = 4
    private const val VEHICLE_ID_PREFIX = "TRK-"

    fun parseVehicleFile(filePath: String): FleetParseResult {
        val file = File(filePath)

        if (!file.exists()) {
            return FleetParseResult(emptyList(), listOf("Critical Error: File '$filePath' not found."))
        }

        val validVehicles = mutableListOf<Vehicle>()
        val warnings = mutableListOf<String>()

        val lines = file.readLines()
        if (lines.isEmpty()) {
            return FleetParseResult(emptyList(), listOf("Warning: File '$filePath' is empty."))
        }

        for ((index, line) in lines.drop(1).withIndex()) {
            val lineNumber = index + 2
            if (line.isBlank()) continue

            val vehicle = parseSingleRow(line, lineNumber, warnings)
            if (vehicle != null) {
                validVehicles.add(vehicle)
            }
        }

        return FleetParseResult(validVehicles, warnings)
    }

    private fun parseSingleRow(line: String, lineNumber: Int, warnings: MutableList<String>): Vehicle? {
        val columns = CsvUtils.splitAndTrim(line)

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

        val capacity = CsvUtils.parseSafeDouble(capacityRaw)
        if (capacity <= 0.0) {
            warnings.add("Warning at line $lineNumber: Invalid max capacity '$capacityRaw'. Skipped.")
            return null
        }

        val costPerKm = CsvUtils.parseSafeDouble(costRaw)

        return Vehicle(vehicleId, hubIdRaw, capacity, costPerKm)
    }
}

// دالة توافقية مع Main القديم
fun parseVehicleFile(filePath: String): Pair<List<Vehicle>, List<String>> {
    val result = FleetParser.parseVehicleFile(filePath)
    return Pair(result.vehicles, result.warnings)
}