package com.logistics

import com.logistics.dataholder.Package
import com.logistics.utils.selectionSortPackages

import com.logistics.parsers.parseVehicleFile
import com.logistics.parsers.parseRoute
import com.logistics.parsers.parsePackagesFromCsv
import java.io.File

private const val PACKAGES_FILE_PATH = "src/main/resources/packages.csv"
private const val ROUTES_FILE_PATH = "src/main/resources/routes.csv"
private const val FLEET_FILE_PATH = "src/main/resources/fleet.csv"
private const val TOP_PACKAGES_DISPLAY_LIMIT = 3

fun main() {
    // 1. Load and parse data
    val validPackages = loadPackages(PACKAGES_FILE_PATH)
    val routesData = parseRoute(ROUTES_FILE_PATH)
    val (validFleet, fleetWarnings) = parseVehicleFile(FLEET_FILE_PATH)

    // 2. Display summary
    printSummary(
        packagesCount = validPackages.size,
        routesCount = routesData.routes.size,
        fleetCount = validFleet.size
    )

    // 3. Process and display top packages
    val sortedPackages = selectionSortPackages(validPackages)
    printTopPackages(sortedPackages, TOP_PACKAGES_DISPLAY_LIMIT)
}

/**
 * Safely opens the file, parses the packages, and ensures the stream is closed afterward.
 */
private fun loadPackages(filePath: String): List<Package> {
    return File(filePath).inputStream().use { stream ->
        parsePackagesFromCsv(stream)
    }
}

private fun printSummary(packagesCount: Int, routesCount: Int, fleetCount: Int) {
    println("Packages parsed: $packagesCount")
    println("Routes parsed: $routesCount")
    println("Fleet parsed: $fleetCount\n")
}

private fun printTopPackages(packages: List<Package>, limit: Int) {
    println("=== Top $limit ===")

    packages.take(limit).forEachIndexed { index, pkg ->
        // Using a multiline string with trimMargin() is cleaner than multiple println() statements
        val output = """
            |${index + 1}. ${pkg.packageId}
            |${pkg.priority}
            |Weight: ${pkg.weight}
            |Destination: ${pkg.destinationHub}
        """.trimMargin()

        println(output)
    }
}