package com.logistics

import com.logistics.sorter.selectionSortPackages
import com.logistics.data.parser.PackageParser
import com.logistics.data.parser.WarehouseParser
import com.logistics.data.parser.RouteParser
import com.logistics.data.parser.VehicleParser
import com.logistics.data.builder.DomainGraphBuilder
import com.logistics.data.raw.PackageRaw
import com.logistics.service.RoutePricingEngine
import com.logistics.domain.strategy.EcoStrategy

import java.util.Locale

private const val PACKAGES_FILE_PATH = "src/main/resources/packages.csv"
private const val WAREHOUSES_FILE_PATH = "src/main/resources/warehouses.csv"
private const val ROUTES_FILE_PATH = "src/main/resources/routes.csv"
private const val FLEET_FILE_PATH = "src/main/resources/fleet.csv"
private const val TOP_PACKAGES_DISPLAY_LIMIT = 3

fun main() {

    val packageParseResult = PackageParser.parsePackageFile(PACKAGES_FILE_PATH)
    val warehouseParseResult = WarehouseParser.processFile(WAREHOUSES_FILE_PATH)
    val routeParseResult = RouteParser.parseRouteFile(ROUTES_FILE_PATH)
    val vehicleParseResult = VehicleParser.parseVehicleFile(FLEET_FILE_PATH)

    val packageRawItems = packageParseResult.rows.toMutableList()
    val warehouseRawItems = warehouseParseResult.rows
    val routeRawItems = routeParseResult.rows
    val vehicleRawItems = vehicleParseResult.rows

    println("Welcome to Logistics!")

    printSummary(packageRawItems.size, warehouseRawItems.size, routeRawItems.size, vehicleRawItems.size)

    val packageRawItemsSorted = selectionSortPackages(packageRawItems)

    println("Top 3 packages after stable selection sort:")
    printTopPackages(packageRawItemsSorted, TOP_PACKAGES_DISPLAY_LIMIT)
    println()

    val domainGraph = DomainGraphBuilder().buildGraph(
        warehouseRawItems = warehouseRawItems,
        packageRawItems = packageRawItems,
        routeRawItems = routeRawItems,
        vehicleRawItems = vehicleRawItems
    )

    val firstWarehouse = domainGraph.warehouses.firstOrNull {
        it.cargoQueue.isNotEmpty() && it.stationedVehicles.isNotEmpty() && it.outgoingRoutes.isNotEmpty()
    } ?: return
    println("Sorted cargo queue for ${firstWarehouse.name}:")
    firstWarehouse.cargoQueue.forEach { packageItem ->
        println("${packageItem.id} | weight=${packageItem.weight} | priority=${packageItem.priority}")
    }
    println()

    val firstVehicle = firstWarehouse.stationedVehicles.first()
    val heapReferenceMatches = firstVehicle.currentHub === firstWarehouse
    println("Warehouse -> Vehicle -> currentHub keeps same heap reference: $heapReferenceMatches")
    println()

    val firstPackage = firstWarehouse.cargoQueue.first()
    val firstRoute = firstWarehouse.outgoingRoutes.first()
    val pricingEngine = RoutePricingEngine(EcoStrategy())
    val ecoCost = pricingEngine.calculateTransitCost(firstPackage, firstRoute, firstVehicle)

    println("Eco cost: ${formatMoney(ecoCost)}")

}


private fun printSummary(packagesCount: Int, routesCount: Int, fleetCount: Int, warehousesCount: Int) {
    println("=== Logistics Summary ===")
    println("Packages parsed: $packagesCount")
    println("Routes parsed: $routesCount")
    println("Fleet parsed: $fleetCount")
    println("Warehouses parsed: $warehousesCount\n")
}

private fun printTopPackages(packages: List<PackageRaw>, limit: Int) {
    println("=== Top $limit Packages ===")

    packages.take(limit).forEachIndexed { index, pkg ->
        val output = """
           ||----------- ${index + 1} -----------
           || ${pkg.id}
           || Priority: ${pkg.priority} | Weight: ${pkg.weight} kg
           || origin : ${pkg.originWarehouseId} | Destination: ${pkg.destinationWarehouseId}
           ||------------------------
        """.trimMargin()
        println(output)
    }
    println()
}

private fun formatMoney(value: Double): String {
    return "$" + String.format(Locale.US, "%.2f", value)
}