package com.logistics.data.parser

import com.logistics.data.raw.VehicleRaw
import com.logistics.utils.CsvUtils
import java.io.File

object VehicleParser {

    private const val EXPECTED_COLUMN_COUNT = 4
    private const val VEHICLE_ID_PREFIX = "TRK-"

    fun parseVehicleFile(filePath: String): ParseResult<VehicleRaw> {
        val file = File(filePath)

        if (!file.exists()) {
            return ParseResult(emptyList(), listOf("Critical Error: File '$filePath' not found."))
        }

        val validVehicles = mutableListOf<VehicleRaw>()
        val warnings = mutableListOf<String>()

        val lines = file.readLines()
        if (lines.isEmpty()) {
            return ParseResult(emptyList(), listOf("Warning: File '$filePath' is empty."))
        }

        for ((index, line) in lines.drop(1).withIndex()) {
            val lineNumber = index + 2
            if (line.isBlank()) continue

            val vehicle = parseSingleRow(line, lineNumber, warnings)
            if (vehicle != null) {
                validVehicles.add(vehicle)
            }
        }

        return ParseResult(validVehicles, warnings)
    }

    private fun parseSingleRow(line: String, lineNumber: Int, warnings: MutableList<String>): VehicleRaw? {
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

        return VehicleRaw(vehicleId, capacity, costPerKm, hubIdRaw)
    }
}