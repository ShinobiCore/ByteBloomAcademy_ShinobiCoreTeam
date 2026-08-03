package com.logistics

import com.logistics.dataholder.Package
import com.logistics.parsers.FleetParser
import com.logistics.parsers.PackageParser
import com.logistics.parsers.RouteParser
import com.logistics.parsers.WarehouseParser
import com.logistics.utils.selectionSortPackages

private const val PACKAGES_FILE_PATH = "src/main/resources/packages.csv"
private const val ROUTES_FILE_PATH = "src/main/resources/routes.csv"
private const val FLEET_FILE_PATH = "src/main/resources/fleet.csv"
private const val WAREHOUSES_FILE_PATH = "src/main/resources/warehouses.csv"
private const val TOP_PACKAGES_DISPLAY_LIMIT = 3

fun main() {

    val packageResult = PackageParser.parsePackageFile(PACKAGES_FILE_PATH)
    val routeResult = RouteParser.parseRouteFile(ROUTES_FILE_PATH)
    val fleetResult = FleetParser.parseVehicleFile(FLEET_FILE_PATH)
    val warehouseResult = WarehouseParser.processFile(WAREHOUSES_FILE_PATH)


    printSummary(
        packagesCount = packageResult.packages.size,
        routesCount = routeResult.routes.size,
        fleetCount = fleetResult.vehicles.size,
        warehousesCount = warehouseResult.warehouses.size
    )


    val sortedPackages = selectionSortPackages(packageResult.packages)
    printTopPackages(sortedPackages, TOP_PACKAGES_DISPLAY_LIMIT)
}

private fun printSummary(packagesCount: Int, routesCount: Int, fleetCount: Int, warehousesCount: Int) {
    println("=== Logistics Summary ===")
    println("Packages parsed: $packagesCount")
    println("Routes parsed: $routesCount")
    println("Fleet parsed: $fleetCount")
    println("Warehouses parsed: $warehousesCount\n")
}

private fun printTopPackages(packages: List<Package>, limit: Int) {
    println("=== Top $limit Packages ===")

    packages.take(limit).forEachIndexed { index, pkg ->
        val output = """
            |${index + 1}. ${pkg.packageId}
            |Priority: ${pkg.priority}
            |Weight: ${pkg.weight} kg
            |Destination: ${pkg.destinationHub}
        """.trimMargin()

        println(output)
    }
}