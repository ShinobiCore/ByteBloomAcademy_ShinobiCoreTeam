package com.logistics.parsers

import com.logistics.models.Route
import java.io.File
import java.io.IOException

data class RouteParseResult(
    val routes: List<Route>,
    val warnings: List<String>
)

fun parse(filePath: String): RouteParseResult {

    val warnings = mutableListOf<String>()
    val routes = mutableListOf<Route>()

    val lines = readFile(filePath, warnings)
        ?: return RouteParseResult(routes, warnings)

    for (index in 1 until lines.size) {

        val lineNumber = index + 1
        val rawLine = lines[index]
        val line = rawLine.trim()

        if (line.isEmpty()) continue

        val parts = splitAndCleanColumns(line)

        if (!hasValidColumns(parts, lineNumber, warnings))
            continue

        val routeId = parts[0].uppercase()
        val originHubId = parts[1]
        val destinationHubId = parts[2]

        val finalDistance = parseDistance(parts[3], lineNumber, warnings)
        val delayMinutes = parseDelay(parts[4], lineNumber, warnings)

        if (!isValidRoute(
                routeId,
                originHubId,
                destinationHubId,
                finalDistance,
                lineNumber,
                warnings
            )
        ) continue

        val myRoute = Route(
            routeId,
            originHubId,
            destinationHubId,
            finalDistance,
            delayMinutes
        )

        routes.add(myRoute)
    }

    return RouteParseResult(routes, warnings)
}

private fun readFile(
    filePath: String,
    warnings: MutableList<String>
): List<String>? {

    val file = File(filePath)

    if (!file.exists()) {
        warnings.add("Fatal Error: File not found at path: $filePath")
        return null
    }

    return try {
        file.readLines()
    } catch (e: IOException) {
        warnings.add("Fatal Error: Failed to read file $filePath -> ${e.message}")
        null
    }
}

private fun hasValidColumns(
    parts: List<String>,
    lineNumber: Int,
    warnings: MutableList<String>
): Boolean {

    if (parts.size != 5) {
        warnings.add("Skipping line $lineNumber: Invalid number of columns (expected 5, found ${parts.size})")
        return false
    }

    return true
}

private fun parseDistance(
    distanceRaw: String,
    lineNumber: Int,
    warnings: MutableList<String>
): Double {

    val distanceCleaned = distanceRaw.removeSuffix("km")
    val distance = distanceCleaned.toDoubleOrNull()

    if (distance == null) {
        warnings.add("Warning at line $lineNumber: Distance is not numeric (\"$distanceRaw\"), default value -1.0 was used")
    }

    return distance ?: -1.0
}

private fun parseDelay(
    delayRaw: String,
    lineNumber: Int,
    warnings: MutableList<String>
): Int? {

    val delayMinutes = delayRaw.toIntOrNull()

    if (delayMinutes == null && delayRaw.isNotEmpty()) {
        warnings.add("Warning at line $lineNumber: typicalDelayMin is not numeric (\"$delayRaw\"), value set to null")
    }

    return delayMinutes
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

    if (originHubId.isEmpty()) {
        warnings.add("Skipping line $lineNumber: originHubId is empty.")
        return false
    }

    if (destinationHubId.isEmpty()) {
        warnings.add("Skipping line $lineNumber: destinationHubId is empty.")
        return false
    }

    if (finalDistance <= 0) {
        warnings.add("Skipping line $lineNumber: Distance must be greater than 0 (found $finalDistance).")
        return false
    }

    return true
}

fun splitAndCleanColumns(line: String): List<String> {

    val parts = line.split(",").map { it.trim() }.toMutableList()

    while (parts.size > 1 && parts.last().isEmpty()) {
        parts.removeAt(parts.size - 1)
    }

    return parts
}