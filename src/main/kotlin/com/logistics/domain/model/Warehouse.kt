package com.logistics.domain.model

class Warehouse(
    val id: String,
    val name: String,
    val regionalZone: String,
    val latitude: Double,
    val longitude: Double
) {
    private val mutableCargoQueue = mutableListOf<Package>()
    private val mutableOutgoingRoutes = mutableListOf<Route>()
    private val mutableStationedVehicles = mutableListOf<Vehicle>()

    val cargoQueue: List<Package>
        get() = mutableCargoQueue

    val outgoingRoutes: List<Route>
        get() = mutableOutgoingRoutes

    val stationedVehicles: List<Vehicle>
        get() = mutableStationedVehicles

    fun addPackage(packageItem: Package) {
        mutableCargoQueue.add(packageItem)
    }

    fun addRoute(route: Route) {
        mutableOutgoingRoutes.add(route)
    }

    fun addVehicle(vehicle: Vehicle) {
        mutableStationedVehicles.add(vehicle)
    }

    fun sortCargoQueueByWeightDescending() {
        com.logistics.sorter.QuickSorter.sortPackagesByWeightDescending(mutableCargoQueue)
    }
}
