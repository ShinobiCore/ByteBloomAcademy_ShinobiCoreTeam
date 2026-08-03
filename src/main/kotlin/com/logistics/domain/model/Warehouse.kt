package com.logistics.domain.model

class Warehouse(
    val id: String,
    val name: String,
    val regionalZone: String
) {

    private val _cargoQueue = mutableListOf<Package>()
    val cargoQueue: List<Package> get() = _cargoQueue

    private val _outgoingRoutes = mutableListOf<Route>()
    val outgoingRoutes: List<Route> get() = _outgoingRoutes

    private val _stationedVehicles = mutableListOf<Vehicle>()
    val stationedVehicles: List<Vehicle> get() = _stationedVehicles

    // Encapsulated Mutators
    fun addPackage(pkg: Package) {
        _cargoQueue.add(pkg)
        pkg.origin = this
    }

    fun removePackage(pkg: Package): Boolean {
        return _cargoQueue.remove(pkg)
    }

    fun addRoute(route: Route) {
        _outgoingRoutes.add(route)
    }

    fun addVehicle(vehicle: Vehicle) {
        _stationedVehicles.add(vehicle)
        vehicle.currentHub = this
    }

    override fun toString(): String {
        return "Warehouse(id='$id', name='$name', zone='$regionalZone', cargoCount=${_cargoQueue.size})"
    }
}