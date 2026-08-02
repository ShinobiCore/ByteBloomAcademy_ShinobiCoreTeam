package com.logistics

import com.logistics.dataholder.PackageRaw
import com.logistics.sorter.selectionSortPackages

import com.logistics.parsers.parseVehicleFile
import com.logistics.parsers.parseRouteFile
import com.logistics.parsers.PackageParser.parsePackageFile


private const val PACKAGES_FILE_PATH = "src/main/resources/packages.csv"
private const val ROUTES_FILE_PATH = "src/main/resources/routes.csv"
private const val FLEET_FILE_PATH = "src/main/resources/fleet.csv"
private const val TOP_PACKAGES_DISPLAY_LIMIT = 3


fun main() {

    val packages = parsePackageFile(PACKAGES_FILE_PATH)
    val routes = parseRouteFile(ROUTES_FILE_PATH)
    val vehicles = parseVehicleFile(FLEET_FILE_PATH)

    printSummary(
        packagesCount = packages.packages.size,
        routesCount = routes.routes.size,
        fleetCount = vehicles.vehicles.size
    )

    val sortedPackages = selectionSortPackages(packages.packages)
    printTopPackages(sortedPackages)
}

private fun printSummary(packagesCount: Int, routesCount: Int, fleetCount: Int) {
    println("Packages parsed: $packagesCount")
    println("Routes parsed: $routesCount")
    println("Fleet parsed: $fleetCount\n")
}

private fun printTopPackages(packages: List<PackageRaw>) {
    println("=== Top $TOP_PACKAGES_DISPLAY_LIMIT ===")

    packages.take(TOP_PACKAGES_DISPLAY_LIMIT).forEachIndexed { index, pkg ->
        val output = """
            |${index + 1}. ${pkg.packageId}
            |${pkg.priority}
            |Weight: ${pkg.weightKg}
            |Destination hub id: ${pkg.destinationHubId}
        """.trimMargin()

        println(output)
    }
}