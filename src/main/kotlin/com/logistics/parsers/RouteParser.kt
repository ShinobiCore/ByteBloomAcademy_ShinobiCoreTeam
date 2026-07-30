package com.logistics.parsers

import com.logistics.dataholder.RouteParseResult
import com.logistics.dataholder.RouteRaw
import com.logistics.utils.CsvUtils
import java.io.File

object RouteParser {

    fun parseRouteFile(filePath: String): RouteParseResult {
        val warnings = mutableListOf<String>()
        val routes = mutableListOf<RouteRaw>()

        val file = File(filePath)
        if (!file.exists()) {
            warnings.add("Fatal Error: File not found at path: $filePath")
            return RouteParseResult(routes, warnings)
        }

        val lines = file.readLines()
        if (lines.isEmpty()) return RouteParseResult(routes, warnings)

        for (index in 1 until lines.size) {
            val lineNumber = index + 1
            val rawLine = lines[index]
            if (rawLine.isBlank()) continue

            val parts = CsvUtils.splitAndTrim(rawLine)
            if (parts.size != 5) {
                warnings.add("Skipping line $lineNumber: Invalid number of columns (expected 5, found ${parts.size})")
                continue
            }

            val routeId = parts[0].uppercase()
            val originHubId = parts[1]
            val destinationHubId = parts[2]
            val finalDistance = CsvUtils.parseSafeDouble(parts[3])
            val delayMinutes = CsvUtils.parseSafeInt(parts[4])

            if (!isValidRoute(routeId, originHubId, destinationHubId, finalDistance, lineNumber, warnings)) {
                continue
            }

            routes.add(RouteRaw(routeId, originHubId, destinationHubId, finalDistance, delayMinutes))
        }

        return RouteParseResult(routes, warnings)
    }

    private fun isValidRoute(
        routeId: String,
        originHubId: String,
        destinationHubId: String,
        finalDistance: Double,
        lineNumber: Int,
        warnings: MutableList<String>
    ): Boolean {
        if (!routeId.startsWith("RT-")) {
            warnings.add("Skipping line $lineNumber: Invalid RouteID ($routeId). It must start with RT-.")
            return false
        }
        if (originHubId.isEmpty() || destinationHubId.isEmpty()) {
            warnings.add("Skipping line $lineNumber: Origin or Destination Hub ID is empty.")
            return false
        }
        if (finalDistance <= 0) {
            warnings.add("Skipping line $lineNumber: Distance must be greater than 0.")
            return false
        }
        return true
    }
}

// دالة توافقية مع Main القديم
fun parseRoute(filePath: String): RouteParseResult = RouteParser.parseRouteFile(filePath)