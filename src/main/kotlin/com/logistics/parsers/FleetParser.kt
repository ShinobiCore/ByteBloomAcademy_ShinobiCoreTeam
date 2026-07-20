package com.logistics.parsers

import com.logistics.dataholder.Fleet
import java.io.File

/**
 * Parses a CSV file containing fleet data and converts it into a list of [Fleet] objects.
 *
 * This function is designed to be safe and fault-tolerant. It handles common data entry
 * errors such as empty lines, extra spaces, trailing commas, and invalid numbers without
 * crashing the system. Invalid rows are skipped, and a warning is recorded.
 *
 * @param filePath The exact path to the `fleet.csv` file.
 * @return A [Pair] containing two lists:
 *         1. `first`: A list of valid, fully processed [Fleet] objects.
 *         2. `second`: A list of warning messages explaining why certain rows were skipped.
 */
fun parseFleetFile(filePath: String): Pair<List<Fleet>, List<String>> {
    val validFleet = mutableListOf<Fleet>()
    val warnings = mutableListOf<String>()

    val file = File(filePath)

    // Check if the file exists to prevent crashes
    if (!file.exists()) {
        warnings.add("Critical Error: File '$filePath' not found.")
        return Pair(validFleet, warnings)
    }

    file.useLines { lines ->
        val iterator = lines.iterator()

        // Check if the file is completely empty
        if (!iterator.hasNext()) {
            warnings.add("Warning: File '$filePath' is empty.")
            return@useLines
        }

        // 1. Skip the header row
        iterator.next()

        var lineNumber = 1
        for (line in iterator) {
            lineNumber++
            val trimmedLine = line.trim()

            // 2. Ignore completely empty lines
            if (trimmedLine.isEmpty()) continue

            // 3. Split the line by commas and remove extra spaces from each column
            val parts = trimmedLine.split(",").map { it.trim() }

            // 4. Remove empty elements at the end caused by trailing commas (e.g., "data1,data2,")
            val cleanedParts = parts.dropLastWhile { it.isEmpty() }

            // 5. Ensure the line has exactly 4 columns
            if (cleanedParts.size != 4) {
                warnings.add("Warning at line $lineNumber: Invalid column count (${cleanedParts.size} instead of 4). Skipped.")
                continue
            }

            // Extract values and normalize the Vehicle ID to uppercase (e.g., "trk-001" becomes "TRK-001")
            val vehicleId = cleanedParts[0].uppercase()
            val currentHubId = cleanedParts[1]
            val maxCapacityStr = cleanedParts[2]
            val costPerKmStr = cleanedParts[3]

            // 6. Validate string formats
            if (!vehicleId.startsWith("TRK-")) {
                warnings.add("Warning at line $lineNumber: Invalid Vehicle ID '$vehicleId'. Skipped.")
                continue
            }
            if (currentHubId.isBlank()) {
                warnings.add("Warning at line $lineNumber: Hub ID is empty. Skipped.")
                continue
            }

            // 7. Safely convert strings to numbers
            // If the value is "N/A", "null", or empty, it falls back to -1.0
            val maxCapacityKg = maxCapacityStr.toDoubleOrNull() ?: -1.0
            val costPerKm = costPerKmStr.toDoubleOrNull() ?: -1.0

            // 8. Validate numeric logic (Capacity must be greater than zero)
            if (maxCapacityKg <= 0.0) {
                warnings.add("Warning at line $lineNumber: Invalid max capacity '$maxCapacityStr'. Skipped.")
                continue
            }

            // If all checks pass, create the Fleet object and add it to the list
            validFleet.add(Fleet(vehicleId, currentHubId, maxCapacityKg, costPerKm))
        }
    }

    return Pair(validFleet, warnings)
}