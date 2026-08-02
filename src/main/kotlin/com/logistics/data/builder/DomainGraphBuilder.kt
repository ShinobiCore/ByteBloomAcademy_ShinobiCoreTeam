package com.logistics.data.builder

import com.logistics.data.raw.PackageRaw
import com.logistics.data.raw.RouteRaw
import com.logistics.data.raw.VehicleRaw
import com.logistics.data.raw.WarehouseRaw
import com.logistics.domain.model.Package
import com.logistics.domain.model.Route
import com.logistics.domain.model.Vehicle
import com.logistics.domain.model.Warehouse

class DomainGraphBuilder {
    fun buildGraph(
        warehouseRawItems: List<WarehouseRaw>,
        packageRawItems: List<PackageRaw>,
        routeRawItems: List<RouteRaw>,
        vehicleRawItems: List<VehicleRaw>
    ): DomainGraph {
        val warehousesById = warehouseRawItems
            .map { Warehouse(it.id, it.name, it.regionalZone, it.latitude, it.longitude) }
            .associateBy { it.id }

        val packageRawItemsByOrigin = packageRawItems.groupBy { it.originWarehouseId }
        val routeRawItemsByOrigin = routeRawItems.groupBy { it.originWarehouseId }
        val vehicleRawItemsByHub = vehicleRawItems.groupBy { it.currentHubId }

        val packages = packageRawItemsByOrigin.values
            .flatten()
            .mapNotNull { createPackage(it, warehousesById) }

        val routes = routeRawItemsByOrigin.values
            .flatten()
            .mapNotNull { createRoute(it, warehousesById) }

        val vehicles = vehicleRawItemsByHub.values
            .flatten()
            .mapNotNull { createVehicle(it, warehousesById) }

        packages.forEach { it.origin.addPackage(it) }
        routes.forEach { it.origin.addRoute(it) }
        vehicles.forEach { it.currentHub.addVehicle(it) }

        return DomainGraph(
            warehouses = warehousesById.values.toList(),
            packages = packages,
            routes = routes,
            vehicles = vehicles
        )
    }

    private fun createPackage(raw: PackageRaw, warehousesById: Map<String, Warehouse>): Package? {
        val origin = warehousesById[raw.originWarehouseId]
        val destination = warehousesById[raw.destinationWarehouseId]
        if (origin == null || destination == null) {
            return null
        }

        return Package(
            id = raw.id,
            weight = raw.weight,
            priority = raw.priority,
            origin = origin,
            destination = destination
        )
    }

    private fun createRoute(raw: RouteRaw, warehousesById: Map<String, Warehouse>): Route? {
        val origin = warehousesById[raw.originWarehouseId]
        val destination = warehousesById[raw.destinationWarehouseId]
        if (origin == null || destination == null) {
            return null
        }

        return Route(
            routeId = raw.routeId,
            distanceKm = raw.distanceKm,
            typicalDelayMin = raw.typicalDelayMin,
            origin = origin,
            destination = destination
        )
    }

    private fun createVehicle(raw: VehicleRaw, warehousesById: Map<String, Warehouse>): Vehicle? {
        val currentHub = warehousesById[raw.currentHubId]
        if (currentHub == null) {
            return null
        }

        return Vehicle(
            vehicleId = raw.vehicleId,
            maxCapacityKg = raw.maxCapacityKg,
            costPerKm = raw.costPerKm,
            currentHub = currentHub
        )
    }
}
